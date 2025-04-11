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
 * The "bitshift" operator.
 * <p>
 * This operator pops two numbers from the stack, computes the inverse tangent of the first ("y") divided by the second
 * ("x"), with the result in the quadrant indicated by that (x, y) point, and returns that value to the stack.  This
 * corresponds to the "atan2" function in C/C++/Java.
 */
public final class BitshiftOperator extends AbstractOperator {

    /**
     * Constructs a new {code BitshiftOperator}.
     */
    public BitshiftOperator() {

        super("bitshift");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object shift = stack.poll();
        final Object toShift = stack.poll();

        if (toShift == null || shift == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[bitshift] operation executed when stack had less than 2 entries.");
        }

        if (toShift instanceof Long toShiftLong) {
            if (shift instanceof Long shiftLong) {
                final long num = toShiftLong.longValue();
                final long shiftValue = shiftLong.longValue();
                if (shiftValue == 0L) {
                    stack.pushLong(num);
                } else if (shiftValue > 24L || shiftValue < -24L) {
                    // All bits will be shifted out, zeros shifted in
                    stack.pushLong(0L);
                } else {
                    final int intShift = (int) shiftValue;
                    final long shifted = intShift > 0 ? num << intShift : num >> intShift;
                    final long result = shifted < OperatorStack.MIN_INTEGER || shifted > OperatorStack.MAX_INTEGER ?
                            0L : shifted;
                    stack.pushLong(result);
                }
            } else {
                throw badType("Second argument", shift, "Long");
            }
        } else {
            throw badType("First argument", toShift, "Long");
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
