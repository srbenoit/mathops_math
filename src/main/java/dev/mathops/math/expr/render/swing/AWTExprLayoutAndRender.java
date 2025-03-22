package dev.mathops.math.expr.render.swing;

import dev.mathops.commons.file.FileLoader;
import dev.mathops.commons.log.Log;
import dev.mathops.math.expr.layout.ExpressionDisplayContext;
import dev.mathops.math.expr.layout.ILayoutImpl;
import dev.mathops.math.expr.layout.VariableReferenceLayout;
import dev.mathops.math.expr.structure.VariableReference;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * An AWT-based renderer that draws the expression to a {@code BufferedImage}.
 */
public class AWTExprLayoutAndRender implements ILayoutImpl {

    /** A one-point text regular font. */
    private final Font onePointTextRegular;

    /** Metrics for the one-point text regular font. */
    private final FontMetrics onePointTextRegularMetrics;

    /** A one-point text italic font. */
    private final Font onePointTextItalic;

    /** Metrics for the one-point text italic font. */
    private final FontMetrics onePointTextItalicMetrics;

    /** A one-point text bold font. */
    private final Font onePointTextBold;

    /** Metrics for the one-point text bold font. */
    private final FontMetrics onePointTextBoldMetrics;

    /** A one-point text bold italic font. */
    private final Font onePointTextBoldItalic;

    /** Metrics for the one-point text bold italic font. */
    private final FontMetrics onePointTextBoldItalicMetrics;

    /** A one-point math font. */
    private final Font onePointMath;

    /** Metrics for the one-point math font. */
    private final FontMetrics onePointMathMetrics;

    /**
     * Constructs a new {@code AWTExpressionRenderer}.
     */
    AWTExprLayoutAndRender() {

        final BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = img.createGraphics();

        // Attempt to load 1-point versions of fonts we may need to use and get their metrics

        this.onePointTextRegular = loadFont("STIXTwoText-Regular.otf", Font.SERIF, Font.PLAIN);
        this.onePointTextRegularMetrics = g2d.getFontMetrics(this.onePointTextRegular);

        this.onePointTextItalic = loadFont("STIXTwoText-Italic.otf", Font.SERIF, Font.ITALIC);
        this.onePointTextItalicMetrics = g2d.getFontMetrics(this.onePointTextItalic);

        this.onePointTextBold = loadFont("STIXTwoText-Regular.otf", Font.SERIF, Font.BOLD);
        this.onePointTextBoldMetrics = g2d.getFontMetrics(this.onePointTextBold);

        this.onePointTextBoldItalic = loadFont("STIXTwoText-Italic.otf", Font.SERIF, Font.BOLD | Font.ITALIC);
        this.onePointTextBoldItalicMetrics = g2d.getFontMetrics(this.onePointTextBoldItalic);

        this.onePointMath = loadFont("STIXTwoMath-Regular.otf", Font.SERIF, Font.PLAIN);
        this.onePointMathMetrics = g2d.getFontMetrics(this.onePointMath);
    }

    /**
     * Attempts to load a one-point version of a font, using a fallback system font if loading fails.
     *
     * @param filename          the font filename
     * @param fallbackFontName  the name of the fallback system font
     * @param fallbackFontStyle the fallback font style
     */
    private static Font loadFont(final String filename, final String fallbackFontName, final int fallbackFontStyle) {

        final byte[] bytes = FileLoader.loadFileAsBytes(AWTExprLayoutAndRender.class, filename, true);

        Font font;

        if (bytes == null) {
            font = new Font(fallbackFontName, fallbackFontStyle, 1);
        } else {
            try (final ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                font = Font.createFont(Font.TRUETYPE_FONT, in);
            } catch (final FontFormatException | IOException ex) {
                Log.warning(ex);
                font = new Font(fallbackFontName, fallbackFontStyle, 1);
            }
        }

        return font;
    }

    /**
     * Performs layout for a {@code VariableReference}.
     *
     * @param context the display context
     * @param object  the object to lay out
     * @param layout  the layout object to populate
     */
    public void layoutVariableReference(final ExpressionDisplayContext context, final VariableReference object,
                                        final VariableReferenceLayout layout) {

    }
}
