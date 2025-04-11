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
 * The "true" operator.
 * <p>
 * This operator pushes a TRUE value onto the stack.
 */
public final class TrueOperator extends AbstractOperator {

    /**
     * Constructs a new {code TrueOperator}.
     */
    public TrueOperator() {

        super("true");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        stack.pushBoolean(Boolean.TRUE);
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("true")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
