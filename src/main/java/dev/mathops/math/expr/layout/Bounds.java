package dev.mathops.math.expr.layout;

/**
 * A container for the bounds of an expression component.
 *
 * TODO: Do we need to track "selection bounds" to define the region to paint when the object is selected?
 */
public class Bounds {

    /** The vertical alignment. */
    EVerticalAlignment alignment;

    /** The x coordinate of the anchor point. */
    double anchorX;

    /** The y coordinate of the anchor point. */
    double anchorY;

    /** The positive vertical position relative to the anchor point of the logical top of the component. */
    double ascent;

    /** The negative vertical position relative to the anchor point of the logical bottom of the component. */
    double descent;

    /** The horizontal position relative to the anchor point of the logical left edge of the component. */
    double leftBearing;

    /** The horizontal position relative to the anchor point of the logical right edge of the component. */
    double advance;

    /** The vertical position relative to the anchor point of the visual top of the component. */
    double visualTop;

    /** The vertical position relative to the anchor point of the visual bottom of the component. */
    double visualBottom;

    /** The horizontal position relative to the anchor point of the visual left edge of the component. */
    double visualLeft;

    /** The horizontal position relative to the anchor point of the visual right edge of the component. */
    double visualRight;

}
