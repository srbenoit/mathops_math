package dev.mathops.math.geom;

import dev.mathops.text.builder.CharSimpleBuilder;
import dev.mathops.commons.CoreConstants;

/**
 * An axis-aligned 2D rectangle specified by a corner point and a width and height.
 */
public class Rect2L {

    /** The x coordinate of the corner. */
    public final long x;

    /** The y coordinate of the corner. */
    public final long y;

    /** The width (could be negative). */
    public final long w;

    /** The height (could be negative). */
    public final long h;

    /**
     * Constructs a new {@code Rect2L} with specified corner coordinates, width, and height.
     *
     * @param theX the x coordinate of the corner
     * @param theY the y coordinate of the corner
     * @param theW the width
     * @param theH the height
     */
    public Rect2L(final long theX, final long theY, final long theW, final long theH) {

        this.x = theX;
        this.y = theY;
        this.w = theW;
        this.h = theH;
    }

    /**
     * Gets the maximum x coordinate of the rectangle.
     *
     * @return the maximum x coordinate
     */
    public final long maxX() {

        return this.x + this.w;
    }

    /**
     * Gets the maximum y coordinate of the rectangle.
     *
     * @return the maximum y coordinate
     */
    public final long maxY() {

        return this.y + this.h;
    }

    /**
     * Parses a rectangle from its string representation, like "1 2 3 4"
     *
     * @param str the string to parse
     * @return the parsed rectangle
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any of the coordinates in an invalid number
     */
    public static Rect2L parse4Numbers(final String str) throws IllegalArgumentException, NumberFormatException {

        final String[] parts = str.trim().split(CoreConstants.SPC);

        if (parts.length != 4) {
            final String msg = Res.fmt(Res.BAD_RECT_STR, str);
            throw new IllegalArgumentException(msg);
        }

        final long x = Long.parseLong(parts[0]);
        final long y = Long.parseLong(parts[1]);
        final long w = Long.parseLong(parts[2]);
        final long h = Long.parseLong(parts[3]);

        return new Rect2L(x, y, w, h);
    }

    /**
     * Parses a rectangle from its string representation, like "3x4@[1,2]"
     *
     * @param str the string to parse
     * @return the parsed rectangle
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any of the coordinates in an invalid number
     */
    public static Rect2L parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();

        final int len = trimmed.length();
        if ((int) trimmed.charAt(len - 1) != GeomConstants.RBRACKET) {
            final String msg = Res.fmt(Res.BAD_RECT_STR, str);
            throw new IllegalArgumentException(msg);
        }

        final int atPos = trimmed.indexOf("@[");
        final int xPos = trimmed.indexOf(GeomConstants.X);
        final int commaPos = trimmed.indexOf(GeomConstants.COMMA);

        if (xPos < 0 || atPos < xPos || commaPos < xPos) {
            final String msg = Res.fmt(Res.BAD_RECT_STR, str);
            throw new IllegalArgumentException(msg);
        }

        final String wStr = trimmed.substring(0, xPos);
        final String wTrimmed = wStr.trim();
        final long w = Long.parseLong(wTrimmed);

        final String hStr = trimmed.substring(xPos + 1, atPos);
        final String hTrimmed = hStr.trim();
        final long h = Long.parseLong(hTrimmed);

        final String xStr = trimmed.substring(atPos + 2, commaPos);
        final String xTrimmed = xStr.trim();
        final long x = Long.parseLong(xTrimmed);

        final String yStr = trimmed.substring(commaPos + 1, len - 1);
        final String yTrimmed = yStr.trim();
        final long y = Long.parseLong(yTrimmed);

        return new Rect2L(x, y, w, h);
    }

    /**
     * Generates the string representation of the rectangle.
     *
     * @return the string representation, such as "1x2@[3,4]" to indicate a 1 unit wide by 2 units high rectangle with
     *         its base corner at (3, 4)
     */
    @Override
    public final String toString() {

        final String wStr = Long.toString(this.w);
        final String hStr = Long.toString(this.h);
        final String xStr = Long.toString(this.x);
        final String yStr = Long.toString(this.y);

        return CharSimpleBuilder.concat(wStr, "x", hStr, "@[", xStr, ",", yStr, "]");
    }
}
