package dev.mathops.math.set.point2;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A point set defined by a circular arc, and parametrized by degree angle from the start angle (0 to arc angle).
 */
public class CircularArcPointSet extends AbstractPlaneCurve {

    /** The center. */
    public final Tuple2D center;

    /** The radius. */
    public final double radius;

    /** The start angle, in degrees. */
    public final double startDegrees;

    /** The arc angle, in degrees. */
    public final double arcDegrees;

    /**
     * Constructs a new {@code CircularArcPointSet}.
     *
     * @param theCenter       the center
     * @param theRadius       the radius
     * @param theStartDegrees the start angle, in degrees
     * @param theArcDegrees   the arc angle, in degrees
     */
    public CircularArcPointSet(final Tuple2D theCenter, final double theRadius, final double theStartDegrees,
                               final double theArcDegrees) {

        super();

        this.center = theCenter.as(ETupleType.POINT);
        this.radius = theRadius;
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

        result.add(new RealInterval(0, this.arcDegrees));

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
    public Tuple2D evaluate(final int segmentIndex, final double param) {

        final Tuple2D result;

        if (segmentIndex != 0 || (param < -EPSILON || param > (this.arcDegrees + EPSILON))) {
            result = null;
        } else {
            final double radians = Math.toRadians(this.startDegrees + param);
            final double x = this.center.x + this.radius * Math.cos(radians);
            final double y = this.center.y + this.radius * Math.sin(radians);
            result = new Tuple2D(x, y, ETupleType.POINT);
        }

        return result;
    }
}
