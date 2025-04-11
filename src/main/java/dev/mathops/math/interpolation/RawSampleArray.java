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

/**
 * A sample array within a larger block of multi-dimensional sample data.
 */
public final class RawSampleArray implements ISampleArray {

    /** The sample data. */
    public final double[] samples;

    /**
     * Constructs a new {code RawSampleArray}.
     *
     * @param theSamples the samples
     */
    public RawSampleArray(final double[] theSamples) {

        this.samples = theSamples;
    }

    /**
     * Gets the value at an index.
     *
     * @param index the index
     * @return the value
     */
    public final double get(final int index) {

        return this.samples[index];
    }
}
