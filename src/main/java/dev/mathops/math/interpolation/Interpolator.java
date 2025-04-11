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

package dev.mathops.math.interpolation;

import dev.mathops.math.function.InterpolationOrder;

/**
 * An interpolator that can take an array (of arbitrary dimension) of sample data and an array of input values, each
 * from 0.0 to N.0 where there are N + 1 samples in that input variable.  Interpolation can be linear (order 1) or cubic
 * (order 3).
 */
public enum Interpolator {
    ;

    /**
     * Performs interpolation.
     *
     * @param samplesPerInput     the number of samples in each input axis
     * @param sampleData          the sample data (the number of samples will be the
     * @param inputVariableValues the input variable values
     * @param order               the desired interpolation order (of CUBIC order is given, but the number of samples in
     *                            an input variable is less than 4, LINEAR will be used in that variable)
     * @return the interpolated value
     */
    public static double interpolate(final int[] samplesPerInput, final double[] sampleData,
                                     final double[] inputVariableValues, final InterpolationOrder order) {

        final int dim = inputVariableValues.length;

        int stride = 1;
        for (int i = 0; i < dim - 1; ++i) {
            stride *= samplesPerInput[i];
        }

        ISampleArray currentArray = new RawSampleArray(sampleData);

        for (int i = dim - 1; i > 0; --i) {
            final double value = inputVariableValues[i];
            final double floor = Math.floor(value);
            final int intFloor = (int) floor;
            final double remainder = value - floor;

            final int numSamples = samplesPerInput[i];
            if (order == InterpolationOrder.LINEAR || numSamples < 4) {
                // Perform linear interpolation
                final int lowerIndex = stride * intFloor;
                final int upperIndex = lowerIndex + stride;
                final WeightedSampleArray lower = new WeightedSampleArray(currentArray, lowerIndex, 1.0 - remainder);
                final WeightedSampleArray upper = new WeightedSampleArray(currentArray, upperIndex, remainder);
                currentArray = new InterpolatedArray(lower, upper);
            } else {
                // Perform cubic interpolation
                if (intFloor == 0) {
                    // Variable value must be in the lower third
                    final double t = remainder / 3.0;
                    final double oneMT = 1.0 - t;
                    final double tSq = t * t;
                    final double tCb = t * t * t;
                    final double oneMTSq = oneMT * oneMT;
                    final double oneMTCb = oneMTSq * oneMT;

                    final WeightedSampleArray p0 = new WeightedSampleArray(currentArray, 0, oneMTCb);
                    final WeightedSampleArray p1 = new WeightedSampleArray(currentArray, stride, oneMTSq * t);
                    final WeightedSampleArray p2 = new WeightedSampleArray(currentArray, stride * 2, oneMT * tSq);
                    final WeightedSampleArray p3 = new WeightedSampleArray(currentArray, stride * 3, tCb);
                    currentArray = new InterpolatedArray(p0, p1, p2, p3);
                } else if (intFloor + 1 == numSamples) {
                    // Variable value  must be in the upper third
                    final double t = (2.0 + remainder) / 3.0;
                    final double oneMT = 1.0 - t;
                    final double tSq = t * t;
                    final double tCb = t * t * t;
                    final double oneMTSq = oneMT * oneMT;
                    final double oneMTCb = oneMTSq * oneMT;
                    final int start = stride * (numSamples - 4);

                    final WeightedSampleArray p0 = new WeightedSampleArray(currentArray, start, oneMTCb);
                    final WeightedSampleArray p1 = new WeightedSampleArray(currentArray, start + stride, oneMTSq * t);
                    final WeightedSampleArray p2 = new WeightedSampleArray(currentArray, start + stride * 2,
                            oneMT * tSq);
                    final WeightedSampleArray p3 = new WeightedSampleArray(currentArray, start + stride * 3, tCb);
                    currentArray = new InterpolatedArray(p0, p1, p2, p3);
                } else {
                    // Variable value can fall in center third
                    final double t = (1.0 + remainder) / 3.0;
                    final double oneMT = 1.0 - t;
                    final double tSq = t * t;
                    final double tCb = t * t * t;
                    final double oneMTSq = oneMT * oneMT;
                    final double oneMTCb = oneMTSq * oneMT;
                    final int start = stride * (intFloor - 1);

                    final WeightedSampleArray p0 = new WeightedSampleArray(currentArray, start, oneMTCb);
                    final WeightedSampleArray p1 = new WeightedSampleArray(currentArray, start + stride, oneMTSq * t);
                    final WeightedSampleArray p2 = new WeightedSampleArray(currentArray, start + stride * 2,
                            oneMT * tSq);
                    final WeightedSampleArray p3 = new WeightedSampleArray(currentArray, start + stride * 3, tCb);
                    currentArray = new InterpolatedArray(p0, p1, p2, p3);
                }
            }

            stride /= samplesPerInput[i];
        }

        // The loop exits when we're down to a single variable - "currentArray" is now treated as one-dimensional

        int numLastSamples = samplesPerInput[0];
        final double value = inputVariableValues[0];
        final double floor = Math.floor(value);
        final int intFloor = (int) floor;
        final double remainder = value - floor;
        double result;

        if (order == InterpolationOrder.LINEAR || numLastSamples < 4) {
            // Perform linear interpolation
            final double lower = currentArray.get(intFloor);
            final double upper = currentArray.get(intFloor + 1);
            result = lower * (1.0 - remainder) + upper * remainder;
        } else {
            // Perform cubic interpolation
            if (intFloor == 0) {
                // Variable value must be in the lower third
                final double t = remainder / 3.0;
                final double oneMT = 1.0 - t;
                final double tSq = t * t;
                final double tCb = t * t * t;
                final double oneMTSq = oneMT * oneMT;
                final double oneMTCb = oneMTSq * oneMT;

                final double p0 = currentArray.get(0) * oneMTCb;
                final double p1 = currentArray.get(1) * oneMTSq * t;
                final double p2 = currentArray.get(2) * oneMT * tSq;
                final double p3 = currentArray.get(3) * tCb;
                result = p0 + p1 + p2 + p3;
            } else if (intFloor + 1 == numLastSamples) {
                // Variable value  must be in the upper third
                final double t = (2.0 + remainder) / 3.0;
                final double oneMT = 1.0 - t;
                final double tSq = t * t;
                final double tCb = t * t * t;
                final double oneMTSq = oneMT * oneMT;
                final double oneMTCb = oneMTSq * oneMT;
                final int start = numLastSamples - 4;

                final double p0 = currentArray.get(start) * oneMTCb;
                final double p1 = currentArray.get(start + 1) * oneMTSq * t;
                final double p2 = currentArray.get(start + 2) * oneMT * tSq;
                final double p3 = currentArray.get(start + 3) * tCb;
                result = p0 + p1 + p2 + p3;
            } else {
                // Variable value can fall in center third
                final double t = (1.0 + remainder) / 3.0;
                final double oneMT = 1.0 - t;
                final double tSq = t * t;
                final double tCb = t * t * t;
                final double oneMTSq = oneMT * oneMT;
                final double oneMTCb = oneMTSq * oneMT;
                final int start = intFloor - 1;

                final double p0 = currentArray.get(start) * oneMTCb;
                final double p1 = currentArray.get(start + 1) * oneMTSq * t;
                final double p2 = currentArray.get(start + 2) * oneMT * tSq;
                final double p3 = currentArray.get(start + 3) * tCb;
                result = p0 + p1 + p2 + p3;
            }
        }

        return result;
    }
}
