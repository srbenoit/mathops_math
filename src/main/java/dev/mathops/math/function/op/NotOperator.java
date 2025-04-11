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
 * The "not" operator.
 * <p>
 * This operator pops one value from the stack.  If the value is a Boolean, it computes its logical NOT, and returns
 * that Boolean value to the stack.  If the value is an integer, it computes its bitwise NOT and returns that integer
 * value to the stack.
 */
public final class NotOperator extends AbstractOperator {

    /**
     * Constructs a new {code NotOperator}.
     */
    public NotOperator() {

        super("not");
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
                    "[not] operation executed when stack is empty.");
        }

        if (arg instanceof Boolean arg1Bool) {
            final boolean result = !arg1Bool.booleanValue();
            final Boolean resultObj = Boolean.valueOf(result);
            stack.pushBoolean(resultObj);
        } else if (arg instanceof Long arg1Long) {
            final long result = ~arg1Long.longValue();
            stack.pushLong(result);
        } else {
            throw badType("First argument", arg, "Boolean or Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("not")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
