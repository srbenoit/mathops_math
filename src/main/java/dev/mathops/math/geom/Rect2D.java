package dev.mathops.math.geom;

import dev.mathops.text.builder.CharSimpleBuilder;
import dev.mathops.commons.CoreConstants;

/**
 * An axis-aligned 2D rectangle specified by a corner point and a width and height.
 */
public class Rect2D {

    /** The x coordinate of the corner. */
    public final double x;

    /** The y coordinate of the corner. */
    public final double y;

    /** The width (could be negative). */
    public final double w;

    /** The height (could be negative). */
    public final double h;

    /**
     * Constructs a new {@code Rect2D} with specified corner coordinates, width, and height.
     *
     * @param theX the x coordinate of the corner
     * @param theY the y coordinate of the corner
     * @param theW the width
     * @param theH the height
     */
    public Rect2D(final double theX, final double theY, final double theW, final double theH) {

        this.x = theX;
        this.y = theY;
        this.w = theW;
        this.h = theH;
    }

    /**
     * Parses a rectangle from its string representation, like "1 2 3 4"
     *
     * @param str the string to parse
     * @return the parsed rectangle
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any of the coordinates in an invalid number
     */
    public static Rect2D parse4Numbers(final String str) throws IllegalArgumentException, NumberFormatException {

        if (str == null) {
            throw new IllegalArgumentException("String to parse may not be null.");
        }
        final String[] parts = str.trim().split(CoreConstants.SPC);

        if (parts.length != 4) {
            final String msg = Res.fmt(Res.BAD_RECT_STR, str);
            throw new IllegalArgumentException(msg);
        }

        final double x = Double.parseDouble(parts[0]);
        final double y = Double.parseDouble(parts[1]);
        final double w = Double.parseDouble(parts[2]);
        final double h = Double.parseDouble(parts[3]);

        return new Rect2D(x, y, w, h);
    }

    /**
     * Parses a rectangle from its string representation, like "3x4@[1,2]" or "3x4"
     *
     * @param str the string to parse
     * @return the parsed rectangle
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any of the coordinates in an invalid number
     */
    public static Rect2D parse(final String str) throws IllegalArgumentException, NumberFormatException {

        if (str == null) {
            throw new IllegalArgumentException("String to parse may not be null.");
        }

        final String trimmed = str.trim();
        final int len = trimmed.length();

        final Rect2D result;

        final int atPos = trimmed.indexOf(GeomConstants.AT);

        if (atPos == -1) {
            final int xPos = trimmed.indexOf(GeomConstants.X);
            if (xPos < 0) {
                final String msg = Res.fmt(Res.BAD_RECT_STR, str);
                throw new IllegalArgumentException(msg);
            }

            final String wStr = trimmed.substring(0, xPos);
            final String wTrimmed = wStr.trim();
            final double w = Double.parseDouble(wTrimmed);

            final String hStr = trimmed.substring(xPos + 1);
            final String hTrimmed = hStr.trim();
            final double h = Double.parseDouble(hTrimmed);

            result = new Rect2D(0.0, 0.0, w, h);
        } else {
            if (((int) trimmed.charAt(len - 1) != GeomConstants.RBRACKET)
                    || (trimmed.length() <= atPos + 1)
                    || ((int) trimmed.charAt(atPos + 1) != GeomConstants.LBRACKET)) {
                final String msg = Res.fmt(Res.BAD_RECT_STR, str);
                throw new IllegalArgumentException(msg);
            }

            final int xPos = trimmed.indexOf(GeomConstants.X);
            final int commaPos = trimmed.indexOf(GeomConstants.COMMA);

            if (xPos < 0 || atPos < xPos || commaPos < xPos) {
                final String msg = Res.fmt(Res.BAD_RECT_STR, str);
                throw new IllegalArgumentException(msg);
            }

            final String wStr = trimmed.substring(0, xPos);
            final String wTrimmed = wStr.trim();
            final double w = Double.parseDouble(wTrimmed);

            final String hStr = trimmed.substring(xPos + 1, atPos);
            final String hTrimmed = hStr.trim();
            final double h = Double.parseDouble(hTrimmed);

            final String xStr = trimmed.substring(atPos + 2, commaPos);
            final String xTrimmed = xStr.trim();
            final double x = Double.parseDouble(xTrimmed);

            final String yStr = trimmed.substring(commaPos + 1, len - 1);
            final String yTrimmed = yStr.trim();
            final double y = Double.parseDouble(yTrimmed);

            result = new Rect2D(x, y, w, h);
        }

        return result;
    }

    /**
     * Gets the maximum x coordinate of the rectangle.
     *
     * @return the maximum x coordinate
     */
    public final double maxX() {

        return this.x + this.w;
    }

    /**
     * Gets the maximum y coordinate of the rectangle.
     *
     * @return the maximum y coordinate
     */
    public final double maxY() {

        return this.y + this.h;
    }

    /**
     * Tests whether this rectangle contains an (x, y) point.
     *
     * @param xx the x coordinate of the point to test
     * @param yy the y coordinate of the point to test
     * @return true if this rectangle contains the point; false if not (points on the boundary of the rectangle are
     *         considered to be contained)
     */
    public final boolean contains(final double xx, final double yy) {

        return xx >= this.x && yy >= this.y && xx <= maxX() && yy < maxY();
    }

    /**
     * Tests whether this rectangle contains tuple.
     *
     * @param tuple the tuple to test
     * @return true if this rectangle contains the point; false if not (points on the boundary of the rectangle are
     *         considered to be contained)
     */
    public final boolean contains(final Tuple2D tuple) {

        return contains(tuple.x, tuple.y);
    }

    /**
     * Generates the string representation of the rectangle.
     *
     * @return the string representation, such as "1x2@[3,4]" to indicate a 1 unit wide by 2 units high rectangle with
     *         its base corner at (3, 4)
     */
    @Override
    public final String toString() {

        final String wStr = Double.toString(this.w);
        final String hStr = Double.toString(this.h);
        final String xStr = Double.toString(this.x);
        final String yStr = Double.toString(this.y);

        return CharSimpleBuilder.concat(wStr, "x", hStr, "@[", xStr, ",", yStr, "]");
    }
}
