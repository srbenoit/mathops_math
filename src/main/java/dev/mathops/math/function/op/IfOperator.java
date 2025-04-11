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
 * The "if" operator.
 * <p>
 * This operator pops an expression and a Boolean value from the stack, and if the Boolean is TRUE, executes the
 * expression.  If the Boolean is FALSE, no action is taken.
 */
public final class IfOperator extends AbstractOperator {

    /**
     * Constructs a new {code IfOperator}.
     */
    public IfOperator() {

        super("if");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object proc = stack.poll();
        final Object condition = stack.poll();

        if (proc == null || condition == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[if] operation executed when stack had less than 2 entries.");
        }

        if (proc instanceof PostScriptExpression procExpr) {
            if (condition instanceof Boolean conditionBool) {
                if (conditionBool.booleanValue()) {
                    procExpr.execute(stack);
                }
            } else {
                throw badType("Second argument ", condition, "Boolean");
            }
        } else {
            throw badType("First argument ", proc, "Expression");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("if")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
