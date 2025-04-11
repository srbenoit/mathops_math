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
 * The "neg" operator.
 * <p>
 * This operator pops a number from the stack, negates it, and returns that value to the stack.
 */
public final class NegOperator extends AbstractOperator {

    /**
     * Constructs a new {code NegOperator}.
     */
    public NegOperator() {

        super("neg");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object num1 = stack.poll();

        if (num1 == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[neg] operation executed when stack is empty.");
        }

        if (num1 instanceof Double num1Dbl) {
            final double value = num1Dbl.doubleValue();
            stack.pushDouble(-value);
        } else if (num1 instanceof Long num1Long) {
            // NOTE: we have selected the MAX and MIN long values allowed on the stack to have the same magnitude
            // so abs will never overflow (as could occur in normal two's complement numbers).
            final long value = num1Long.longValue();
            stack.pushLong(-value);
        } else {
            throw badType("Argument", num1, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("neg")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
