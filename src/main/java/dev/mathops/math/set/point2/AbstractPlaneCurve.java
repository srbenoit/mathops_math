package dev.mathops.math.set.point2;

import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.List;

/**
 * The base class for arbitrary curves in the plan. Curves may be traced out parametrically as a parameter runs through
 * a domain. A curve may be made up of multiple connected components, which should each be drawn separately and without
 * being connected to each other.
 */
public abstract class AbstractPlaneCurve extends AbstractPlanePointSet {

    /** Epsilon value for domain endpoint comparisons. */
    public static final double EPSILON = 0.000000000000000001;

    /**
     * Constructs a new {@code AbstractPlaneCurve}.
     */
    protected AbstractPlaneCurve() {

        super();
    }

    /**
     * The domains of the segments of the curve. These could be overlapping intervals - when rendering the curve, each
     * segment is queried individually.
     *
     * @return the domain intervals for each segment
     */
    public abstract List<RealInterval> segmentDomains();

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
    public abstract Tuple2D evaluate(int segmentIndex, double param);
}
