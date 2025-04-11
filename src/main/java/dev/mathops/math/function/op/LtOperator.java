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
 * The "lt" operator.
 * <p>
 * This operator pops two numbers from the stack, compares their numeric values, and pushes TRUE if the first is less
 * than the second, or FALSE if not to the stack.
 */
public final class LtOperator extends AbstractOperator {

    /**
     * Constructs a new {code LtOperator}.
     */
    public LtOperator() {

        super("lt");
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
                    "[lt] operation executed when stack had less than 2 entries.");
        }

        final boolean result;

        if (num1 instanceof Number arg1Nbr) {
            if (num2 instanceof Number arg2Nbr) {
                result = arg1Nbr.doubleValue() < arg2Nbr.doubleValue();
            } else {
                throw badType("Second argument", num2, "Number");
            }
        } else {
            throw badType("First argument", num1, "Number");
        }

        final Boolean toPush = Boolean.valueOf(result);
        stack.pushBoolean(toPush);
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("lt")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
