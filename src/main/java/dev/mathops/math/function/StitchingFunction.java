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
 * A stitching (Type 3) function. This function takes one input and generates N outputs.  It takes K functions and maps
 * a portion of the domain to a portion of that functions domain.
 */
public class StitchingFunction extends AbstractFunction implements IPdfFunction {

    /** The (K) functions to stitch together. */
    private final AbstractFunction[] functions;

    /** The (K - 1) bounds between piecewise segments. */
    private final double[] bounds;

    /** (K) intervals that map domain segment defined by bounds to domain of each function. */
    private final RealInterval[] encode;

    /**
     * Constructs a new {code StitchingFunction}.
     *
     * @param theNumOutputs the number of outputs (N) this function generates
     * @param theDomain     the domain of the input value
     * @param theRange      (optional) an array of N intervals giving the range of each of N output values
     * @param theFunctions  an array of K functions, all of which must be one-input and all of which must have the same
     *                      output dimension, which must agree with {code range} if present
     * @param theBounds     an array of K-1 numbers, which must be in ascending order and must all fall within the
     *                      domain
     * @param theEncode     an array of K intervals, giving the part of the domain of the corresponding function that
     *                      will map to the corresponding part of the domain of this function
     */
    public StitchingFunction(final int theNumOutputs, final RealInterval theDomain, final RealInterval[] theRange,
                             final AbstractFunction[] theFunctions, final double[] theBounds,
                             final RealInterval[] theEncode) {

        super(1, theNumOutputs, new RealInterval[]{theDomain}, theRange);

        if (theDomain == null) {
            throw new IllegalArgumentException("Domain may not be null");
        }
        if (theRange != null && theRange.length != theNumOutputs) {
            throw new IllegalArgumentException("Range must have 'numOutputs' intervals");
        }
        if (theFunctions == null || theFunctions.length == 0) {
            throw new IllegalArgumentException("Functions array may not be null or empty");
        }
        for (final AbstractFunction test : theFunctions) {
            if (test == null) {
                throw new IllegalArgumentException("Functions array may not contain null values");
            }
            if (test.getNumInputs() != 1 || test.getNumOutputs() != theNumOutputs) {
                throw new IllegalArgumentException("Each function in array must have 1 input and 'numOutputs' outputs");
            }
        }

        final int k = theFunctions.length;
        if (theBounds == null || theBounds.length != k - 1) {
            throw new IllegalArgumentException("Bounds array must be present and must contain k-1 numbers");
        }
        if (theDomain.contains(theBounds[0])) {
            for (int i = 1; i < k - 1; ++i) {
                if (theDomain.contains(theBounds[i])) {
                    if (theBounds[i] < theBounds[i - 1]) {
                        throw new IllegalArgumentException("Bounds array values must be in ascending order");
                    }
                } else {
                    throw new IllegalArgumentException("Bounds array values must fall within domain");
                }
            }
        } else {
            throw new IllegalArgumentException("Bounds array values must fall within domain");
        }
        if (theEncode == null || theEncode.length != k) {
            throw new IllegalArgumentException("Encode array must contain same number of intervals as functions");
        }
        for (int i = 0; i < k; ++i) {
            if (theEncode[i] == null) {
                throw new IllegalArgumentException("Encode array may not contain null values");
            }
        }

        this.functions = theFunctions.clone();
        this.bounds = theBounds.clone();
        this.encode = theEncode.clone();
    }

    /**
     * Gets the array of piece functions for each subdomain.
     *
     * @return the piece functions
     */
    public final AbstractFunction[] getFunctions() {

        return this.functions.clone();
    }

    /**
     * Gets the list of boundaries between subdomains of this function's domain.
     *
     * @return the bounds
     */
    public final double[] getBounds() {

        return this.bounds.clone();
    }

    /**
     * Gets the encoding from the individual pieces of this functions' domain into the domains of the piece functions.
     *
     * @return the encoding intervals
     */
    public final RealInterval[] getEncode() {

        return this.encode.clone();
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

        final int numFunctions = this.functions.length;
        int whichFunction = -1;
        double lowerBound = 0.0;
        double upperBound = 0.0;
        for (int i = 0; i < numFunctions; ++i) {
            if (i == numFunctions - 1) {
                whichFunction = i;
                lowerBound = this.bounds[numFunctions - 1];
                upperBound = domain.upperBound.doubleValue();
            } else if (x < this.bounds[i]) {
                whichFunction = i;
                lowerBound = i == 0 ? domain.lowerBound.doubleValue() : this.bounds[i - 1];
                upperBound = this.bounds[i];
            }
        }

        final RealInterval enc = this.encode[whichFunction];
        final double encoded = IFunction.interpolate(x, lowerBound, upperBound, enc.lowerBound.doubleValue(),
                enc.upperBound.doubleValue());

        return this.functions[whichFunction].evaluate(encoded);
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
        return "StitchingFunction{}";
    }
}
