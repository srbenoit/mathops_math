package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A base class for homogeneous points, vectors, and normal vectors in space with {@code double} coordinates.
 */
public final class Tuple4D {

    /** The X coordinate of this {@code Tuple4D}. */
    public final double x;

    /** The Y coordinate of this {@code Tuple4D}. */
    public final double y;

    /** The z coordinate of this {@code Tuple4D}. */
    public final double z;

    /** The w coordinate of this {@code Tuple4D}. */
    public final double w;

    /** The tuple's type. */
    public final ETupleType type;

    /**
     * Constructs a new {@code Tuple4D} with specified coordinates.
     *
     * @param xx      the x coordinate
     * @param yy      the y coordinate
     * @param zz      the z coordinate
     * @param ww      the w coordinate
     * @param theType the tuple's type
     * @throws IllegalArgumentException if either coordinate is not finite
     */
    public Tuple4D(final double xx, final double yy, final double zz, final double ww,
                   final ETupleType theType) throws IllegalArgumentException {

        if (theType == null) {
            final String msg = Res.get(Res.NULL_TUPLE_TYPE);
            throw new IllegalArgumentException(msg);
        }

        if (Double.isFinite(xx) && Double.isFinite(yy) && Double.isFinite(zz) && Double.isFinite(ww)) {
            this.x = xx;
            this.y = yy;
            this.z = zz;
            this.w = ww;
            this.type = theType;
        } else {
            final String msg = Res.get(Res.BAD_COORDINATES);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Constructs a new {@code Tuple3D} with coordinates taken from another {@code Tuple3D}.
     *
     * @param src the source whose coordinates to copy
     */
    public Tuple4D(final Tuple4D src) {

        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
        this.w = src.w;
        this.type = src.type;
    }

    /**
     * Parses a {code Tuple3D} from its string representation.  This can either be in Cartesian form:
     * <pre>
     *     Point: (x, y, z, w)
     *     Vector: [x, y, z, w]
     *     Normal vector: {x, y, z, w}
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed tuple
     * @throws NumberFormatException    if the x or y or z coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static Tuple4D parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        if (len < 9) {
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
        final int index3 = trimmed.indexOf(GeomConstants.COMMA, index2 + 1);
        if (index3 == -1) {
            throw makeBadTupleStringException(str);
        }

        final String xStr = trimmed.substring(1, index1);
        final String xTrimmed = xStr.trim();
        final double x = Double.parseDouble(xTrimmed);

        final String yStr = trimmed.substring(index1 + 1, index2);
        final String yTrimmed = yStr.trim();
        final double y = Double.parseDouble(yTrimmed);

        final String zStr = trimmed.substring(index2 + 1, index3);
        final String zTrimmed = zStr.trim();
        final double z = Double.parseDouble(zTrimmed);

        final String wStr = trimmed.substring(index3 + 1, len - 1);
        final String wTrimmed = wStr.trim();
        final double w = Double.parseDouble(wTrimmed);

        return new Tuple4D(x, y, z, w, type);
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
     * Returns this tuple as a particular type (returns this tuple unaltered if it is already of the requested type, or
     * a new tuple with type set to that type if not).
     *
     * @param newType the requested type
     * @return the tuple
     */
    public final Tuple4D as(final ETupleType newType) {

        return this.type == newType ? this : new Tuple4D(this.x, this.y, this.z, this.w, newType);
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final int hashCode() {

        return Double.hashCode(this.x) + Double.hashCode(this.y) + Double.hashCode(this.z) + Double.hashCode(this.w) + this.type.hashCode();
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
        } else if (obj instanceof final Tuple4D tuple) {
            equal = tuple.type == this.type && tuple.x == this.x && tuple.y == this.y && tuple.z == this.z
                    && tuple.w == this.w;
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

        final String xStr = Double.toString(this.x);
        final String yStr = Double.toString(this.y);
        final String zStr = Double.toString(this.y);
        final String wStr = Double.toString(this.w);
        builder.addStrings(xStr, ", ", yStr, ", ", zStr, ", ", wStr);

        switch (this.type) {
            case POINT -> builder.addChar(')');
            case VECTOR -> builder.addChar(']');
            case NORMAL_VECTOR -> builder.addChar('}');
        }

        return builder.toString();
    }
}
