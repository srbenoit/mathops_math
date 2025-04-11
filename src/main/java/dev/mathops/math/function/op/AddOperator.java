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
 * The "add" operator.
 * <p>
 * This operator pops two numbers from the stack, computes their sum, and returns that value to the stack.
 */
public final class AddOperator extends AbstractOperator {

    /**
     * Constructs a new {code AddOperator}.
     */
    public AddOperator() {

        super("add");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object num2 = stack.poll();
        final Object num1 = stack.poll();

        if (num1 == null || num2 == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[add] operation executed when stack had less than 2 entries.");
        }

        if (num1 instanceof Long num1Long) {
            if (num2 instanceof Long num2Long) {
                final long sum = num1Long.longValue() + num2Long.longValue();
                if (sum > OperatorStack.MAX_INTEGER || sum < OperatorStack.MIN_INTEGER) {
                    stack.pushDouble((double) sum);
                } else {
                    stack.pushLong(sum);
                }
            } else if (num2 instanceof Double arg2Dbl) {
                final double sum = num1Long.doubleValue() + arg2Dbl.doubleValue();
                stack.pushDouble(sum);
            } else {
                throw badType("Second argument", num2, "Number");
            }
        } else if (num1 instanceof Double arg1Dbl) {
            if (num2 instanceof Number arg2Nbr) {
                final double sum = arg1Dbl.doubleValue() + arg2Nbr.doubleValue();
                stack.pushDouble(sum);
            } else {
                throw badType("Second argument", num2, "Number");
            }
        } else {
            throw badType("First argument", num1, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("add")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
