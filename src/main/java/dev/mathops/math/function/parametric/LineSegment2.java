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
import dev.mathops.math.set.number.RealInterval;

/**
 * A line segment in the plane from P0 to P1, parametrized over the interval [0,1].  This is also a Bezier curve of
 * order 1.
 *
 * <pre>
 * P(t) = P0 (1 - t) + P1 (t)
 * </pre>
 */
public class LineSegment2 implements IParametricCurve2 {

    /** The start point, P0. */
    public final Tuple2D p0;

    /** The end point, P1. */
    public final Tuple2D p1;

    /**
     * Constructs a new {@code LineSegment2}.
     *
     * @param theP0 the first point (returned at parameter value 0)
     * @param theP1 the second point (returned at parameter value 1)
     */
    public LineSegment2(final Tuple2D theP0, final Tuple2D theP1) {

        this.p0 = theP0.as(ETupleType.POINT);
        this.p1 = theP1.as(ETupleType.POINT);
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
     * @param t the parameter value
     * @return the point (may or may not be null if parameter falls outside the domain)
     */
    @Override
    public final Tuple2D evaluate(final double t) {

        // U = P0 (1 - t) + P1 (t)

        final double u = 1.0 - t;

        return new Tuple2D(this.p0.x * u + this.p1.x * t, this.p0.y * u + this.p1.y * t, ETupleType.POINT);
    }
}
