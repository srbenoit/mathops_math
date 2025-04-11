package dev.mathops.math.set.point2;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.geom.Tuple3D;
import dev.mathops.math.set.number.RealInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * A point set defined by a parabolic arc, parameterized over a range of "x" values.
 *
 * <p>
 * The parabola is defined by a vertex point, two vectors that give the "x" and "y" axes along which to orient the
 * parabola and define the unit length in those directions, and a coefficient A. The shape of the parabola will be
 * identical to "y = Ax^2", its vertex will lie at the specified point, and its x and y components represent distances
 * along the corresponding direction vectors.
 *
 * <p>
 * The arc, then, is defined by an interval on the x-axis.
 */
public class ParabolicArcPointSet extends AbstractPlaneCurve {

    /** The vertex. */
    public final Tuple2D vertex;

    /** A vector onto which to map the "x" axis in "y = Ax^2". */
    public final Tuple3D xDirection;

    /** A vector onto which to map the "y" axis in "y = Ax^2". */
    public final Tuple3D yDirection;

    /** The coefficient. */
    public final double coefficient;

    /** The start of the arc interval. */
    public final double xStart;

    /** The end of the arc interval. */
    public final double xEnd;

    /**
     * Constructs a new {@code ParabolicArcPointSet}.
     *
     * @param theVertex      the vertex
     * @param theXDirection  the vector that defines the direction and unit length of the x-axis
     * @param theYDirection  the vector that defines the direction and unit length of the y-axis
     * @param theCoefficient the coefficient
     * @param theStart       the start x coordinate
     * @param theEnd         the end x coordinate
     */
    public ParabolicArcPointSet(final Tuple2D theVertex, final Tuple3D theXDirection, final Tuple3D theYDirection,
                                final double theCoefficient, final double theStart, final double theEnd) {

        super();

        this.vertex = theVertex.as(ETupleType.POINT);
        this.xDirection = theXDirection.as(ETupleType.VECTOR);
        this.yDirection = theYDirection.as(ETupleType.VECTOR);
        this.coefficient = theCoefficient;
        this.xStart = theStart;
        this.xEnd = theEnd;
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

        result.add(new RealInterval(this.xStart, this.xEnd));

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

        if (segmentIndex != 0 || (param < this.xStart - EPSILON || param > (this.xEnd + EPSILON))) {
            result = null;
        } else {
            final double fxnVal = this.coefficient * param * param;
            final double x = this.vertex.x + param * this.xDirection.x + fxnVal * this.yDirection.x;
            final double y = this.vertex.y + param * this.yDirection.y + fxnVal * this.yDirection.y;
            result = new Tuple2D(x, y, ETupleType.POINT);
        }

        return result;
    }
}
