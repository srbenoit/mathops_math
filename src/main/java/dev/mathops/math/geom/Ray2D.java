package dev.mathops.math.geom;

/**
 * A ray in space with a base point (Pt2D) and vector direction (Vec2D).
 */
public class Ray2D {

    /** The base point. */
    public final Tuple2D basePoint;

    /** The direction. */
    public final Tuple2D direction;

    /**
     * Constructs a new {@code Ray2D}.
     *
     * @param theBasePoint the base point
     * @param theDirection the direction
     */
    public Ray2D(final Tuple2D theBasePoint, final Tuple2D theDirection) {

        this.basePoint = theBasePoint.as(ETupleType.POINT);
        this.direction = theDirection.as(ETupleType.VECTOR);
    }

}
