package dev.mathops.math.set.point2;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.geom.Tuple3D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A point set defined by a generalized elliptical arc, and parametrized by degree angle from the start angle (0 to arc
 * angle).
 *
 * <p>
 * The ellipse is defined by a center point and two radius vectors (that need not be orthogonal). A point on the ellipse
 * associated with a given angle (t) is located at the center plus (radius1 * cos(t), radius2 * sin(t)). Therefore
 * angles of 0 or 180 lie along the radius1 axis, and angles of 90 and 270 lie along the radius2 axis.
 */
public class EllipticalArcPointSet extends AbstractPlaneCurve {

    /** The center. */
    public final Tuple2D center;

    /** The first radius vector. */
    public final Tuple3D radius1;

    /** The second radius vector. */
    public final Tuple3D radius2;

    /** The start angle, in degrees. */
    public final double startDegrees;

    /** The arc angle, in degrees. */
    public final double arcDegrees;

    /**
     * Constructs a new {@code EllipticalArcPointSet}.
     *
     * @param theCenter       the center
     * @param theRadius1      the first radius vector
     * @param theRadius2      the second radius vector
     * @param theStartDegrees the start angle, in degrees
     * @param theArcDegrees   the arc angle, in degrees
     */
    public EllipticalArcPointSet(final Tuple2D theCenter, final Tuple3D theRadius1, final Tuple3D theRadius2,
                                 final double theStartDegrees, final double theArcDegrees) {

        super();

        this.center = theCenter.as(ETupleType.POINT);
        this.radius1 = theRadius1.as(ETupleType.VECTOR);
        this.radius2 = theRadius2.as(ETupleType.VECTOR);
        this.startDegrees = theStartDegrees;
        this.arcDegrees = theArcDegrees;
    }

    /**
     * The domains of the segments of the curve. These could be overlapping intervals - when rendering the curve, each
     * segment is queried individually.
     *
     * @return the domain intervals for each segment
     */
    @Override
    public final List<RealInterval> segmentDomains() {

        final List<RealInterval> result = new ArrayList<>(1);

        result.add(new RealInterval(0.0, this.arcDegrees));

        return result;
    }

    /**
     * Evaluates a point along the curve.
     *
     * @param segmentIndex the index of the segment (the domain is found in this index position of the list returned by
     *                     {@code segmentDomains}).
     * @param param        the parameter value at which to evaluate
     * @return the point; {@code null} if the parameter value falls outside the domain (note: to allow for floating
     *         point rounding errors, any parameter value that lies within some epsilon of the ends of the domain will
     *         produce a point).
     */
    @Override
    public final Tuple2D evaluate(final int segmentIndex, final double param) {

        final Tuple2D result;

        if (segmentIndex != 0 || (param < -EPSILON || param > (this.arcDegrees + EPSILON))) {
            result = null;
        } else {
            final double radians = Math.toRadians(this.startDegrees + param);
            final double cos = Math.cos(radians);
            final double sin = Math.sin(radians);

            final double x = this.center.x + cos * this.radius1.x + sin * this.radius2.x;
            final double y = this.center.y + cos * this.radius1.y + sin * this.radius2.y;
            result = new Tuple2D(x, y, ETupleType.POINT);
        }

        return result;
    }
}
