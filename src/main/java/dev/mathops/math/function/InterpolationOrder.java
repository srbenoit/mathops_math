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

/**
 * An interpolation order.
 */
public enum InterpolationOrder {

    /** Linear interpolation. */
    LINEAR(1),

    /** Cubic spline interpolation. */
    CUBIC(3);

    /** The degree of the interpolating polynomial. */
    public final int degree;

    /**
     * Constructs a new {code InterpolationOrder.
     *
     * @param theDegree the degree of the interpolating polynomial
     */
    InterpolationOrder(final int theDegree) {

        this.degree = theDegree;
    }

    /**
     * Generates the string representation of the object.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        return "InterpolationOrder{degree=" + this.degree + "}";
    }

    /**
     * Returns the {code InterpolationOrder} object with a specified degree.
     *
     * @param theDegree the degree
     * @return the {code InterpolationOrder} object with a specified degree; {code null} if none matches
     */
    public static InterpolationOrder forDegree(final int theDegree) {

        InterpolationOrder result = null;

        for (final InterpolationOrder test : InterpolationOrder.values()) {
            if (test.degree == theDegree) {
                result = test;
                break;
            }
        }

        return result;
    }
}
