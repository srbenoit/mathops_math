package dev.mathops.math.expr.structure;

/**
 * An expression, which consists of the top-level node in the node tree, a current cursor position, and an optional
 * selection range.  The expression can react to key events, cut/copy/paste/delete event, and if the expression has
 * attached layout information, the selection can be changed by mouse events.
 */
public class Expression {

    /** The root node of the expression tree. */
    AbstractExpressionNode root;

    /** The current cursor position. */
    int cursorPosition;

    /** The start of the selection range. */
    int selectionRangeStart;

    /** The end of the selection range. */
    int selectionRangeEnd;

    /**
     * Constructs a new {@code Expression}.
     *
     * @param theRoot the root node
     */
    public Expression(final AbstractExpressionNode theRoot) {

        this.root = theRoot;
    }
}
