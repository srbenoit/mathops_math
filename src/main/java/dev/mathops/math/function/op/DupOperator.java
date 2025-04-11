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
 * The "dup" operator.
 * <p>
 * This operator pops a value from the stack, then pushes two copies of that value onto the stack.
 */
public final class DupOperator extends AbstractOperator {

    /**
     * Constructs a new {code DupOperator}.
     */
    public DupOperator() {

        super("dup");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final int size = stack.size();

        if (size == 0) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[dup] operation executed when stack is empty.");
        }

        stack.dupN(1);
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("dup")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
