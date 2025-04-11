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

package dev.mathops.math.function;

import dev.mathops.math.expression.Expr;
import dev.mathops.math.expression.VariableValues;
import dev.mathops.math.set.number.RealInterval;
import dev.mathops.text.builder.CharSimpleBuilder;

/**
 * A function that is based on an expression.
 */
public class ExprFunction extends AbstractFunction {

    /** The expression. */
    private final Expr expr;

    /** The independent variable. */
    private final String indepVar;

    /** Variable values used to evaluate the expression. */
    private final VariableValues values;

    /**
     * Constructs a new {code ExprFunction}.
     *
     * @param theDomain   the domain of the input value
     * @param theExpr     the expression
     * @param theIndepVar the independent variable
     */
    public ExprFunction(final RealInterval theDomain, final Expr theExpr, final String theIndepVar) {

        super(1, 1, new RealInterval[]{theDomain}, null);

        if (theDomain == null) {
            throw new IllegalArgumentException("Domain may not be null");
        }
        if (theExpr == null) {
            throw new IllegalArgumentException("Expression may not be null");
        }
        if (theIndepVar == null) {
            throw new IllegalArgumentException("Independent variable may not be null");
        }

        this.expr = theExpr;
        this.indepVar = theIndepVar;
        this.values = new VariableValues();
    }

    /**
     * Evaluates the function.
     *
     * @param arguments the arguments (the length of this array must match the number of inputs)
     * @return the result (an array whose length is the number of outputs)
     * @throws IllegalArgumentException if {code arguments} is null or not the correct length
     */
    @Override
    public double[] evaluate(final double... arguments) {

        if (arguments == null || arguments.length != 1) {
            throw new IllegalArgumentException("Number of arguments does not match number of inputs");
        }

        final RealInterval domain = getDomain(0);
        final double x = IFunction.clampToRange(arguments[0], domain.lowerBound, domain.upperBound);
        this.values.set(this.indepVar, Double.valueOf(x));

        final Number result = this.expr.eval(this.values);

        return new double[]{result.doubleValue()};
    }

    /**
     * Differentiates the function.
     *
     * @param index the index of the domain variable with respect to which to differentiate
     * @return the derivative function
     */
    @Override
    public IFunction differentiate(final int index) {

        if (index != 0) {
            throw new IllegalArgumentException("Invalid differentiation variable index.");
        }

        final RealInterval domain = getDomain(0);
        final Expr diff = this.expr.differentiate(this.indepVar);
        return new ExprFunction(domain, diff, this.indepVar);
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        final RealInterval domain = getDomain(0);

        return CharSimpleBuilder.concat("ExprFunction{domain=", domain, " expr=", this.expr, "}");
    }
}
