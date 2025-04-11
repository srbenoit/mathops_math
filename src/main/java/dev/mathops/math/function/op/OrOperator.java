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
 * The "or" operator.
 * <p>
 * This operator pops two values from the stack.  If they are both Boolean, it computes their logical OR, and returns
 * that Boolean value to the stack.  If they are both Integers, it computes their bitwise OR and returns that integer
 * value to the stack.
 */
public final class OrOperator extends AbstractOperator {

    /**
     * Constructs a new {code OrOperator}.
     */
    public OrOperator() {

        super("or");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object arg2 = stack.poll();
        final Object arg1 = stack.poll();

        if (arg1 == null || arg2 == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[or] operation executed when stack had less than 2 entries.");
        }

        if (arg1 instanceof Boolean arg1Bool) {
            if (arg2 instanceof Boolean arg2Bool) {
                final boolean result = arg1Bool.booleanValue() || arg2Bool.booleanValue();
                final Boolean resultObj = Boolean.valueOf(result);
                stack.pushBoolean(resultObj);
            } else {
                throw badType("Second argument", arg2, "Boolean");
            }
        } else if (arg1 instanceof Long arg1Long) {
            if (arg2 instanceof Long arg2Long) {
                final long result = arg1Long.longValue() | arg2Long.longValue();
                stack.pushLong(result);
            } else {
                throw badType("Second argument", arg2, "Long");
            }
        } else {
            throw badType("First argument", arg1, "Boolean or Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("or")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
