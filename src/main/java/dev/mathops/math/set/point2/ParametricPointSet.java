package dev.mathops.math.set.point2;

import dev.mathops.math.function.IFunction;
import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of points defined through the evaluation of a function over a domain. The domain dies not need to
 * correspond to the $x$ axis - the function could be y(x), x(y), or x(t) and y(t).
 */
public class ParametricPointSet extends AbstractPlaneCurve {

    /** The function that generates x coordinates. */
    private final IFunction xFxn;

    /** The function that generates y coordinates. */
    private final IFunction yFxn;

    /** The domain. */
    private final RealInterval domain;

    /**
     * Constructs a new {@code FunctionPointSet}. The domain used is the intersection of the domains of the two
     * component functions.
     *
     * @param theXFxn the x function
     * @param theYFxn the y function
     */
    public ParametricPointSet(final IFunction theXFxn, final IFunction theYFxn) {

        super();

        if (theXFxn == null) {
            throw new IllegalArgumentException("X function may not me null");
        }
        if (theYFxn == null) {
            throw new IllegalArgumentException("Y function may not me null");
        }
        if (theXFxn.getNumInputs() != 1 || theXFxn.getNumOutputs() != 1) {
            throw new IllegalArgumentException("X function must be 1-in, 1-out");
        }
        if (theYFxn.getNumInputs() != 1 || theYFxn.getNumOutputs() != 1) {
            throw new IllegalArgumentException("y function must be 1-in, 1-out");
        }

        this.xFxn = theXFxn;
        this.yFxn = theYFxn;

        final RealInterval xDomain = theXFxn.getDomain(0);
        final RealInterval yDomain = theYFxn.getDomain(0);
        this.domain = xDomain.intersect(yDomain);
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
    public Tuple2D evaluate(final int segmentIndex, final double param) {

        final Tuple2D result;

        if (segmentIndex != 0 ||
            (param < (this.domain.lowerBound.doubleValue() - EPSILON)
             || param > (this.domain.upperBound.doubleValue() + EPSILON))) {
            result = null;
        } else {
            final double[] x = this.xFxn.evaluate(param);
            final double[] y = this.yFxn.evaluate(param);
            if (x == null || y == null) {
                result = null;
            } else {
                final double xx = x[0];
                final double yy = y[0];
                result = new Tuple2D(xx, yy, ETupleType.POINT);
            }
        }

        return result;
    }
}
