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

import java.util.List;

/**
 * A Bezier quadratic curve in the plane, from P0 to P2 with control point P1, parametrized over the interval [0,1].
 *
 * <pre>
 * P(t) = P0 (1 - t)^2 + P1 (2 t)(1 - t) + P2 t^2
 * </pre>
 * <p>
 * The tangent direction at P0 is parallel to the line from P0 to P1. The tangent direction at P2 is parallel to the
 * line from P1 to P2. The curve is contained in the triangle (convex hull) formed by P0, P1, and P2.
 */
public class BezierQuadratic2 implements IParametricCurve2 {

    /** A commonly used constant. */
    public static final double HALF = 0.5;

    /** The start point, P0. */
    public final Tuple2D p0;

    /** The control point, P1. */
    public final Tuple2D p1;

    /** The end point, P2. */
    public final Tuple2D p2;

    /**
     * Constructs a new {@code BezierQuadratic2}.
     *
     * @param theP0 the start point (returned at parameter value 0)
     * @param theP1 the control point
     * @param theP2 the end point (returned at parameter value 1)
     */
    public BezierQuadratic2(final Tuple2D theP0, final Tuple2D theP1, final Tuple2D theP2) {

        this.p0 = theP0.as(ETupleType.POINT);
        this.p1 = theP1.as(ETupleType.POINT);
        this.p2 = theP2.as(ETupleType.POINT);
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

        // P(t) = P0 (1 - t)^2 + P1 (2 t)(1 - t) + P2 t^2

        final double u = 1.0 - t;
        final double tt = t * t;
        final double tu2 = 2.0 * t * u;
        final double uu = u * u;

        return new Tuple2D(this.p0.x * uu + this.p1.x * tu2 + this.p2.x * tt,
                this.p0.y * uu + this.p1.y * tu2 + this.p2.y * tt, ETupleType.POINT);
    }

    /**
     * Returns a cubic representation of this curve.
     *
     * <pre>
     *     P(t) = (1-t)^2 Pt1 + 2t(1-t) C + t^2 Pt2
     *
     *     P(t) = t P(t) + (1-t) P(t)
     *          = t(1-t)^2 Pt1 + 2t^2(1-t) C + t^3 Pt2 + (1-t)^3 Pt1 + 2t(1-t)^2 C + t^2(1-t) Pt2
     *          = (1-t)^3 Pt1 + t(1-t)^2 Pt1 + 2t(1-t)^2 C + t^2(1-t) Pt2 + 2t^2(1-t) C + t^3 Pt2
     *          = (1-t)^3 Pt1 + t(1-t)^2 (Pt1 + 2C) + t^2(1-t) (Pt2 + 2C) + t^3 Pt2
     *          = (1-t)^3 Pt1 + 3t(1-t)^2 (Pt1 + 2C)/3 + 3t^2(1-t) (Pt2 + 2C)/3 + t^3 Pt2
     *
     *     P(t) = Cubic with same starting/ending points, and control points C1 and C2 defined by
     *            C1 = (Pt1 + 2C) / 3
     *            C2 = (Pt2 + 2C) / 3
     * </pre>
     *
     * @return the cubic representation
     */
    public final BezierCubic2 asCubic() {

        final double c1x = (this.p0.x + 2.0 * this.p1.x) / 3.0;
        final double c1y = (this.p0.y + 2.0 * this.p1.y) / 3.0;
        final double c2x = (this.p2.x + 2.0 * this.p1.x) / 3.0;
        final double c2y = (this.p2.y + 2.0 * this.p1.y) / 3.0;

        final Tuple2D c1 = new Tuple2D(c1x, c1y, ETupleType.POINT);
        final Tuple2D c2 = new Tuple2D(c2x, c2y, ETupleType.POINT);

        return new BezierCubic2(this.p0, c1, c2, this.p2);
    }

    /**
     * Recursively approximates a segment of a quadratic with one or more circular arcs.  The resulting curve is
     * continuous, but its tangent vector will not be continuous at arc boundaries.
     *
     * @param arcs     the list to which to add arcs
     * @param maxError the (approximate) maximum error
     */
    public void approximateWithCircularArcs(final List<CircularArc2> arcs, final double maxError) {

        double mid = HALF;
        double high = 1.0;
        double tween1 = mid * HALF;
        double tween2 = mid + tween1;

        while (true) {
            final Tuple2D pt2 = evaluate(tween1);
            final Tuple2D pt3 = evaluate(mid);
            final Tuple2D pt4 = evaluate(tween2);
            final Tuple2D pt5 = evaluate(high);
            final CircularArc2 arc = CircularArc2.throughThreePoints(this.p0, pt3, pt5);
            final double err1 = Tuple2D.distance(arc.center, pt2);

            if (err1 < maxError) {
                final double err2 = Tuple2D.distance(arc.center, pt4);
                if (err2 < maxError) {
                    arcs.add(arc);
                    break;
                }
            }

            tween1 *= HALF;
            mid *= HALF;
            tween2 *= HALF;
            high *= HALF;
        }

        if (high < 1.0) {
            // Subdivide at "high", use region from z = high to 1.0
            final double zSq = high * high;
            final double zMinus1 = high - 1.0;
            final double zMinus1Sq = zMinus1 * zMinus1;
            final double twoZZMinus1 = 2.0 * high * zMinus1;

            final double x0 = zSq * this.p2.x - twoZZMinus1 * this.p1.x + zMinus1Sq * this.p0.x;
            final double y0 = zSq * this.p2.y - twoZZMinus1 * this.p1.y + zMinus1Sq * this.p0.y;

            final double x1 = high * this.p2.x - zMinus1 * this.p1.x;
            final double y1 = high * this.p2.y - zMinus1 * this.p1.y;

            final BezierQuadratic2 remainder = new BezierQuadratic2(new Tuple2D(x0, y0, ETupleType.POINT),
                    new Tuple2D(x1, y1, ETupleType.POINT), this.p2);
            remainder.approximateWithCircularArcs(arcs, maxError);
        }
    }
}
