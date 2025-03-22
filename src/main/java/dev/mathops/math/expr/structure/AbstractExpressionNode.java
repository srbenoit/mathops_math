package dev.mathops.math.expr.structure;

import dev.mathops.math.expr.layout.ExpressionDisplayContext;
import dev.mathops.math.expr.layout.ILayoutImpl;

/**
 * The base class for all nodes in an expression tree.
 */
abstract class AbstractExpressionNode {

    /**
     * Gets the total number of tokens in this node.  The cursor and selection bounds exist between tokens, or before
     * the first token or after the last token.
     *
     * @return the number of tokens
     */
    abstract int getNumTokens();

    /**
     * Performs layout of a node using a provided implementation.
     *
     * @param context the display context
     * @param impl    the layout implementation
     */
    abstract void performLayout(ExpressionDisplayContext context, ILayoutImpl impl);
}
