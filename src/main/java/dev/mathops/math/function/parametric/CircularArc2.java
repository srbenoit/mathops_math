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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A circular arc in the plane, defined by a center, a radius, a start angle, and an arc angle, parametrized over the
 * interval [0,1].
 *
 * <pre>
 * Theta(t) = StartAngle + t * ArcAngle
 * P(t) = C + R COS(Theta(t)) (1,0) + R SIN(Theta(t)) (0,i)
 * </pre>
 */
public class CircularArc2 implements IParametricCurve2 {

    /** A commonly-used constant. */
    public static final double TWO_PI = 2.0 * Math.PI;

    /** A commonly-used constant. */
    public static final double PI_OVER_2 = Math.PI * 0.5;

    /** The "magic number" for placing control points to approximate a circular arc with cubics. */
    private static final double MAGIC = 0.55228474983079;

    /** The center. */
    public final Tuple2D center;

    /** The radius. */
    public final double radius;

    /** The start angle, in radians. */
    public final double startAngleDeg;

    /** The arc angle, in radians. */
    public final double arcAngleDeg;

    /**
     * Constructs a new {code CircularArc2}.
     *
     * @param theCenter        the center
     * @param theRadius        the radius
     * @param theStartAngleDeg the start angle, in degrees
     * @param theArcAngleDeg   the arc angle, in degrees
     */
    public CircularArc2(final Tuple2D theCenter, final double theRadius, final double theStartAngleDeg,
                        final double theArcAngleDeg) {

        super();

        this.center = theCenter.as(ETupleType.POINT);
        this.radius = theRadius;
        this.startAngleDeg = theStartAngleDeg;
        this.arcAngleDeg = theArcAngleDeg;
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
     * Evaluates the position function at a specified parameter value.
     *
     * @param t the parameter value
     * @return the position (the start point at t = 0.0, and the end point at t = 1.0)
     */
    public final Tuple2D evaluate(final double t) {

        final double angle = Math.toRadians(this.startAngleDeg + t * this.arcAngleDeg);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        final double x = this.center.x + this.radius * cos;
        final double y = this.center.y + this.radius * sin;

        return new Tuple2D(x, y, ETupleType.POINT);
    }

    /**
     * Generates a circular arc through three arbitrary points.  The generated arc runs from the first point to the
     * second point through the middle point.
     *
     * @param point1 the first point
     * @param point2 the second point
     * @param point3 the third point
     * @return the circular arc; {code null} if points are congruent or co-linear
     */
    public static CircularArc2 throughThreePoints(final Tuple2D point1, final Tuple2D point2, final Tuple2D point3) {

        // Consider line from point1 to point2
        final Tuple2D vec12 = Tuple2D.difference(point2, point1, ETupleType.VECTOR);
        final Tuple2D rotated12 = new Tuple2D(-vec12.y, vec12.x, ETupleType.VECTOR);
        final Tuple2D mid12 = Tuple2D.lerp(point1, point2, 0.5);

        // Consider line from point2 to point3
        final Tuple2D vec23 = Tuple2D.difference(point3, point2, ETupleType.VECTOR);
        final Tuple2D rotated23 = new Tuple2D(-vec23.y, vec23.x, ETupleType.VECTOR);
        final Tuple2D mid23 = Tuple2D.lerp(point2, point3, 0.5);

        // Intersect lines defined by [ mid12 + A rotated12 ] and [ mid23 + B rotated23 ]
        // [rotated12.x rotated23.x] [A] = [mid23.x - mid12.x]
        // [rotated12.y rotated23.y] [B] = [mid23.y - mid12.y]

        CircularArc2 result = null;

        final double det = rotated12.x * rotated23.y - rotated23.x * rotated12.y;
        if (det != 0.0) {
            final double reciprocal = 1.0 / det;
            final double inv11 = rotated23.y * reciprocal;
            final double inv12 = -rotated23.x * reciprocal;

            final double rightx = mid23.x - mid12.x;
            final double righty = mid23.y - mid12.y;

            final double a = inv11 * rightx + inv12 * righty;

            final double cx = mid12.x + a * rotated12.x;
            final double cy = mid12.y + a * rotated12.y;
            final Tuple2D center = new Tuple2D(cx, cy, ETupleType.POINT);
            final double radius = Tuple2D.distance(center, point1);

            // Find the angles to each of the three points
            final Tuple2D ray1 = Tuple2D.difference(point1, center, ETupleType.VECTOR);
            final Tuple2D ray2 = Tuple2D.difference(point2, center, ETupleType.VECTOR);
            final Tuple2D ray3 = Tuple2D.difference(point3, center, ETupleType.VECTOR);

            final double theta1Deg = ray1.getThetaDeg();
            final double theta2Deg = ray2.getThetaDeg();
            final double theta3Deg = ray3.getThetaDeg();

            final double arcAngleDeg;
            if (theta1Deg == theta3Deg) {
                arcAngleDeg = 360.0;
            } else if (theta3Deg > theta1Deg) {
                if (theta2Deg >= theta1Deg && theta2Deg <= theta3Deg) {
                    arcAngleDeg = theta3Deg - theta1Deg;
                } else {
                    arcAngleDeg = theta3Deg - theta1Deg - 360.0;
                }
            } else if (theta2Deg >= theta3Deg && theta2Deg <= theta1Deg) {
                arcAngleDeg = theta3Deg - theta1Deg;
            } else {
                arcAngleDeg = theta3Deg - theta1Deg - 360.0;
            }

            result = new CircularArc2(center, radius, theta1Deg, arcAngleDeg);
        }

        return result;
    }

    /**
     * Returns a cubic representation of this segment, constructing it when this method is first called.
     *
     * <pre>
     *     Derivation in "APPROXIMATION OF A CUBIC BEZIER CURVE BY CIRCULAR ARCS AND VICE VERSA", Aleksas Riškus,
     *     INFORMATION TECHNOLOGY AND CONTROL, 2006, Vol.35, No.4
     * </pre>
     *
     * @return the cubic representation as an unmodifiable list of cubic primitives
     */
    public final List<BezierCubic2> asCubicCurves() {

        // Note - we do not truncate arc angle - a path could have an arc greater than 2 PI if it is to be
        // used for (for example) animation, rather than rendering.

        final List<BezierCubic2> result = new ArrayList<>(4);

        if (this.arcAngleDeg > 0.0) {
            buildCCWCubicCurves(result);
        } else if (this.arcAngleDeg < 0.0) {
            buildCWCubicCurves(result);
        }

        return result;
    }

    /**
     * Creates cubic primitives for a counter-clockwise arc.
     *
     * @param accumulator the accumulator to which to add cubic primitives
     */
    private void buildCCWCubicCurves(final Collection<? super BezierCubic2> accumulator) {

        double current = this.startAngleDeg;
        double totalAngle = 0.0;

        while (totalAngle < this.arcAngleDeg) {

            final double remains = this.arcAngleDeg - totalAngle;
            final double end;
            if (remains >= PI_OVER_2) {
                end = current + PI_OVER_2;
                totalAngle += PI_OVER_2;
                current += PI_OVER_2;
            } else {
                end = this.startAngleDeg + this.arcAngleDeg;
                totalAngle = this.arcAngleDeg;
            }

            final double x1 = this.center.x + this.radius * Math.cos(current);
            final double y1 = this.center.y + this.radius * Math.sin(current);

            final double x4 = this.center.x + this.radius * Math.cos(end);
            final double y4 = this.center.y + this.radius * Math.sin(end);

            final double phi = end - current;
            final double cosPhi = Math.cos(phi);
            final double sinPhi = Math.sin(phi);

            final double x2 = x1 + MAGIC * this.radius * sinPhi;
            final double y2 = y1 - MAGIC * this.radius * cosPhi;

            final double x3 = x4 + MAGIC * this.radius * sinPhi;
            final double y3 = y4 - MAGIC * this.radius * cosPhi;

            // TODO: Verify these in all quadrants

            final Tuple2D pt1 = new Tuple2D(x1, y1, ETupleType.POINT);
            final Tuple2D pt2 = new Tuple2D(x2, y2, ETupleType.POINT);
            final Tuple2D pt3 = new Tuple2D(x3, y3, ETupleType.POINT);
            final Tuple2D pt4 = new Tuple2D(x4, y4, ETupleType.POINT);

            accumulator.add(new BezierCubic2(pt1, pt2, pt3, pt4));
        }
    }

    /**
     * Creates cubic primitives for a clockwise arc.
     *
     * @param accumulator the accumulator to which to add cubic primitives
     */
    private void buildCWCubicCurves(final Collection<? super BezierCubic2> accumulator) {

        double current = this.startAngleDeg;
        double totalAngle = 0.0;

        // Total angle becomes more negative as we go...
        while (totalAngle > this.arcAngleDeg) {

            final double remains = this.arcAngleDeg - totalAngle;
            final double end;
            if (remains <= -PI_OVER_2) {
                end = current - PI_OVER_2;
                totalAngle -= PI_OVER_2;
                current -= PI_OVER_2;
            } else {
                end = this.startAngleDeg + this.arcAngleDeg;
                totalAngle = this.arcAngleDeg;
            }

            final double x1 = this.center.x + this.radius * Math.cos(current);
            final double y1 = this.center.y + this.radius * Math.sin(current);

            final double x4 = this.center.x + this.radius * Math.cos(end);
            final double y4 = this.center.y + this.radius * Math.sin(end);

            final double phi = end - current;
            final double cosPhi = Math.cos(phi);
            final double sinPhi = Math.sin(phi);

            final double x2 = x1 + MAGIC * this.radius * sinPhi;
            final double y2 = y1 - MAGIC * this.radius * cosPhi;

            final double x3 = x4 + MAGIC * this.radius * sinPhi;
            final double y3 = y4 - MAGIC * this.radius * cosPhi;

            // TODO: Verify these in all quadrants

            final Tuple2D pt1 = new Tuple2D(x1, y1, ETupleType.POINT);
            final Tuple2D pt2 = new Tuple2D(x2, y2, ETupleType.POINT);
            final Tuple2D pt3 = new Tuple2D(x3, y3, ETupleType.POINT);
            final Tuple2D pt4 = new Tuple2D(x4, y4, ETupleType.POINT);

            accumulator.add(new BezierCubic2(pt1, pt2, pt3, pt4));
        }
    }
}
