package dev.mathops.math.expr.structure;

import dev.mathops.math.expr.layout.Bounds;
import dev.mathops.math.expr.layout.ExpressionDisplayContext;
import dev.mathops.math.expr.layout.ILayoutImpl;

/**
 * The base class for all nodes in an expression tree.
 */
public abstract class AbstractExpressionNode {

    /**
     * Gets the total number of tokens in this node.  The cursor and selection bounds exist between tokens, or before
     * the first token or after the last token.
     *
     * @return the number of tokens
     */
    public abstract int getNumTokens();

    /**
     * Performs layout of a node using a provided implementation.
     *
     * @param context the display context
     * @param impl    the layout implementation
     */
    public abstract void performLayout(ExpressionDisplayContext context, ILayoutImpl impl);

    /**
     * Gets the layout bounds of the node.
     *
     * @return the layout bounds; {@code null} if the node has not been laid out
     */
    public abstract Bounds getBounds();
}
