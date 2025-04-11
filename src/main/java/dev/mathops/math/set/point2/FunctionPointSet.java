package dev.mathops.math.set.point2;

import dev.mathops.math.function.parametric.IParametricCurve2;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of points defined through the evaluation of a function over a domain. The domain does not need to
 * correspond to the $x$ axis - the function could be y(x), x(y), or x(t) and y(t).
 */
public class FunctionPointSet extends AbstractPlaneCurve {

    /** The function that generates points. */
    private final IParametricCurve2 fxn;

    /**
     * Constructs a new {@code FunctionPointSet}.
     *
     * @param theFxn the function
     */
    public FunctionPointSet(final IParametricCurve2 theFxn) {

        super();

        this.fxn = theFxn;
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

        final RealInterval domain = this.fxn.getDomain();
        result.add(domain);

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

        final RealInterval domain = this.fxn.getDomain();

        final Tuple2D result;

        if (segmentIndex != 0 || (param < (domain.lowerBound - EPSILON) || param > (domain.upperBound + EPSILON))) {
            result = null;
        } else {
            result = this.fxn.evaluate(param);
        }

        return result;
    }
}
