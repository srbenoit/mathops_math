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

/**
 * A literal number operator that simply pushes a number onto the stack.
 */
public final class LiteralNumberOperator extends AbstractOperator {

    /** The value (a Long or Double). */
    private final Number value;

    /**
     * Constructs a new {code LiteralNumberOperator}.
     *
     * @param theValue the value
     */
    public LiteralNumberOperator(final double theValue) {

        super("literal");

        if (Double.isFinite(theValue)) {
            this.value = Double.valueOf(theValue);
        } else {
            throw new IllegalArgumentException("Real values must be finite");
        }
    }

    /**
     * Constructs a new {code LiteralNumberOperator}.
     *
     * @param theValue the value
     */
    public LiteralNumberOperator(final long theValue) {

        super("literal");

        if (theValue > OperatorStack.MAX_INTEGER || theValue < OperatorStack.MIN_INTEGER) {
            this.value = Double.valueOf((double) theValue);
        } else {
            this.value = Long.valueOf(theValue);
        }
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        if (this.value instanceof Long longVal) {
            final long toPush = longVal.longValue();
            stack.pushLong(toPush);
        } else if (this.value instanceof Double doubleVal) {
            final double toPush = doubleVal.doubleValue();
            stack.pushDouble(toPush);
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("le")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
