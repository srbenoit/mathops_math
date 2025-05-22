package dev.mathops.math.set.number;

import dev.mathops.commons.number.NumberParser;
import dev.mathops.math.NumberComparator;
import dev.mathops.math.geom.GeomConstants;

/**
 * An interval on the real line. An empty interval can be represented by making the lower bound greater than the upper.
 */
public class RealInterval extends AbstractRealNumberSet {

    /** The unit interval from 0.0 to 1.0, which is commonly used. */
    public static final RealInterval UNIT_INTERVAL = new RealInterval(0, 1);

    /** An empty interval. */
    public static final RealInterval EMPTY_INTERVAL = new RealInterval(1, 0);

    /** The lower bound. */
    public final Number lowerBound;

    /** The upper bound. */
    public final Number upperBound;

    /**
     * Constructs a new {@code RealInterval}.
     *
     * @param theLowerBound the lower bound
     * @param theUpperBound the upper bound
     */
    public RealInterval(final Number theLowerBound, final Number theUpperBound) {

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
    public static RealInterval parse(final String str) {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        final int first = (int) trimmed.charAt(0);
        final int last = (int) trimmed.charAt(len - 1);

        if (first == '(' && last == ')') {
            final int index = trimmed.indexOf(GeomConstants.COMMA);
            if (index == -1) {
                throw makeBadIntervalStringException(str);
            }

            final String xStr = trimmed.substring(1, index);
            final String xTrimmed = xStr.trim();
            final Number x = NumberParser.parse(xTrimmed);

            final String yStr = trimmed.substring(index + 1, len - 1);
            final String yTrimmed = yStr.trim();
            final Number y = NumberParser.parse(yTrimmed);

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

        final int below = NumberComparator.INSTANCE.compare(this.upperBound, other.lowerBound);

        if (below < 0) {
            result = null;
        } else {
            final int above = NumberComparator.INSTANCE.compare(this.lowerBound, other.upperBound);
            if (above > 0) {
                result = null;
            } else {
                final int compareLower = NumberComparator.INSTANCE.compare(this.lowerBound, other.lowerBound);
                final Number maxLower = compareLower > 0 ? this.lowerBound : other.lowerBound;

                final int compareUpper = NumberComparator.INSTANCE.compare(this.upperBound, other.upperBound);
                final Number minUpper = compareUpper < 0 ? this.upperBound : other.upperBound;

                result = new RealInterval(maxLower, minUpper);
            }
        }

        return result;
    }

    /**
     * Tests whether this interval is empty.
     *
     * @return true if this is an empty interval
     */
    public final boolean isEmpty() {

        final int compare = NumberComparator.INSTANCE.compare(this.lowerBound, this.upperBound);

        return compare < 0;
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        return "(" + this.lowerBound + ", " + this.upperBound + ")";
    }

    /**
     * Tests whether the set contains a number.
     *
     * @param number the number
     * @return {@code true} if the set contains the number; {@code false} if not
     */
    public boolean contains(final Number number) {

        boolean result;

        final int vsLower = NumberComparator.INSTANCE.compare(number, this.lowerBound);
        if (vsLower < 0) {
            // Number is less than lower bound
            result = false;
        } else {
            final int vsUpper = NumberComparator.INSTANCE.compare(number, this.lowerBound);
            result = vsUpper <= 0;
        }

        return result;
    }

    /**
     * Returns the lower bound for the set.
     *
     * @return the lower bound ({@code Double.NEGATIVE_INFINITY} if not bounded below)
     */
    public Number lowerBound() {

        return this.lowerBound;
    }

    /**
     * Returns the upper bound for the set.
     *
     * @return the upper bound ({@code Double.POSITIVE_INFINITY} if not bounded above)
     */
    public Number upperBound() {

        return this.upperBound;
    }
}
