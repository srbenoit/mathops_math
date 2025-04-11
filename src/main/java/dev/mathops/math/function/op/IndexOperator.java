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
 * The "index" operator.
 * <p>
 * This operator pops two values from the stack, then pushes them back so their positions are exchanged.
 */
public final class IndexOperator extends AbstractOperator {

    /**
     * Constructs a new {code IndexOperator}.
     */
    public IndexOperator() {

        super("index");
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
                    "[index] operation executed when stack is empty.");
        }

        if (arg instanceof Long argLong) {
            final long index = argLong.longValue();
            if (index < 0L) {
                throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                        "[index] operation executed with negative index.");
            }
            final int stackSize = stack.size();
            if (index >= (long) stackSize) {
                throw new OperatorException(EOperatorExceptionType.RANGE_CHECK,
                        "[index] operation executed with index beyond stack contents.");
            }
            stack.index((int) index);
        } else {
            throw badType("Argument", arg, "Long");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("index")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
