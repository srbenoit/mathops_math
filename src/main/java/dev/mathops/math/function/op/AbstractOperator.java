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
 * The base class for all operators.
 */
public abstract class AbstractOperator {

    /** The operator name. */
    public final String name;

    /**
     * Constructs a new {code AbstractOperator}
     *
     * @param theName the operator name
     */
    AbstractOperator(final String theName) {

        this.name = theName;
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public abstract void execute(final OperatorStack stack) throws OperatorException;

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation
     */
    @Override
    public abstract String toString();

    /**
     * Creates an {code OperatorException} whose type is TYPE_CHECK with a constructed message.
     *
     * @param descr        the descriptor of what had the incorrect type, such as "First argument"
     * @param obj          the object whose type was incorrect
     * @param expectedType the type that was needed, such as "Long or Double"
     * @return the constructed exception
     */
    final OperatorException badType(final String descr, final Object obj, final String expectedType) {

        final Class<?> argClass = obj.getClass();
        final String argClassName = argClass.getName();

        final StringBuilder msg = new StringBuilder(80);
        msg.append(descr);
        msg.append(" to [");
        msg.append(this.name);
        msg.append("] operation was ");
        msg.append(argClassName);
        msg.append(" rather than ");
        msg.append(expectedType);
        msg.append(".");
        final String msgStr = msg.toString();

        return new OperatorException(EOperatorExceptionType.TYPE_CHECK, msgStr);
    }
}
