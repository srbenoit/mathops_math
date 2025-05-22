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

import dev.mathops.math.interpolation.Interpolator;
import dev.mathops.math.set.number.RealInterval;
import dev.mathops.commons.CoreConstants;

/**
 * A sampled function that takes any number of inputs and generates a single output.  A PDF sampled function (which can
 * generate multiple outputs) is simply a sequence of these one-output functions.
 */
public class SampledFunction extends AbstractFunction implements IPdfFunction {

    /** The maximum number of total samples. */
    public static final long MAX_SAMPLES = 1_999_999_999L;

    /** The interpolation order. */
    public final InterpolationOrder order;

    /** The number of samples in each input variable. */
    private final int[] samplesPerInput;

    /** The sample data as a linear array, where the first input variable index varies fastest. */
    private final double[] samples;

    /**
     * Constructs a new {code SampledFunction}.  This type of function can take any number of inputs and can produce any
     * number of outputs.
     *
     * @param theDomains         the domains of each input value
     * @param theRange           an array of N intervals  giving the range of each output value
     * @param theOrder           the order of interpolation between samples (LINEAR assumed if not provided)
     * @param theSamplesPerInput an array whose length is the number of input variables and whose entries are the number
     *                           of samples in that input axis (must be at least 1)
     * @param theSampleData      the sample data.
     */
    public SampledFunction(final RealInterval[] theDomains, final RealInterval theRange,
                           final InterpolationOrder theOrder, final int[] theSamplesPerInput,
                           final double[] theSampleData) {

        super(theDomains.length, 1, theDomains, new RealInterval[]{theRange});

        if (theSamplesPerInput.length != theDomains.length) {
            throw new IllegalArgumentException("Length of samples per input array must match domain array");
        }

        this.order = theOrder;
        this.samplesPerInput = theSamplesPerInput.clone();

        long total = 1;
        for (final int axis : theSamplesPerInput) {
            if (axis < 1) {
                throw new IllegalArgumentException("Number of samples per input must be at least 1 in each axis");
            }
            total = total * (long) axis;
            if (total > MAX_SAMPLES) {
                throw new IllegalArgumentException("Number of samples needed exceeds maximum allowed");
            }
        }

        if (theSampleData == null || (long) theSampleData.length != total) {
            throw new IllegalArgumentException("Expected " + total + " samples; " + theSampleData.length
                    + " were provided");
        }

        for (final double test : theSampleData) {
            if (!Double.isFinite(test)) {
                throw new IllegalArgumentException("Samples must be finite.");
            }
        }

        this.samples = theSampleData.clone();
    }

    /**
     * Gets the number of samples for an input axis.
     *
     * @param index the index of an input variable
     * @return the number if samples along that variable's axis
     */
    public final int getSamplesPerInput(final int index) {

        return this.samplesPerInput[index];
    }

    /**
     * Gets the interpolation order.
     *
     * @return the order
     */
    public final InterpolationOrder getOrder() {

        return this.order;
    }

    /**
     * Gets the sample data as a linear array, where the first input variable index varies fastest.
     *
     * @return the sample data
     */
    public final double[] getSamples() {

        return this.samples.clone();
    }

    /**
     * Evaluates the function.
     *
     * @param arguments the arguments (the length of this array must match the number of inputs)
     * @return the result (an array whose length is the number of outputs)
     * @throws IllegalArgumentException if {code arguments} is null or not the correct length
     */
    @Override
    public final double[] evaluate(final double... arguments) {

        final int numInputs = getNumInputs();
        if (arguments == null || arguments.length != numInputs) {
            throw new IllegalArgumentException("Number of arguments does not match number of inputs");
        }

        // Clamp inputs to domain, and scale inputs to 0.0 to N.0 where N is the number of samples in each axis.
        // FLOOR of the scaled value is the index of the interpolation segment, and the fractional part is the
        // interpolation parameter within that segment.
        final double[] clampedToDomain = new double[numInputs];
        final double[] scaled = new double[numInputs];
        for (int i = 0; i < numInputs; ++i) {
            final RealInterval dom = getDomain(i);
            clampedToDomain[i] = IFunction.clampToRange(arguments[i], dom.lowerBound, dom.upperBound);
            scaled[i] = (double) this.samplesPerInput[i] * (clampedToDomain[i] - dom.lowerBound.doubleValue())
                        / (dom.upperBound.doubleValue() - dom.lowerBound.doubleValue());
        }

        final double result = Interpolator.interpolate(this.samplesPerInput, this.samples, scaled, this.order);

        return new double[]{result};
    }

    /**
     * Gets the index in the sample data for the sample with a specified "address", which is a sequence of integers in
     * each input variable.  An address of 0, ..., 0 gives the first sample (index = 0), and the highest possible
     * address is N1, N2, ..., Nk, where Ni is one greater than the number of samples in the i-th input axis.
     *
     * @param address the address
     * @return the sample index
     */
    private int sampleIndex(final int[] address) {

        int multiplier = 1;
        int result = 0;

        for (int i = 0; i < address.length; ++i) {
            result += address[i] * multiplier;
            multiplier *= this.samplesPerInput[i];
        }

        return result;
    }

    /**
     * Computes the derivative of the function with respect to a single input variable.
     *
     * @param index the index of the domain variable with respect to which to differentiate
     * @return the differentiated function
     */
    @Override
    public IFunction differentiate(final int index) {

        // TODO: seems hard!

        return null;
    }

    /**
     * Generates the string representation of the function.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final StringBuilder builder = new StringBuilder(100);

        builder.append("{SampledFunction Domains=[");
        final int numInputs = getNumInputs();
        for (int i = 0; i < numInputs; ++i) {
            final RealInterval domain = getDomain(i);
            if (i > 0) {
                builder.append(CoreConstants.SPC);
            }
            builder.append(domain);
        }

        builder.append("] Range=");
        final RealInterval range = getRange(0);
        builder.append(range);


        builder.append(" SamplesPerInput=[");
        for (int i = 0; i < numInputs; ++i) {
            final int count = this.samplesPerInput[i];
            if (i > 0) {
                builder.append(CoreConstants.SPC);
            }
            builder.append(count);
        }
        builder.append("]}");

        return builder.toString();
    }
}
