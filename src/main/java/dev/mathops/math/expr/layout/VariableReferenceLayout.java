package dev.mathops.math.expr.layout;

/**
 * All layout information needed for a {@code VariableReference} object.
 */
public final class VariableReferenceLayout {

    /** The bounds for the variable name. */
    public final Bounds overallBounds;

    /** The bounds for the variable name. */
    public final Bounds variableNameBounds;

    /** The bounds for the accent above the variable name. */
    public final SimpleBounds accentBounds;

    /** The bounds for the pre-superscript. */
    public final Bounds preSuperscriptBounds;

    /** The bounds for the pre-subscript. */
    public final Bounds preSubscriptBounds;

    /** The bounds for the superscript. */
    public final Bounds superscriptBounds;

    /** The bounds for the subscript. */
    public final Bounds subscriptBounds;

    /** The bounds for the opening bracket for indexes. */
    public final Bounds openingBracketBounds;

    /** The bounds for the first index. */
    public final Bounds index1Bounds;

    /** The bounds for the comma between indexes. */
    public final Bounds indexCommaBounds;

    /** The bounds for the second index. */
    public final Bounds index2Bounds;

    /** The bounds for the closing bracket for indexes. */
    public final Bounds closingBracketBounds;

    /**
     * Constructs a new {@code VariableReferenceLayout};
     */
    public VariableReferenceLayout() {

        this.overallBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.variableNameBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.accentBounds = new SimpleBounds();
        this.preSuperscriptBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.preSubscriptBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.superscriptBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.subscriptBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.openingBracketBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.index1Bounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.indexCommaBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.index2Bounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
        this.closingBracketBounds = new Bounds(EVerticalAlignment.TEXT_BASELINE);
    }
}
