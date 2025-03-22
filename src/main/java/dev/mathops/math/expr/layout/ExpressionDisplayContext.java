package dev.mathops.math.expr.layout;

import java.awt.Color;

/**
 * Parameters that control the layout and rendering of expressions.
 */
public final class ExpressionDisplayContext {

    /** The base font size. */
    double fontSize;

    /** The shape weight. */
    double shapeWeight;

    /** The symbol color. */
    Color symbolColor;

    /** The shape color. */
    Color shapeColor;

    /** The cursor color. */
    Color cursorColor;

    /** The cursor width. */
    double cursorWidth;

    /** The error decoration style. */
    EExpressionErrorDecoration errorDecorations;

    /** The shape weight. */
    int currentScriptLevel = 0;

    /**
     * Constructs a new {@code ExpressionDisplayContext}.
     *
     * @param theFontSize the font size
     */
    public ExpressionDisplayContext(final double theFontSize) {

        this.fontSize = theFontSize;

        this.shapeWeight = theFontSize / 20.0;
        this.symbolColor = Color.BLACK;
        this.shapeColor = Color.BLACK;
        this.cursorColor = Color.BLUE;
        this.cursorWidth = this.shapeWeight;
        this.errorDecorations = EExpressionErrorDecoration.INVALID_NODE_WAVY_UNDERLINE;
    }
}
