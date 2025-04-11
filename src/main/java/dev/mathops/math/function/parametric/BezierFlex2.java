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
 * A "flex" curve, which consists of two Bezier cubic curves in the plane (the first characterized by point P0, P1, P2,
 * and P3, and the second by Q0, Q1, Q2, and Q3), where P3 = Q0 (called the "join point"), parametrized over the
 * interval [0, 1].
 *
 * <p>The curve also includes a "flex depth" parameter.  When scaled to device space for grid fitting or rendering, if
 * the distance (min of x or y offset) from the join point to the line between the endpoints (called the "flex distance"
 * is less than the flex depth (in device pixels), the curve is rendered as a straight line connecting the end points
 * rather than as cubic curves.
 *
 * <pre>
 * IF (flex distance < flex depth) {
 *     P(t) = P0 (1 - t) + Q3 (t)
 * } ELSE IF (t < 0.5) {
 *     LET T = 2t;
 *     P(t) = {P0 (1 - T)^3 + P1 (3 T)(1 - T)^2 + P2 (3 T^2)(1 - t) + P3 T^3
 * } ELSE {
 *     LET T = 2t - 1;
 *     P(t) = {Q0 (1 - T)^3 + Q1 (3 T)(1 - T)^2 + Q2 (3 T^2)(1 - T) + Q3 T^3
 * }
 * </pre>
 *
 * <p>
 * Note that the tangent direction at P0 and Q3 may differ depending on whether the flex depth condition is met unless
 * control points P1 and Q2 lie on the line segment between P0 and Q3.
 */
public class BezierFlex2 implements IParametricCurve2 {

    /** The start point, P0. */
    public final Tuple2D p0;

    /** The first control point of the first curve, P1. */
    public final Tuple2D p1;

    /** The second control point of the first curve, P2. */
    public final Tuple2D p2;

    /** The join point, P3 = Q0. */
    public final Tuple2D p3q0;

    /** The first control point of the second curve, Q1. */
    public final Tuple2D q1;

    /** The second control point of the second curve, Q2. */
    public final Tuple2D q2;

    /** The end point, Q3. */
    public final Tuple2D q3;

    /** The flex depth, in device pixels (0.5 is a typical value). */
    private final double flexDepth;

    /**
     * Constructs a new {code BezierCubic2}
     *
     * @param theP0        the start point (returned at parameter value 0)
     * @param theP1        the first control point of the first curve
     * @param theP2        the second control point of the first curve
     * @param theP3Q0      the join point
     * @param theQ1        the first control point of the second curve
     * @param theQ2        the second control point of the second curve
     * @param theQ3        the end point (returned at parameter value 1)
     * @param theFlexDepth the flex depth, in device pixels
     */
    public BezierFlex2(final Tuple2D theP0, final Tuple2D theP1, final Tuple2D theP2, final Tuple2D theP3Q0,
                       final Tuple2D theQ1, final Tuple2D theQ2, final Tuple2D theQ3, final double theFlexDepth) {

        this.p0 = theP0.as(ETupleType.POINT);
        this.p1 = theP1.as(ETupleType.POINT);
        this.p2 = theP2.as(ETupleType.POINT);
        this.p3q0 = theP3Q0.as(ETupleType.POINT);
        this.q1 = theQ1.as(ETupleType.POINT);
        this.q2 = theQ2.as(ETupleType.POINT);
        this.q3 = theQ3.as(ETupleType.POINT);
        this.flexDepth = theFlexDepth;
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
     * Gets the flex depth, which is the minimum flex distance (the nearest horizontal or vertical distance, in device
     * pixels, from the join point to the line connecting the two endpoints) for which the curve should be rendered as
     * two cubic curves.  Below this distance, the curve should be rendered as a simple line connecting the endpoints.
     *
     * @return the flex depth
     */
    public final double getFlexDepth() {

        return this.flexDepth;
    }

    /**
     * Evaluates the curve to a point for a particular parameter value.  This method returns the point on the two cubic
     * curves, not the linearization that should be substituted when flex distance is less than flex depth.
     *
     * @param t the parameter value
     * @return the point (may or may not be null if parameter falls outside the domain)
     */
    @Override
    public final Tuple2D evaluate(final double t) {

        // IF (t < 0.5) {
        //     LET T = 2t;
        //     P(t) = {P0 (1 - T)^3 + P1 (3 T)(1 - T)^2 + P2 (3 T^2)(1 - t) + P3 T^3
        // } ELSE {
        //     LET T = 2t - 1;
        //     P(t) = {Q0 (1 - T)^3 + Q1 (3 T)(1 - T)^2 + Q2 (3 T^2)(1 - T) + Q3 T^3
        // }

        final Tuple2D result;

        if (t < 0.5) {
            final double tt = 2.0 * t;
            final double u = 1.0 - tt;
            final double ttt = tt * tt * tt;
            final double ttu3 = 3.0 * tt * tt * u;
            final double tuu3 = 3.0 * tt * u * u;
            final double uuu = u * u * u;

            result = new Tuple2D(//
                    this.p0.x * uuu + this.p1.x * tuu3 + this.p2.x * ttu3 + this.p3q0.x * ttt, //
                    this.p0.y * uuu + this.p1.y * tuu3 + this.p2.y * ttu3 + this.p3q0.y * ttt, //
                    ETupleType.POINT);
        } else {
            final double tt = 2.0 * t - 1.0;
            final double u = 1.0 - tt;
            final double ttt = tt * tt * tt;
            final double ttu3 = 3.0 * tt * tt * u;
            final double tuu3 = 3.0 * tt * u * u;
            final double uuu = u * u * u;

            result = new Tuple2D(//
                    this.p3q0.x * uuu + this.q1.x * tuu3 + this.q2.x * ttu3 + this.q3.x * ttt, //
                    this.p3q0.y * uuu + this.q1.y * tuu3 + this.q2.y * ttu3 + this.q3.y * ttt, //
                    ETupleType.POINT);
        }

        return result;
    }
}
