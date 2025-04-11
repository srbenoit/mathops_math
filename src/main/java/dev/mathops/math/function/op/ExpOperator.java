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
 * The "exp" operator.
 * <p>
 * This operator pops two numbers from the stack, computes the first raised to the power of the second, and returns that
 * value to the stack.  The result is always a Double value.
 */
public final class ExpOperator extends AbstractOperator {

    /**
     * Constructs a new {code ExpOperator}.
     */
    public ExpOperator() {

        super("exp");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object exponent = stack.poll();
        final Object base = stack.poll();

        if (base == null || exponent == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[exp] operation executed when stack had less than 2 entries.");
        }

        if (base instanceof Number baseNbr) {
            if (exponent instanceof Number exponentNbr) {
                final double baseValue = baseNbr.doubleValue();
                final double exponentValue = exponentNbr.doubleValue();

                final double ans = Math.pow(baseValue, exponentValue);
                if (Double.isFinite(ans)) {
                    stack.pushDouble(ans);
                } else {
                    throw new OperatorException(EOperatorExceptionType.UNDEFINED_RESULT,
                            "Raising base " + base + " to exponent " + exponent + " produces undefined result.");
                }
            } else {
                throw badType("Second argument", base, "Number");
            }
        } else {
            throw badType("First argument", base, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("exp")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
