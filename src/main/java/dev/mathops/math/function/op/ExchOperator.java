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
 * The "exch" operator.
 * <p>
 * This operator pops two values from the stack, then pushes them back so their positions are exchanged.
 */
public final class ExchOperator extends AbstractOperator {

    /**
     * Constructs a new {code ExchOperator}.
     */
    public ExchOperator() {

        super("exch");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final int size = stack.size();

        if (size < 2) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[exch] operation executed when stack had less than 2 entries.");
        }

        stack.exchange();
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("exch")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
