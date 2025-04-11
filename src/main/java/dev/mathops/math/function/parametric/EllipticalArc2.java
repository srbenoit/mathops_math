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
 * An elliptical arc in the plane, defined by a center, an x radius vector, a y radius vector, a start angle, and an arc
 * angle, parametrized over the interval [0,1].
 *
 * <pre>
 * Theta(t) = StartAngle + t * ArcAngle
 * P(t) = C + X COS(Theta(t)) + Y SIN(Theta(t))
 * </pre>
 */
public class EllipticalArc2 implements IParametricCurve2 {

    /** A commonly-used constant. */
    public static final double TWO_PI = 2.0 * Math.PI;

    /** The center. */
    public final Tuple2D center;

    /** The +x axis vector. */
    public final Tuple2D xAxis;

    /** The +y axis vector. */
    public final Tuple2D yAxis;

    /** The start angle, in degrees. */
    public final double startAngleDeg;

    /** The arc angle, in degrees. */
    public final double arcAngleDeg;

    /**
     * Constructs a new {code EllipticalArc2}.
     *
     * @param theCenter        the center
     * @param theXAxis         the x-axis vector
     * @param theYAxis         the y-axis vector
     * @param theStartAngleDeg the start angle, in degrees
     * @param theArcAngleDeg   the arc angle, in degrees
     */
    public EllipticalArc2(final Tuple2D theCenter, final Tuple2D theXAxis, final Tuple2D theYAxis,
                          final double theStartAngleDeg, final double theArcAngleDeg) {

        super();

        this.center = theCenter.as(ETupleType.POINT);
        this.startAngleDeg = theStartAngleDeg;
        this.arcAngleDeg = theArcAngleDeg;
        this.xAxis = theXAxis.as(ETupleType.VECTOR);
        this.yAxis = theYAxis.as(ETupleType.VECTOR);
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

        final double angle = this.startAngleDeg + t * this.arcAngleDeg;
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        final double x = this.center.x + this.xAxis.x * cos + this.yAxis.x * sin;
        final double y = this.center.y + this.xAxis.y * cos + this.yAxis.y * sin;

        return new Tuple2D(x, y, ETupleType.POINT);
    }
}

