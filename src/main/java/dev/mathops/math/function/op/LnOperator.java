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
 * The "ln" operator.
 * <p>
 * This operator pops a number from the stack, computes its natural logarithm, and returns that value to the stack.
 */
public final class LnOperator extends AbstractOperator {

    /**
     * Constructs a new {code LnOperator}.
     */
    public LnOperator() {

        super("ln");
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
                    "[ln] operation executed when stack is empty.");
        }

        if (arg instanceof Number argNbr) {
            final double value = argNbr.doubleValue();
            final double lnValue = Math.log(value);
            stack.pushDouble(lnValue);
        } else {
            throw badType("Argument", arg, "Number");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("ln")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
