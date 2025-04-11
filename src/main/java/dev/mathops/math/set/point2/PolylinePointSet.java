package dev.mathops.math.set.point2;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A point set defined by a sequence of line segments connecting a list of points. A segment from the last point to the
 * first is NOT automatically generated - to make a closed path, include the start point again at the end of the list of
 * points.
 *
 * <p>
 * A polyline represents single connected segment. If the polyline is defined by N points, its parameter range is from 0
 * to the (N-1). Parameter values 0 to 1 span the first line segment, parameter values 1 to 2 span the second, and so
 * forth.
 */
public class PolylinePointSet extends AbstractPlaneCurve {

    /** The points. */
    public final List<Tuple2D> points;

    /**
     * Constructs a new {@code PolylinePointSet}.
     *
     * @param thePoints the points
     */
    public PolylinePointSet(final Tuple2D... thePoints) {

        super();

        if (thePoints == null) {
            this.points = new ArrayList<>(4);
        } else {
            this.points = new ArrayList<>(thePoints.length);
            final List<Tuple2D> list = Arrays.asList(thePoints);
            this.points.addAll(list);
        }
    }

    /**
     * Constructs a new {@code PolylinePointSet}.
     *
     * @param thePoints the points
     */
    public PolylinePointSet(final List<Tuple2D> thePoints) {

        super();

        this.points = Objects.requireNonNullElseGet(thePoints, () -> new ArrayList<>(4));
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

        final int count = this.points.size();
        result.add(new RealInterval(0, (double) (count - 1)));

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

        final int maxDomain = this.points.size() - 1;

        if (segmentIndex != 0 || (param < -EPSILON || param > ((double) maxDomain + EPSILON))) {
            result = null;
        } else {
            final double floor = Math.floor(param);
            final int piece = Math.max(0, (int) floor);
            final Tuple2D p1 = this.points.get(piece);
            final Tuple2D p2 = this.points.get(piece + 1);
            final double inner = param - floor;

            final double x = p1.x + inner * (p2.x - p1.x);
            final double y = p1.y + inner * (p2.y - p1.y);
            result = new Tuple2D(x, y, ETupleType.POINT);
        }

        return result;
    }
}
