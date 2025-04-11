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
 * The "cos" operator.
 * <p>
 * This operator pops a number from the stack, computes its cosine (interpreting the number as an angle in degrees), and
 * returns that value to the stack.
 */
public final class CosOperator extends AbstractOperator {

    /**
     * Constructs a new {code CosOperator}.
     */
    public CosOperator() {

        super("cos");
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
                    "[cos] operation executed when stack is empty.");
        }

        if (arg instanceof Double argDbl) {
            final double value = argDbl.doubleValue();
            final double radians = Math.toRadians(value);
            final double cosValue = Math.cos(radians);
            stack.pushDouble(cosValue);
        } else if (arg instanceof Long argLong) {
            // NOTE: we have selected the MAX and MIN long values allowed on the stack to have the same magnitude
            // so abs will never overflow (as could occur in normal two's complement numbers).
            final long value = argLong.longValue();
            if (value == 0.0) {
                stack.pushDouble(1.0);
            } else if (value == 90.0 || value == 270.0) {
                stack.pushDouble(0.0);
            } else if (value == 180.0) {
                stack.pushDouble(-1.0);
            } else {
                final double doubleValue = argLong.doubleValue();
                final double radians = Math.toRadians(doubleValue);
                final double cosValue = Math.cos(radians);
                stack.pushDouble(cosValue);
            }
        } else {
            throw badType("Argument ", arg, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("cos")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
