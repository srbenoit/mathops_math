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
 * A base clas for functions.  Functions are general in that they can accept any number of inputs and generate any
 * numbers of outputs (they are vector-valued functions of a vector argument), but a special case of this is an ordinary
 * real function of a real argument.
 */
public abstract class AbstractFunction implements IFunction {

    /** The number of input values this function accepts. */
    private final int numInputs;

    /** The number of output values this function generates. */
    private final int numOutputs;

    /** The domain of each input variable (required). */
    private final RealInterval[] domain;

    /** The range of each output variable (optional for some types of function). */
    private final RealInterval[] range;

    /**
     * Constructs a new {code PdfFunction}.
     *
     * @param theNumInputs the number of input values this function accepts
     * @param theNumOutputs the number of output values this function generates
     * @param theDomain the domain of each input variable
     * @param theRange the range of each output variable (optional for some function types)
     * @throws IllegalArgumentException if either {code theNumInputs} or {code theNumOutputs} is less than 1,
     *         {code theDomain} array is {code null}, is length is not {code theNumInputs}, or it contains a {code null}
     *         entry or if {code theRange} array (if present) has length that is not {code theNumOutputs}, or contains a
     *         {code null} entry.
     */
    AbstractFunction(final int theNumInputs, final int theNumOutputs, final RealInterval[] theDomain,
                     final RealInterval[] theRange) throws IllegalArgumentException {

        super();

        if (theNumInputs < 1) {
            throw new IllegalArgumentException("Number of inputs must be at least 1");
        }
        if (theNumOutputs < 1) {
            throw new IllegalArgumentException("Number of outputs must be at least 1");
        }
        if (theDomain == null) {
            throw new IllegalArgumentException("Domain is required");
        }
        if (theDomain.length != theNumInputs) {
            throw new IllegalArgumentException("Domain must have an interval for each input");
        }
        for (final RealInterval domInt : theDomain) {
            if (domInt == null) {
                throw new IllegalArgumentException("Domain array may not contain nulls");
            }
        }

        RealInterval[] actualRange = null;
        if (theRange != null) {
            if (theRange.length != theNumOutputs) {
                throw new IllegalArgumentException("Range must have an interval for each output");
            }
            for (final RealInterval rangeInt : theRange) {
                if (rangeInt == null) {
                    throw new IllegalArgumentException("Range array may not contain nulls");
                }
            }
            actualRange = theRange.clone();
        }

        this.numInputs = theNumInputs;
        this.numOutputs = theNumOutputs;
        this.domain = theDomain.clone();
        this.range = actualRange;
    }

    /**
     * Gets the number of inputs this function accepts.
     *
     * @return the number of inputs
     */
    public final int getNumInputs() {

        return this.numInputs;
    }

    /**
     * Gets the number of outputs this function produces.
     *
     * @return the number of outputs
     */
    public final int getNumOutputs() {

        return this.numOutputs;
    }

    /**
     * Gets the domain of one input variable.
     *
     * @param inputIndex the input index (from 0 to one less than the value provided in the constructor)
     * @return the variable domain ({code null} if not yet set)
     */
    public final RealInterval getDomain(final int inputIndex) {

        return this.domain[inputIndex];
    }

    /**
     * Gets the range of one output variable.
     *
     * @param outputIndex the output index (from 0 to one less than the value provided in the constructor)
     * @return the variable range ({code null} if not yet set)
     */
    public final RealInterval getRange(final int outputIndex) {

        return this.range[outputIndex];
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public abstract String toString();
}
