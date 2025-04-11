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

package dev.mathops.math.function.parametric;

import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.geom.Tuple2DArray;

/**
 * A parametric curve in 2-dimensional space, which can generate a point for any parameter value within its domain.
 */
public interface IParametricCurve2Arr extends IParametricCurve {

    /**
     * Evaluates the curve to a point for a particular parameter value.
     *
     * @param t     the parameter value
     * @param array the array from which to retrieve point coordinates
     * @return the point (may or may not be null if parameter falls outside the domain)
     */
    Tuple2D evaluate(double t, Tuple2DArray array);
}
