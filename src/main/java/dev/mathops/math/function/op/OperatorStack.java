/*
 * Copyright (C) 2022 Steve Benoit
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the  License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If  not, see
 * <https://www.gnu.org/licenses/>.
 */

package dev.mathops.math.function.op;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * The operator stack. The stack may contain the following object types:
 * <ul>
 *     <li>integers (stored as {code Long} values within a constrained range)</li>
 *     <li>finite real numbers (stored as {code Double} values)</li>
 *     <li>{code Boolean} values</li>
 *     <li>{code PostScriptExpression} objects</li>
 * </ul>
 */
public final class OperatorStack {

    /** The greatest integer value the stack can hold. */
    static final long MAX_INTEGER = 140_737_488_355_327L;

    /** The least integer value the stack can hold. */
    static final long MIN_INTEGER = -140_737_488_355_327L;

    /** A zero-length array used when converting the stack to an array. */
    private static final Object[] ZERO_LEN_ARRAY = new Object[0];

    /** The stack. */
    private final Deque<Object> stack;

    /**
     * Constructs a new {code OperatorStack}.
     */
    public OperatorStack() {

        this.stack = new ArrayDeque<>(20);
    }

    /**
     * Gets the number if items in the stack.
     *
     * @return the number of items
     */
    public final int size() {

        return this.stack.size();
    }

    /**
     * Pushes a {code Long} value onto the stack.
     *
     * @param toPush the value to push
     * @throws IllegalArgumentException if the value is outside the legal range
     */
    public final void pushLong(final long toPush) throws IllegalArgumentException {

        if (toPush < MIN_INTEGER || toPush > MAX_INTEGER) {
            throw new IllegalArgumentException("Integer value outside of allowed range");
        }

        final Long longObj = Long.valueOf(toPush);
        this.stack.push(longObj);
    }

    /**
     * Pushes a {code Double} value onto the stack.
     *
     * @param toPush the value to push
     * @throws IllegalArgumentException if the value is not finite
     */
    public final void pushDouble(final double toPush) throws IllegalArgumentException {

        if (Double.isFinite(toPush)) {
            final Double dblObj = Double.valueOf(toPush);
            this.stack.push(dblObj);
        } else {
            throw new IllegalArgumentException("Real value is not finite");
        }
    }

    /**
     * Pushes a {code Boolean} value onto the stack.
     *
     * @param toPush the value to push
     * @throws IllegalArgumentException if the value is not finite
     */
    public final void pushBoolean(final Boolean toPush) throws IllegalArgumentException {

        if (toPush == null) {
            throw new IllegalArgumentException("Value to push is null");
        }

        this.stack.push(toPush);
    }

    /**
     * Pushes an expression (a sequence of operators) onto the stack.
     *
     * @param expression the expression to push
     * @throws IllegalArgumentException if the expression is null
     */
    public final void pushExpression(final PostScriptExpression expression) throws IllegalArgumentException {

        if (expression == null) {
            throw new IllegalArgumentException("Expression may not be null");
        }

        this.stack.push(expression);
    }

    /**
     * Polls the stack, returning the top (most recently pushed) value or {code null} if the stack is empty.  The item
     * returned is removed from the stack.
     *
     * @return the top item from the stack
     */
    public final Object poll() {

        return this.stack.poll();
    }

    /**
     * Attempts to duplicate the top N elements on the stack.  On completion, the top 2N entries will be two copies of
     * the top N entries on entry.
     *
     * @param n the number of entries to duplicate
     */
    final void dupN(final int n) {

        if (n < 0) {
            throw new IllegalArgumentException("Number of entries to duplicate cannot be negative.");
        }
        if (n > this.stack.size()) {
            throw new IllegalArgumentException("Number of entries to duplicate cannot exceed stack size.");
        }

        final List<Object> items = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            final Object item = this.stack.pop();
            // Reverse the order as we go, since top item comes off first, but needs to go on last
            items.add(0, item);
        }

        for (final Object o : items) {
            this.stack.push(o);
        }
        for (final Object o : items) {
            this.stack.push(o);
        }
    }

    /**
     * Exchanges the top two items on the stack.
     */
    final void exchange() {

        if (this.stack.size() < 2) {
            throw new IllegalArgumentException("Stack must contain at least 2 entries to use 'exchange'.");
        }

        final Object arg1 = this.stack.poll(); // arg1 was "top of stack"
        final Object arg2 = this.stack.poll();
        this.stack.push(arg1);
        this.stack.push(arg2); // arg2 is now "top of stack"
    }

    /**
     * Finds the n-th item from the top of the stack (0 for the top item) and pushes a new copy of that item onto the
     * stack.
     *
     * @param n the index
     */
    final void index(final int n) {

        final int size = this.stack.size();
        if (n >= size) {
            throw new IllegalArgumentException("index(" + n + ") called when stack has only " + size + " items.");
        }

        final Object[] array = this.stack.toArray(ZERO_LEN_ARRAY);
        final Object toPush = array[size - 1 - n];

        this.stack.push(toPush);
    }

    /**
     * Performs a circular rotation of the top N entries on the stack by amount J.
     *
     * @param n the number of entries to rotate
     * @param j the number of steps by which to rotate (positive for upward motion)
     */
    final void roll(final int n, final int j) {

        if (n < 0 || n >= this.stack.size()) {
            throw new IllegalArgumentException("Invalid number of items to roll");
        }
        if (n > 0 && j != 0) {
            final List<Object> buffer = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                final Object obj = poll();
                buffer.add(0, obj);
            }
            // Buffer has items to roll, top-most item last

            if (j > 0) {
                for (int i = 0; i < j; ++i) {
                    // Roll top (last) to bottom (first) for positive J
                    final Object obj = buffer.remove(n - 1);
                    buffer.add(0, obj);
                }
            } else {
                for (int i = 0; i > j; --i) {
                    // Roll bottom (first) to top (last) for negative J
                    final Object obj = buffer.remove(0);
                    buffer.add(obj);
                }
            }

            // Return the rolled items to the stack
            for (final Object obj : buffer) {
                this.stack.push(obj);
            }
        }
    }

    /**
     * Generates the string representation of the stack.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        return "OperatorStack{stack=" + stack + '}';
    }
}
