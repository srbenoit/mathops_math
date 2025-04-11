package dev.mathops.math.expr.layout;

/**
 * A container for the bounds of an expression component.  A bounding box creates a new coordinate frame in which
 * component geometry can be specified.  In this frame, x increases to the right, y increases upward.
 *
 * <p>
 * TODO: Do we need to track "selection bounds" to define the region to paint when the object is selected?
 */
public final class Bounds {

    /** How this node should be aligned on a line of math content. */
    public final EVerticalAlignment alignment;

    /**
     * The x coordinate of the anchor point relative to the containing coordinate frame, and the x coordinate of the
     * origin in this object's coordinate frame.
     */
    public double anchorX;

    /**
     * The y coordinate of the anchor point relative to the containing coordinate frame, and the y coordinate of the
     * origin in this object's coordinate frame (this coordinate coincides to the baseline specified in
     * {@code alignment}).
     */
    public double anchorY;

    /** The y coordinate of the logical top of the component (typically positive, set to font ascent). */
    public double logicalTop;

    /**
     * The y coordinate of the logical bottom of the component (typically negative, set to font descent).
     */
    public double logicalBottom;

    /**
     * The x coordinate of the logical left edge of the component (typically positive, set to the left-side bearing of
     * the leftmost character).
     */
    public double logicalLeft;

    /**
     * The x coordinate of the logical right edge of the component (typically positive, set to the total advance).
     */
    public double logicalRight;

    /** The y coordinate of the visual top of the component. */
    public double visualTop;

    /** The y coordinate of the visual bottom of the component. */
    public double visualBottom;

    /** The x coordinate of the visual left edge of the component. */
    public double visualLeft;

    /** The x coordinate of the visual right edge of the component. */
    public double visualRight;

    /**
     * Constructs a new {@code Bounds}.
     *
     * @param theAlignment how this node should be aligned on a line of math content
     */
    public Bounds(final EVerticalAlignment theAlignment) {

        this.alignment = theAlignment;
    }

    /**
     * Returns the logical width of the bounding box.
     *
     * @return the logical width
     */
    public double logicalWidth() {

        return this.logicalRight - this.logicalLeft;
    }

    /**
     * Sets the logical bounds.
     *
     * @param minX the minimum x coordinate
     * @param maxX the maximum x coordinate
     * @param minY the minimum y coordinate
     * @param maxY the maximum y coordinate
     */
    public void setLogical(final double minX, final double maxX, final double minY, final double maxY) {

        this.logicalLeft = minX;
        this.logicalRight = maxX;
        this.logicalBottom = minY;
        this.logicalTop = maxY;
    }

    /**
     * Sets the visyul bounds.
     *
     * @param minX the minimum x coordinate
     * @param maxX the maximum x coordinate
     * @param minY the minimum y coordinate
     * @param maxY the maximum y coordinate
     */
    public void setVisual(final double minX, final double maxX, final double minY, final double maxY) {

        this.visualLeft = minX;
        this.visualRight = maxX;
        this.visualBottom = minY;
        this.visualTop = maxY;
    }
}
