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
 * The "eq" operator.
 * <p>
 * This operator pops two objects from the stack, tests whether they are equal, and then pushes TRUE if equal or FALSE
 * if unequal to the stack.
 */
public final class EqOperator extends AbstractOperator {

    /**
     * Constructs a new {code EqOperator}.
     */
    public EqOperator() {

        super("eq");
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
                    "[eq] operation executed when stack had less than 2 entries.");
        }

        final boolean result;

        if (arg1 instanceof Number arg1Nbr) {
            if (arg2 instanceof Number arg2Nbr) {
                result = arg1Nbr.doubleValue() == arg2Nbr.doubleValue();
            } else {
                result = false;
            }
        } else if (arg1 instanceof Boolean arg1Bool) {
            if (arg2 instanceof Boolean arg2Bool) {
                result = arg1Bool.equals(arg2Bool);
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        final Boolean toPush = Boolean.valueOf(result);
        stack.pushBoolean(toPush);
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("eq")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
