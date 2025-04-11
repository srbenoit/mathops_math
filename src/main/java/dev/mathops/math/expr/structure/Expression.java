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

    /**
     * Gets the root node of the expression tree.
     *
     * @return the root node
     */
    public AbstractExpressionNode getRoot() {

        return this.root;
    }

    /**
     * Gets the current cursor position.
     *
     * @return the cursor position
     */
    public int getCursorPosition() {

        return this.cursorPosition;
    }

    /**
     * Gets the start of the selection range.
     *
     * @return the start position of the selection range
     */
    public int getSelectionRangeStart() {

        return this.selectionRangeStart;
    }

    /**
     * Gets the end of the selection range.
     *
     * @return the end position of the selection range
     */
    public int getSelectionRangeEnd() {

        return this.selectionRangeEnd;
    }

}
