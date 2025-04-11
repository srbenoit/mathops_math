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
public final class WeightedSampleArray implements ISampleArray {

    /** The source array. */
    public final ISampleArray source;

    /** The start index. */
    public int startIndex;

    /** The weight. */
    public final double weight;

    /**
     * Constructs a new {code WeightedSampleArray}.
     *
     * @param theSource     the source array
     * @param theStartIndex the start index
     * @param theWeight     the weight
     */
    public WeightedSampleArray(final ISampleArray theSource, final int theStartIndex, final double theWeight) {

        this.source = theSource;
        this.startIndex = theStartIndex;
        this.weight = theWeight;
    }

    /**
     * Gets the weighted value at an index.
     *
     * @param index the index
     * @return the weighted value (the sample value at the specified index multiplied by the weight)
     */
    public final double get(final int index) {

        return this.source.get(index + this.startIndex) * this.weight;
    }
}
