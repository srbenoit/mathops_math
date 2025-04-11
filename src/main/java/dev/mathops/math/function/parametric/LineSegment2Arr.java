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

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.geom.Tuple2DArray;
import dev.mathops.math.set.number.RealInterval;

/**
 * A line segment in the plane from P0 to P1, parametrized over the interval [0,1], where P0 and P1 are taken from a
 * {code Pt2DArray}.  This is a Bézier curve of order 1.
 *
 * <pre>
 * P(t) = P0 (1 - t) + P1 (t)
 * </pre>
 */
public class LineSegment2Arr implements IParametricCurve2Arr {

    /** The start point, P0. */
    public final int p0Index;

    /** The end point, P1. */
    public final int p1Index;

    /**
     * Constructs a new {@code LineSegment2Arr}.
     *
     * @param theP0Index the index of the first point (returned at parameter value 0)
     * @param theP1Index the index of the second point (returned at parameter value 1)
     */
    public LineSegment2Arr(final int theP0Index, final int theP1Index) {

        this.p0Index = theP0Index;
        this.p1Index = theP1Index;
    }

    /**
     * Get the domain of the curve, which is [0, 1].
     *
     * @return the domain, as a real interval
     */
    @Override
    public final RealInterval getDomain() {

        return RealInterval.UNIT_INTERVAL;
    }

    /**
     * Evaluates the curve to a point for a particular parameter value.
     *
     * @param t     the parameter value
     * @param array the array from which to retrieve point coordinates
     * @return the point (may or may not be null if parameter falls outside the domain)
     */
    @Override
    public final Tuple2D evaluate(final double t, final Tuple2DArray array) {

        // U = P0 (1 - t) + P1 (t)

        final double u = 1.0 - t;

        final double p0x = array.getX(this.p0Index);
        final double p0y = array.getY(this.p0Index);
        final double p1x = array.getX(this.p1Index);
        final double p1y = array.getY(this.p1Index);

        return new Tuple2D(p0x * u + p1x * t, p0y * u + p1y * t, ETupleType.POINT);
    }
}
