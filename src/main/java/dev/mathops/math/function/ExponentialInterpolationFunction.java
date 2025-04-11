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

import dev.mathops.math.set.number.RealInterval;

/**
 * An exponential interpolation (type 2) function.  This function takes one input value and generates N output values.
 */
public class ExponentialInterpolationFunction extends AbstractFunction implements IPdfFunction {

    /** The function value for each output when x = 0.0. */
    private final double[] valueAt0;

    /** The function value for each output when x = 1.0. */
    private final double[] valueAt1;

    /** The interpolation exponent. */
    private final double exponent;

    /**
     * Constructs a new {code ExponentialInterpolationFunction}.  This type of function only accepts one input.
     *
     * @param theNumOutputs the number of outputs (N) this function generates
     * @param theDomain     the domain of the input value
     * @param theRange      an array of N intervals giving the range of each of N output values (optional)
     * @param theC0         an array of M numbers giving the function result when x = 0.0 (assumed to contain all 0.0
     *                      values if absent)
     * @param theC1         an array of M numbers giving the function result when x = 1.0 (assumed to contain all 1.0
     *                      values if absent)
     * @param theN          the interpolation exponent
     */
    public ExponentialInterpolationFunction(final int theNumOutputs, final RealInterval theDomain,
                                            final RealInterval[] theRange, final double[] theC0, final double[] theC1,
                                            final double theN) {

        super(1, theNumOutputs, new RealInterval[]{theDomain}, theRange);

        if (theC0 != null && theC0.length != theNumOutputs) {
            throw new IllegalArgumentException("C0 array must have 'numOutputs' values");
        }
        if (theC1 != null && theC1.length != theNumOutputs) {
            throw new IllegalArgumentException("C1 array must have 'numOutputs' values");
        }

        this.valueAt0 = theC0 == null ? null : theC0.clone();
        this.valueAt1 = theC1 == null ? null : theC1.clone();
        this.exponent = theN;
    }

    /**
     * Gets the array of output values when the input value is 0.0.
     *
     * @return the values at 0.0
     */
    public final double[] getValuesAt0() {

        return this.valueAt0.clone();
    }

    /**
     * Gets the array of output values when the input value is 1.0.
     *
     * @return the values at 1.0
     */
    public final double[] getValuesAt1() {

        return this.valueAt1.clone();
    }

    /**
     * Gets the exponent.
     *
     * @return the exponent
     */
    public final double getExponent() {

        return this.exponent;
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

        final int numOutputs = getNumOutputs();
        final double[] result = new double[numOutputs];

        final double x = arguments[0];
        final double xn = Math.pow(x, this.exponent);

        for (int outVar = 0; outVar < numOutputs; ++outVar) {
            final double c0 = this.valueAt0 == null ? 0.0 : this.valueAt0[outVar];
            final double c1 = this.valueAt1 == null ? 1.0 : this.valueAt1[outVar];
            result[outVar] = c0 + xn * (c1 - c0);

            final RealInterval range = getRange(outVar);
            if (range != null) {
                result[outVar] = IFunction.clampToRange(result[outVar], range.lowerBound, range.upperBound);
            }
        }

        return result;
    }

    @Override
    public IFunction differentiate(int index) {
        return null;
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        // TODO:
        return "ExponentialInterpolationFunction{}";
    }
}
