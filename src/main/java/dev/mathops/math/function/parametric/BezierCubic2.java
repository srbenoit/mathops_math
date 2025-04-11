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
 * A Bezier cubic curve in the plane, from P0 to P3 with control points P1 and P2, parametrized over the interval
 * [0,1].
 *
 * <pre>
 * P(t) = P0 (1 - t)^3 + P1 (3 t)(1 - t)^2 + P2 (3 t^2)(1 - t) + P3 t^3
 * </pre>
 *
 * <p>
 * The tangent direction at P0 is parallel to the line from P0 to P1. The tangent direction at P3 is parallel to the
 * line from P2 to P3. The curve is contained in the convex hull formed by P0, P1, P2, and P3.
 */
public class BezierCubic2 implements IParametricCurve2 {

    /** The start point, P0. */
    public final Tuple2D p0;

    /** The first control point, P1. */
    public final Tuple2D p1;

    /** The second control point, P2. */
    public final Tuple2D p2;

    /** The end point, P3. */
    public final Tuple2D p3;

    /**
     * Constructs a new {code BezierCubic2}
     *
     * @param theP0 the start point (returned at parameter value 0)
     * @param theP1 the first control point
     * @param theP2 the second control point
     * @param theP3 the end point (returned at parameter value 1)
     */
    public BezierCubic2(final Tuple2D theP0, final Tuple2D theP1, final Tuple2D theP2, final Tuple2D theP3) {

        this.p0 = theP0.as(ETupleType.POINT);
        this.p1 = theP1.as(ETupleType.POINT);
        this.p2 = theP2.as(ETupleType.POINT);
        this.p3 = theP3.as(ETupleType.POINT);
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

        // P(t) = P0 (1 - t)^3 + P1 (3 t)(1 - t)^2 + P3 (3 t^2)(1 - t) + P3 t^3

        final double u = 1.0 - t;
        final double ttt = t * t * t;
        final double ttu3 = 3.0 * t * t * u;
        final double tuu3 = 3.0 * t * u * u;
        final double uuu = u * u * u;

        return new Tuple2D(//
                this.p0.x * uuu + this.p1.x * tuu3 + this.p2.x * ttu3 + this.p3.x * ttt, //
                this.p0.y * uuu + this.p1.y * tuu3 + this.p2.y * ttu3 + this.p3.y * ttt,
                ETupleType.POINT);
    }
}
