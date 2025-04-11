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
 * The "atan" operator.
 * <p>
 * This operator pops two numbers from the stack, computes the inverse tangent of the first ("y") divided by the second
 * ("x"), with the result in the quadrant indicated by that (x, y) point, and returns that value to the stack.  This
 * corresponds to the "atan2" function in C/C++/Java.
 */
public final class AtanOperator extends AbstractOperator {

    /**
     * Constructs a new {code AtanOperator}.
     */
    public AtanOperator() {

        super("atan");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object denom = stack.poll();
        final Object numer = stack.poll();

        if (numer == null || denom == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[atan] operation executed when stack had less than 2 entries.");
        }

        if (numer instanceof Number yNbr) {
            if (denom instanceof Number xNbr) {
                final double y = yNbr.doubleValue();
                final double x = xNbr.doubleValue();

                if (x == 0.0 && y == 0.0) {
                    throw new OperatorException(EOperatorExceptionType.UNDEFINED_RESULT,
                            "Arguments to [atan] operation were both zero.");
                }
                final double result = Math.atan2(y, x);
                stack.pushDouble(result);
            } else {
                throw badType("Second argument", denom, "Number");
            }
        } else {
            throw badType("First argument", numer, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("atan")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
