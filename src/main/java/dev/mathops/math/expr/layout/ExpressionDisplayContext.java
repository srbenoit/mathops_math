package dev.mathops.math.expr.layout;

import java.awt.Color;

/**
 * Parameters that control the layout and rendering of expressions.
 */
public class ExpressionDisplayContext {

    double fontSize;

    double shapeWeight;

    Color symbolColor;

    Color shapeColor;

    Color cursorColor;

    double cursorWidth;

    EExpressionErrorDecoration errorDecorations;

    int currentScriptLevel = 0;
}
