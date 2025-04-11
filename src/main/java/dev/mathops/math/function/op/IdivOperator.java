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
 * The "idiv" operator.
 * <p>
 * This operator pops two integers from the stack, computes their quotient with remainder discarded (where the first
 * entry popped is the denominator and the second is the numerator), and returns that value to the stack.
 */
public final class IdivOperator extends AbstractOperator {

    /**
     * Constructs a new {code IdivOperator}.
     */
    public IdivOperator() {

        super("idiv");
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
                    "[idiv] operation executed when stack had less than 2 entries.");
        }

        if (arg1 instanceof Long arg1Long) {
            if (arg2 instanceof Long arg2Long) {
                final long denominator = arg1Long.longValue();
                if (denominator == 0L) {
                    throw new OperatorException(EOperatorExceptionType.UNDEFINED_RESULT,
                            "[idiv] operation executed with denominator zero.");
                }
                final long numerator = arg2Long.longValue();
                final long quotient = numerator / denominator;
                stack.pushLong(quotient);
            } else {
                throw badType("Second argument", arg2, "Long");
            }
        } else {
            throw badType("First argument", arg1, "Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("idiv")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
