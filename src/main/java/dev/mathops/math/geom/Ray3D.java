package dev.mathops.math.geom;

/**
 * A ray in space with a base point (Pt2D) and vector direction (Vec2D).
 */
public class Ray3D {

    /** The base point. */
    public final Tuple3D basePoint;

    /** The direction. */
    public final Tuple3D direction;

    /**
     * Constructs a new {@code Ray3D}.
     *
     * @param theBasePoint the base point
     * @param theDirection the direction
     */
    public Ray3D(final Tuple3D theBasePoint, final Tuple3D theDirection) {

        this.basePoint = theBasePoint.as(ETupleType.POINT);
        this.direction = theDirection.as(ETupleType.VECTOR);
    }
}
