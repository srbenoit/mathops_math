package dev.mathops.math.expr.layout;

import dev.mathops.math.expr.structure.VariableReference;

/**
 * A layout implementation that can be attached to an expression tree to perform layout.
 */
public interface ILayoutImpl {

    /**
     * Performs layout for a {@code VariableReference}.
     *
     * @param context the display context
     * @param object  the object to lay out
     * @param layout  the layout object to populate
     */
    void layoutVariableReference(ExpressionDisplayContext context, VariableReference object,
                                 VariableReferenceLayout layout);
}
