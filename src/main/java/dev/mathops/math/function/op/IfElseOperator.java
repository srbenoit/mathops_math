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
 * The "ifelse" operator.
 * <p>
 * This operator pops two expressions and a Boolean value from the stack, and if the Boolean is TRUE, executes the
 * second expression popped.  If the Boolean is FALSE, executes the first expression popped.
 */
public final class IfElseOperator extends AbstractOperator {

    /**
     * Constructs a new {code IfElseOperator}.
     */
    public IfElseOperator() {

        super("ifelse");
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public final void execute(final OperatorStack stack) throws OperatorException {

        final Object proc2 = stack.poll();
        final Object proc1 = stack.poll();
        final Object condition = stack.poll();

        if (proc1 == null || proc2 == null || condition == null) {
            throw new OperatorException(EOperatorExceptionType.STACK_UNDERFLOW,
                    "[ifelse] operation executed when stack had less than 3 entries.");
        }

        if (proc1 instanceof PostScriptExpression proc1Expr) {
            if (proc2 instanceof PostScriptExpression proc2Expr) {
                if (condition instanceof Boolean conditionBool) {
                    if (conditionBool.booleanValue()) {
                        proc1Expr.execute(stack);
                    } else {
                        proc2Expr.execute(stack);
                    }
                } else {
                    throw badType("Third argument ", condition, "Boolean");
                }
            } else {
                throw badType("Second argument ", proc2, "Expression");
            }
        } else {
            throw badType("First argument ", proc1, "Expression");
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation ("ifelse")
     */
    @Override
    public final String toString() {

        return this.name;
    }
}
