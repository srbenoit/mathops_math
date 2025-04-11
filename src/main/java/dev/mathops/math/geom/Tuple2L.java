package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A base class for points and vectors in the plane with {@code long} coordinates.
 * <p>
 * Coordinate values should be limited to 24 bits or fewer to ensure operations cannot overflow.
 * <p>
 * The type is encoded in the string representation by choice of fence characters.
 * <pre>
 *     Point: (1, 2)
 *     Vector: [1, 2]
 *     Normal vector: {1, 2} (should not be used for points with long coordinates)
 * </pre>
 */
public final class Tuple2L {

    /** The x coordinate. */
    public final long x;

    /** The y coordinate. */
    public final long y;

    /** The tuple's type. */
    public final ETupleType type;

    /**
     * Constructs a new {@code Tuple2L} with specified coordinates.
     *
     * @param xx      the x coordinate
     * @param yy      the y coordinate
     * @param theType the tuple's type
     * @throws IllegalArgumentException if the type is null
     */
    public Tuple2L(final long xx, final long yy, final ETupleType theType) throws IllegalArgumentException {

        if (theType == null) {
            final String msg = Res.get(Res.NULL_TUPLE_TYPE);
            throw new IllegalArgumentException(msg);
        }

        this.x = xx;
        this.y = yy;
        this.type = theType;
    }

    /**
     * Constructs a new {@code Tuple2L} with coordinates taken from another {@code Tuple2L}.
     *
     * @param src the source whose coordinates to copy
     */
    public Tuple2L(final Tuple2L src) {

        this.x = src.x;
        this.y = src.y;
        this.type = src.type;
    }

    /**
     * Parses a {@code Tuple2L} from its string representation.
     * <pre>
     *     Point: (x, y)
     *     Vector: [x, y]
     *     Normal vector: {x, y} (should not be used for tuples with long coordinates)
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed tuple
     * @throws NumberFormatException    if the x or y coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static Tuple2L parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        if (len < 5) {
            final String badVectorStr = Res.fmt(Res.BAD_TUPLE_STR, str);
            throw new IllegalArgumentException(badVectorStr);
        }

        final ETupleType type;
        final int first = (int) trimmed.charAt(0);
        final int last = (int) trimmed.charAt(len - 1);

        if (first == '(' && last == ')') {
            type = ETupleType.POINT;
        } else if (first == '[' && last == ']') {
            type = ETupleType.VECTOR;
        } else if (first == '{' && last == '}') {
            type = ETupleType.NORMAL_VECTOR;
        } else {
            final String badVectorStr = Res.fmt(Res.BAD_TUPLE_STR, str);
            throw new IllegalArgumentException(badVectorStr);
        }

        final int index = trimmed.indexOf(GeomConstants.COMMA);
        if (index == -1) {
            final String badVectorStr = Res.fmt(Res.BAD_TUPLE_STR, str);
            throw new IllegalArgumentException(badVectorStr);
        }

        final String xStr = trimmed.substring(1, index);
        final String xTrimmed = xStr.trim();
        final long x = Long.parseLong(xTrimmed);

        final String yStr = trimmed.substring(index + 1, len - 1);
        final String yTrimmed = yStr.trim();
        final long y = Long.parseLong(yTrimmed);

        return new Tuple2L(x, y, type);
    }

    /**
     * Returns this tuple as a particular type (returns this tuple unaltered if it is already of the requested type, or
     * a new tuple with type set to that type if not).
     *
     * @param newType the requested type
     * @return the tuple
     */
    public final Tuple2L as(final ETupleType newType) {

        return this.type == newType ? this : new Tuple2L(this.x, this.y, newType);
    }

    /**
     * Returns the minimum coordinate of this tuple.
     *
     * @return the minimum coordinate
     */
    public final long minComponent() {

        return Math.min(this.x, this.y);
    }

    /**
     * Returns the maximum coordinate of this tuple.
     *
     * @return the maximum coordinate
     */
    public final long maxComponent() {

        return Math.max(this.x, this.y);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple added to those of another tuple.
     *
     * @param toAdd      the tuple to add
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public final Tuple2L sum(final Tuple2L toAdd, final ETupleType resultType) {

        return new Tuple2L(this.x + toAdd.x, this.y + toAdd.y, resultType);
    }

    /**
     * Computes the result of adding two tuples, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public static Tuple2L sum(final Tuple2L tup1, final Tuple2L tup2, final ETupleType resultType) {

        return new Tuple2L(tup1.x + tup2.x, tup2.y + tup2.y, resultType);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple minus those of another tuple.
     *
     * @param toSubtract the tuple to subtract
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public final Tuple2L difference(final Tuple2L toSubtract, final ETupleType resultType) {

        return new Tuple2L(this.x - toSubtract.x, this.y - toSubtract.y, resultType);
    }

    /**
     * Computes the result of subtracting one tuple from another, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the difference as a tuple
     */
    public static Tuple2L difference(final Tuple2L tup1, final Tuple2L tup2, final ETupleType resultType) {

        return new Tuple2L(tup1.x - tup2.x, tup1.y - tup2.y, resultType);
    }

    /**
     * Creates the negation of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @return the negated tuple
     */
    public final Tuple2L negated() {

        return new Tuple2L(-this.x, -this.y, this.type);
    }

    /**
     * Creates the negation of a tuple as a new tuple.  The source tuple's type is retained.
     *
     * @param src the tuple whose coordinates to negate
     * @return the negated tuple
     */
    public static Tuple2L negated(final Tuple2L src) {

        return new Tuple2L(-src.x, -src.y, src.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param factor the factor by which to scale the tuple's coordinates
     * @return the scaled tuple
     */
    public final Tuple2L scaled(final long factor) {

        return new Tuple2L(this.x * factor, this.y * factor, this.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param xFactor the factor by which to scale the tuple's x coordinate
     * @param yFactor the factor by which to scale the tuple's y coordinate
     * @return the scaled tuple
     */
    public final Tuple2L scaled(final long xFactor, final long yFactor) {

        return new Tuple2L(this.x * xFactor, this.y * yFactor, this.type);
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final int hashCode() {

        return Long.hashCode(this.x) + Long.hashCode(this.y) + this.type.hashCode();
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final boolean equals(final Object obj) {

        final boolean equal;

        if (this == obj) {
            equal = true;
        } else if (obj instanceof final Tuple2L tuple) {
            equal = tuple.type == this.type && tuple.x == this.x && tuple.y == this.y;
        } else {
            equal = false;
        }

        return equal;
    }

    /**
     * Generates the string representation of the tuple.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final CharHtmlBuilder builder = new CharHtmlBuilder(32);

        switch (this.type) {
            case POINT -> builder.addChar('(');
            case VECTOR -> builder.addChar('[');
            case NORMAL_VECTOR -> builder.addChar('{');
        }

        final String xStr = Long.toString(this.x);
        final String yStr = Long.toString(this.y);
        builder.addStrings(xStr, ", ", yStr);

        switch (this.type) {
            case POINT -> builder.addChar(')');
            case VECTOR -> builder.addChar(']');
            case NORMAL_VECTOR -> builder.addChar('}');
        }

        return builder.toString();
    }
}
