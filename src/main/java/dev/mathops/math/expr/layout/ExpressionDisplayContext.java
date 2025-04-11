package dev.mathops.math.expr.layout;

import java.awt.Color;

/**
 * Parameters that control the layout and rendering of expressions.
 */
public final class ExpressionDisplayContext {

    /** The base font size. */
    public double fontSize;

    /** The minimum font size. */
    public double minFontSize;

    /** The shape weight. */
    public double shapeWeight;

    /** The symbol color. */
    public Color symbolColor;

    /** The shape color. */
    public Color shapeColor;

    /** The cursor color. */
    public Color cursorColor;

    /** The cursor width. */
    public double cursorWidth;

    /** The error decoration style. */
    public EExpressionErrorDecoration errorDecorations;

    /** The current script level (0 for top-level constructions). */
    public int currentScriptLevel = 0;

    /**
     * Constructs a new {@code ExpressionDisplayContext}.
     *
     * @param theFontSize the font size
     */
    public ExpressionDisplayContext(final float theFontSize) {

        this.fontSize = theFontSize;

        this.shapeWeight = theFontSize / 20.0;
        this.minFontSize = 8.0;
        this.symbolColor = Color.BLACK;
        this.shapeColor = Color.BLACK;
        this.cursorColor = Color.BLUE;
        this.cursorWidth = this.shapeWeight;
        this.errorDecorations = EExpressionErrorDecoration.INVALID_NODE_WAVY_UNDERLINE;
    }
}
