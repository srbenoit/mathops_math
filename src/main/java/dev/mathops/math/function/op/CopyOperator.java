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
 * The "copy" operator.
 * <p>
 * This operator pops a non-negative integer from the stack which determines the number of stack entries to copy, then
 * pops that many stack entries, and then pushes them back twice, so the stack ands up with its original contents (less
 * the non-negative count, N), plus a copy of the top N items.
 */
public final class CopyOperator extends AbstractOperator {

    /**
     * Constructs a new {code CopyOperator}.
     */
    public CopyOperator() {

        super("copy");
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
                    "[copy] operation executed when stack is empty.");
        }

        if (arg instanceof Long argLong) {
            final long numberToCopy = argLong.longValue();
            if (numberToCopy < 0L) {
                throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                        "First argument to [copy] operation may not be negative.");
            }

            final int stackSize = stack.size();
            if (numberToCopy > (long) stackSize) {
                throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                        "[copy] operation asked to copy " + numberToCopy + " items, but stack has " + stackSize);
            }

            stack.dupN((int) numberToCopy);
        } else {
            throw badType("First argument ", arg, "Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("copy")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
