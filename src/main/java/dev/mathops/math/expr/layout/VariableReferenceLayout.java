package dev.mathops.math.expr.layout;

/**
 * All layout information needed for a {@code VariableReference} object.
 */
public class VariableReferenceLayout {

    /** The overall bounds for the construction. */
    Bounds overallBounds;

    /** The bounds for the variable name. */
    Bounds variableNameBounds;

    /** The bounds for the accent above the variable name. */
    SimpleBounds accentBounds;

    /** The bounds for the pre-superscript. */
    Bounds preSuperscriptBounds;

    /** The bounds for the pre-subscript. */
    Bounds preSubscriptBounds;

    /** The bounds for the superscript. */
    Bounds superscriptBounds;

    /** The bounds for the subscript. */
    Bounds subscriptBounds;

    /** The bounds for the opening bracket for indexes. */
    Bounds openingBracketBounds;

    /** The bounds for the first index. */
    Bounds index1Bounds;

    /** The bounds for the comma between indexes. */
    Bounds indexCommaBounds;

    /** The bounds for the second index. */
    Bounds index2Bounds;

    /** The bounds for the closing bracket for indexes. */
    Bounds closingBracketBounds;
}
