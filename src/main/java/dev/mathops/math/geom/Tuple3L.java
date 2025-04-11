package dev.mathops.math.geom;

import dev.mathops.text.builder.CharSimpleBuilder;

/**
 * A base class for points and vectors in space with {@code long} coordinates.
 * <p>
 * Coordinate values should be limited to 24 bits or fewer to ensure operations cannot overflow.
 */
public final class Tuple3L {

    /** The x coordinate. */
    public final long x;

    /** The z coordinate. */
    public final long y;

    /** The y coordinate. */
    public final long z;

    /** The tuple's type. */
    public final ETupleType type;

    /**
     * Constructs a new {@code Tuple3L} with specified coordinates.
     *
     * @param xx      the x coordinate
     * @param yy      the y coordinate
     * @param zz      the z coordinate
     * @param theType the tuple's type
     * @throws IllegalArgumentException if the type is null
     */
    public Tuple3L(final long xx, final long yy, final long zz, final ETupleType theType) {

        if (theType == null) {
            final String msg = Res.get(Res.NULL_TUPLE_TYPE);
            throw new IllegalArgumentException(msg);
        }

        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.type = theType;
    }

    /**
     * Constructs a new {@code Tuple3L} with coordinates taken from another {@code Tuple3L}.
     *
     * @param src the source whose coordinates to copy
     */
    public Tuple3L(final Tuple3L src) {

        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
        this.type = src.type;
    }

    /**
     * Parses a {@code Tuple3L} from its string representation.
     * <pre>
     *     Point: (x, y, z)
     *     Vector: [x, y, z]
     *     Normal vector: {x, y, z} (should not be used for tuples with long coordinates)
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed tuple
     * @throws NumberFormatException    if the x or y or z coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static Tuple3L parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        if (len < 7) {
            throw makeBadTupleStringException(str);
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
            throw makeBadTupleStringException(str);
        }

        final int index1 = trimmed.indexOf(GeomConstants.COMMA);
        if (index1 == -1) {
            throw makeBadTupleStringException(str);
        }

        final int index2 = trimmed.indexOf(GeomConstants.COMMA, index1 + 1);
        if (index2 == -1) {
            throw makeBadTupleStringException(str);
        }

        final String xStr = trimmed.substring(1, index1);
        final String xTrimmed = xStr.trim();
        final long x = Long.parseLong(xTrimmed);

        final String yStr = trimmed.substring(index1 + 1, index2);
        final String yTrimmed = yStr.trim();
        final long y = Long.parseLong(yTrimmed);

        final String zStr = trimmed.substring(index2 + 1, len - 1);
        final String zTrimmed = zStr.trim();
        final long z = Long.parseLong(zTrimmed);

        return new Tuple3L(x, y, z, type);
    }

    /**
     * Creates an exception that indicates the tuple string is invalid.
     *
     * @param str the tuple string
     * @return the exception
     */
    private static IllegalArgumentException makeBadTupleStringException(final String str) {

        final String badVectorStr = Res.fmt(Res.BAD_TUPLE_STR, str);
        return new IllegalArgumentException(badVectorStr);
    }

    /**
     * Returns the minimum coordinate of this tuple.
     *
     * @return the minimum coordinate
     */
    public final long minComponent() {

        return Math.min(Math.min(this.x, this.y), this.z);
    }

    /**
     * Returns the maximum coordinate of this tuple.
     *
     * @return the maximum coordinate
     */
    public final long maxComponent() {

        return Math.max(Math.max(this.x, this.y), this.z);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple added to those of another tuple.
     *
     * @param toAdd      the tuple to add
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public final Tuple3L sum(final Tuple3L toAdd, final ETupleType resultType) {

        return new Tuple3L(this.x + toAdd.x, this.y + toAdd.y, this.z + toAdd.z, resultType);
    }

    /**
     * Computes the result of adding two tuples, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public static Tuple3L sum(final Tuple3L tup1, final Tuple3L tup2, final ETupleType resultType) {

        return new Tuple3L(tup1.x + tup2.x, tup2.y + tup2.y, tup2.z + tup2.z, resultType);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple minus those of another tuple.
     *
     * @param toSubtract the tuple to subtract
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public final Tuple3L difference(final Tuple3L toSubtract, final ETupleType resultType) {

        return new Tuple3L(this.x - toSubtract.x, this.y - toSubtract.y, this.z - toSubtract.z, resultType);
    }

    /**
     * Computes the result of subtracting one tuple from another, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public static Tuple3L difference(final Tuple3L tup1, final Tuple3L tup2, final ETupleType resultType) {

        return new Tuple3L(tup1.x - tup2.x, tup1.y - tup2.y, tup1.z - tup2.z, resultType);
    }

    /**
     * Creates the negation of this point as a new tuple.  The tuple's type is retained.
     *
     * @return the negated tuple
     */
    public final Tuple3L negated() {

        return new Tuple3L(-this.x, -this.y, -this.z, this.type);
    }

    /**
     * Creates the negation of a tuple as a new tuple.  The source tuple's type is retained.
     *
     * @param src the tuple whose coordinates to negate
     * @return the negated tuple
     */
    public static Tuple3L negated(final Tuple3L src) {

        return new Tuple3L(-src.x, -src.y, -src.z, src.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param factor the factor by which to scale the tuple's coordinates
     * @return the scaled tuple
     */
    public final Tuple3L scaled(final long factor) {

        return new Tuple3L(this.x * factor, this.y * factor, this.z * factor, this.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param xFactor the factor by which to scale the tuple's x coordinate
     * @param yFactor the factor by which to scale the tuple's y coordinate
     * @param zFactor the factor by which to scale the tuple's z coordinate
     * @return the scaled tuple
     */
    public final Tuple3L scaled(final long xFactor, final long yFactor, final long zFactor) {

        return new Tuple3L(this.x * xFactor, this.y * yFactor, this.z * zFactor, this.type);
    }


    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final int hashCode() {

        return Long.hashCode(this.x) + Long.hashCode(this.y) + Long.hashCode(this.z);
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
        } else if (obj instanceof final Tuple3L tuple) {
            equal = tuple.x == this.x && tuple.y == this.y && tuple.z == this.z;
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

        final String xStr = Long.toString(this.x);
        final String yStr = Long.toString(this.y);
        final String zStr = Long.toString(this.z);

        return CharSimpleBuilder.concat("[", xStr, ", ", yStr, ", ", zStr, "]");
    }

}
