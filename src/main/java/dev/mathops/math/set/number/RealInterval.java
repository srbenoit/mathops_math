package dev.mathops.math.set.number;

import dev.mathops.math.geom.GeomConstants;

/**
 * An interval on the real line. An empty interval can be represented by making the lower bound greater than the upper.
 */
public class RealInterval {

    /** The unit interval from 0.0 to 1.0, which is commonly used. */
    public static final RealInterval UNIT_INTERVAL = new RealInterval(0, 1);

    /** An empty interval. */
    public static final RealInterval EMPTY_INTERVAL = new RealInterval(1, 0);

    /** The lower bound. */
    public final double lowerBound;

    /** The upper bound. */
    public final double upperBound;

    /**
     * Constructs a new {@code RealInterval}.
     *
     * @param theLowerBound the lower bound
     * @param theUpperBound the upper bound
     */
    public RealInterval(final double theLowerBound, final double theUpperBound) {

        this.lowerBound = theLowerBound;
        this.upperBound = theUpperBound;
    }

    /**
     * Parses a {code RealInterval} from its string representation.  Format:
     * <pre>
     *     (lower-bound, upper-bound)
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed interval
     * @throws NumberFormatException    if the x or y coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static RealInterval parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        final int first = (int) trimmed.charAt(0);
        final int last = (int) trimmed.charAt(len - 1);

        if (first == '(' && last == ')') {
            int index = trimmed.indexOf(GeomConstants.COMMA);
            if (index == -1) {
                throw makeBadIntervalStringException(str);
            }

            final String xStr = trimmed.substring(1, index);
            final String xTrimmed = xStr.trim();
            final double x = Double.parseDouble(xTrimmed);

            final String yStr = trimmed.substring(index + 1, len - 1);
            final String yTrimmed = yStr.trim();
            final double y = Double.parseDouble(yTrimmed);

            return new RealInterval(x, y);
        } else {
            throw makeBadIntervalStringException(str);
        }
    }

    /**
     * Creates an exception that indicates the interval string is invalid.
     *
     * @param str the interval string
     * @return the exception
     */
    private static IllegalArgumentException makeBadIntervalStringException(final String str) {

        final String badVectorStr = Res.fmt(Res.BAD_INTERVAL_STR, str);
        return new IllegalArgumentException(badVectorStr);
    }

    /**
     * Returns a new {@code ReaInterval} whose range is the intersection of this interval and another.
     *
     * @param other the other interval with which to intersect.
     * @return the result; null if the intervals do not intersect
     */
    public RealInterval intersect(final RealInterval other) {

        final RealInterval result;

        if (this.upperBound < other.lowerBound || this.lowerBound > other.upperBound) {
            result = null;
        } else {
            final double maxLower = Math.max(this.lowerBound, other.lowerBound);
            final double minUpper = Math.min(this.upperBound, other.upperBound);
            result = new RealInterval(maxLower, minUpper);
        }

        return result;

    }

    /**
     * Gets the extent of the interval, which is the upper bound minus the lower bound, or 0 if these bounds are equal
     * or the upper is less than the lower.
     *
     * @return the extent
     */
    public final double extent() {

        return this.upperBound < this.lowerBound ? 0.0 : this.upperBound - this.lowerBound;
    }

    /**
     * Tests whether this interval is empty.
     *
     * @return true if this is an empty interval
     */
    public final boolean isEmpty() {

        return this.upperBound < this.lowerBound;
    }

    /**
     * Tests whether a point is contained in this interval.
     *
     * @param x the point to test
     * @return true if the point lies in this interval (or at an endpoint); false if not
     */
    public final boolean contains(final double x) {

        return x >= this.lowerBound && x <= this.upperBound;
    }

    /**
     * Tests whether an interval is contained in this interval.
     *
     * @param x the interval to test
     * @return true if the test interval falls entirely within this interval; false if not (an empty interval is not
     *         considered to be "contained" in any interval)
     */
    public final boolean contains(final RealInterval x) {

        final boolean result;

        if (isEmpty() || x.isEmpty()) {
            result = false;
        } else {
            result = x.lowerBound >= this.lowerBound && x.upperBound <= this.upperBound;
        }

        return result;
    }

    /**
     * Clamps a value to this domain.
     *
     * @param value the value to clamp
     * @return the nearest value in this domain to {code value} (if the input value is NaN, the output is NaN)
     */
    public final double clamp(final double value) {

        final double result;

        return Double.isNaN(value) ? value : Math.max(this.lowerBound, Math.min(value, this.upperBound));
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        return "(" + (float) this.lowerBound + ", " + (float) this.upperBound + ')';
    }
}
