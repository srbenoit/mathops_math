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
 * The "roll" operator.
 * <p>
 * This operator pops a value from the stack, then pushes two copies of that value onto the stack.
 */
public final class RollOperator extends AbstractOperator {

    /**
     * Constructs a new {code RollOperator}.
     */
    public RollOperator() {

        super("roll");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object j = stack.poll();
        final Object n = stack.poll();

        if (n == null || j == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[roll] operation executed when stack has less than 2 entries.");
        }

        if (n instanceof Long nLong) {
            if (j instanceof Long jLong) {
                final long numberToRoll = nLong.longValue();
                if (numberToRoll < 0) {
                    throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                            "First argument to [roll] operation must be non-negative.");
                }
                final int stackSize = stack.size();
                if (numberToRoll > (long) stackSize) {
                    throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                            "First argument to [roll] operation may not exceed stack size.");
                }

                final long steps = jLong.longValue();
                final int intSteps = (int) (steps % numberToRoll);

                stack.roll((int) numberToRoll, intSteps);
            } else {
                throw badType("Second argument ", j, "Long");
            }
        } else {
            throw badType("First argument ", n, "Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("roll")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
