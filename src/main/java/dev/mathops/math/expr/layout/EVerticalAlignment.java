package dev.mathops.math.expr.layout;

/**
 * Vertical alignment positions against which expression components may be laid out.
 */
public enum EVerticalAlignment {

    /** The component's anchor point is placed on the text baseline. */
    TEXT_BASELINE,

    /**
     * The component's anchor point is placed on the mathematical centerline (aligned to the horizontal line of a "+"
     * character.
     */
    MATH_CENTERLINE;
}
