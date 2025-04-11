package dev.mathops.math.set.point2;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A point set defined by some portion of a line.
 *
 * <p>
 * The line is defined by two points, P1 and P2, by "P = P1 + T(P2 - P1)". The portion of the line included in the point
 * set is given by a (perhaps unbounded) range of values for parameter T.
 */
public class LinePointSet extends AbstractPlaneCurve {

    /** The first point. */
    public final Tuple2D p1;

    /** The second point. */
    public final Tuple2D p2;

    /** The parameter domain. */
    public final RealInterval domain;

    /**
     * Constructs a new {@code LinePointSet}.
     *
     * @param theP1     the first point
     * @param theP2     the second point
     * @param theDomain the parameter domain
     */
    public LinePointSet(final Tuple2D theP1, final Tuple2D theP2, final RealInterval theDomain) {

        super();

        this.p1 = theP1.as(ETupleType.POINT);
        this.p2 = theP2.as(ETupleType.POINT);
        this.domain = theDomain;
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

        result.add(this.domain);

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

        if (segmentIndex != 0 || (param < (this.domain.lowerBound - EPSILON) || param > (this.domain.upperBound + EPSILON))) {
            result = null;
        } else {
            final double x = this.p1.x + param * (this.p2.x - this.p1.x);
            final double y = this.p1.y + param * (this.p2.y - this.p1.y);
            result = new Tuple2D(x, y, ETupleType.POINT);
        }

        return result;
    }
}
