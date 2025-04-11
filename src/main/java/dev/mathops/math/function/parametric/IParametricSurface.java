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

import java.awt.geom.Area;

/**
 * A base interface with methods common to all parametric surfaces (height fields over a 2-D domain).
 */
public interface IParametricSurface {

    /**
     * Get the domain of the surface.
     *
     * @return the domain
     */
    Area getDomain();

    /**
     * Evaluates the surface at a point.
     *
     * @param point the point
     * @return the surface height; null if the point falls outside the domain
     */
    Double evaluate(Tuple2D point);
}
