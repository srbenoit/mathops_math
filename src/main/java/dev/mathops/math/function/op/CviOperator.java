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
 * The "cvi" operator.
 * <p>
 * This operator pops a number or String from the stack, converts it to an integer, and returns that value to the stack.
 * Real (double) values are converted to integers by truncation (rounding toward zero).
 */
public final class CviOperator extends AbstractOperator {

    /**
     * Constructs a new {code CviOperator}.
     */
    public CviOperator() {

        super("cvi");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object arg = stack.poll();

        if (arg == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[cvi] operation executed when stack is empty.");
        }

        if (arg instanceof Double argDbl) {
            final double value = argDbl.doubleValue();
            if (value > (double) OperatorStack.MAX_INTEGER || value < (double) OperatorStack.MIN_INTEGER) {
                throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                        "Argument to [cvi] operation (" + argDbl
                                + ") is outside allowed range of integers");
            }
            final long rounded = (long) value;
            stack.pushLong(rounded);
        } else if (arg instanceof Long argLong) {
            final long value = argLong.longValue();
            stack.pushLong(value);
        } else {
            throw badType("Argument ", arg, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("cvi")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
