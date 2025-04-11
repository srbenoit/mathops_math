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

import java.util.Arrays;
import java.util.List;

/**
 * An interpolated array that combines a collection of source arrays with weights to generate interpolated array values
 * (values are computed when requested, not pre-computed and stored).
 */
public final class InterpolatedArray implements ISampleArray {

    /** The list of source arrays to combine. */
    private final List<ISampleArray> sourceArrays;

    /**
     * Constructs a new {code InterpolatedArray}.
     *
     * @param theSourceArrays the list of weighted sample arrays to combine
     */
    public InterpolatedArray(final ISampleArray... theSourceArrays) {

        this.sourceArrays = Arrays.asList(theSourceArrays);
    }

    /**
     * Computes the interpolated value at an array index by combining the corresponding samples in each weighted array.
     *
     * @param index the index of the array entry to retrieve
     * @return the interpolated value
     */
    public double get(final int index) {

        double result = 0.0;

        for (final ISampleArray array : this.sourceArrays) {
            result += array.get(index);
        }

        return result;
    }
}
