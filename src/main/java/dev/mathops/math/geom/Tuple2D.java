package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A point, vector, or normal vectors in the plane with {@code double} coordinates.
 * <p>
 * The type is encoded in the string representation by choice of fence characters.
 * <pre>
 *     Point: (1, 2)
 *     Vector: [1, 2]
 *     Normal vector: {1, 2}
 * </pre>
 */
public final class Tuple2D {

    /** The X coordinate of this {@code Tuple2D}. */
    public final double x;

    /** The Y coordinate of this {@code Tuple2D}. */
    public final double y;

    /** The tuple's type. */
    public final ETupleType type;

    /**
     * Constructs a new {@code Tuple2D} with specified coordinates.
     *
     * @param theX    the x coordinate
     * @param theY    the y coordinate
     * @param theType the tuple's type
     * @throws IllegalArgumentException if either coordinate is not finite, or the type is null
     */
    public Tuple2D(final double theX, final double theY, final ETupleType theType) throws IllegalArgumentException {

        if (theType == null) {
            final String msg = Res.get(Res.NULL_TUPLE_TYPE);
            throw new IllegalArgumentException(msg);
        }

        if (Double.isFinite(theX) && Double.isFinite(theY)) {
            this.x = theX;
            this.y = theY;
            this.type = theType;
        } else {
            final String msg = Res.get(Res.BAD_COORDINATES);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Constructs a new {@code Tuple2D} with coordinates taken from another {@code Tuple2D}.
     *
     * @param src the source whose coordinates to copy
     */
    public Tuple2D(final Tuple2D src) {

        this.x = src.x;
        this.y = src.y;
        this.type = src.type;
    }

    /**
     * Returns a {code Tuple2D} whose components are specified in polar coordinates (r, theta).
     * <pre>
     * x = r cos(theta)
     * y = r sin(theta)
     * </pre>
     *
     * @param r        the radius
     * @param thetaDeg the theta coordinate (angle from the 0 meridian, in degrees)
     * @param type     the type for the new tuple
     * @return the generated {code Tuple2D}
     */
    public static Tuple2D forPolar(final double r, final double thetaDeg, final ETupleType type) {

        final double x;
        final double y;

        if (thetaDeg == 0.0) {
            x = r;
            y = 0.0;
        } else if (thetaDeg == 90.0) {
            x = 0.0;
            y = r;
        } else if (thetaDeg == 180.0) {
            x = -r;
            y = 0.0;
        } else if (thetaDeg == 270.0) {
            x = 0.0;
            y = -r;
        } else {
            final double rad = Math.toRadians(thetaDeg);
            x = r * Math.cos(rad);
            y = r * Math.sin(rad);
        }

        return new Tuple2D(x, y, type);
    }

    /**
     * Parses a {code Tuple2D} from its string representation.  This can either be in Cartesian form:
     * <pre>
     *     Point: (x, y)
     *     Vector: [x, y]
     *     Normal vector: {x, y}
     * </pre>
     *
     * <p>
     * or in Polar form (distinguished by the use of ';' rather than ',' to separate components)
     * <pre>
     *     Point: (R; ThetaDeg)
     *     Vector: [R; ThetaDeg]
     *     Normal vector: {R; ThetaDeg}
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed vector
     * @throws NumberFormatException    if the x or y coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static Tuple2D parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trimmed = str.trim();
        final int len = trimmed.length();

        if (len < 5) {
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

        boolean isPolar = false;
        int index = trimmed.indexOf(GeomConstants.COMMA);
        if (index == -1) {
            index = trimmed.indexOf(GeomConstants.SEMICOLON);
            if (index == -1) {
                throw makeBadTupleStringException(str);
            }
            isPolar = true;
        }

        final String xStr = trimmed.substring(1, index);
        final String xTrimmed = xStr.trim();
        final double x = Double.parseDouble(xTrimmed);

        final String yStr = trimmed.substring(index + 1, len - 1);
        final String yTrimmed = yStr.trim();
        final double y = Double.parseDouble(yTrimmed);

        return isPolar ? Tuple2D.forPolar(x, y, type) : new Tuple2D(x, y, type);
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
    public final Tuple2D as(final ETupleType newType) {

        return this.type == newType ? this : new Tuple2D(this.x, this.y, newType);
    }

    /**
     * Gets the r coordinate.
     *
     * @return the r coordinate
     */
    public final double getR() {

        return length();
    }

    /**
     * Gets the theta coordinate.
     *
     * @return the theta coordinate, in degrees, in the range 0 <= result < 360.0
     */
    public final double getThetaDeg() {

        final double result;

        if (this.y == 0.0) {
            result = this.x >= 0.0 ? 0.0 : 180.0;
        } else if (this.x == 0.0) {
            result = this.y >= 0.0 ? 90.0 : 270.0;
        } else {
            final double radians = Math.atan2(this.y, this.x);
            result = Math.toDegrees(radians);
        }

        return result;
    }

    /**
     * Calculates the square of the length of the vector.
     *
     * @return the squared length
     */
    public final double lengthSquared() {

        return this.x * this.x + this.y * this.y;
    }

    /**
     * Calculates the length of the vector.
     *
     * @return the length
     */
    public final double length() {

        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Computes the square of the distance between two tuples.
     *
     * @param p1 the first tuple
     * @param p2 the second tuple
     * @return the square of the distance
     */
    public static double distanceSquared(final Tuple2D p1, final Tuple2D p2) {

        final double dx = p1.x - p2.x;
        final double dy = p1.y - p2.y;

        return dx * dx + dy * dy;
    }

    /**
     * Computes the distance between two tuples.
     *
     * @param tup1 the first tuple
     * @param tup2 the second tuple
     * @return the distance
     */
    public static double distance(final Tuple2D tup1, final Tuple2D tup2) {

        final double distSq = distanceSquared(tup1, tup2);
        return Math.sqrt(distSq);
    }

    /**
     * Returns the minimum coordinate of this tuple.
     *
     * @return the minimum coordinate
     */
    public final double minComponent() {

        return Math.min(this.x, this.y);
    }

    /**
     * Returns the maximum coordinate of this tuple.
     *
     * @return the maximum coordinate
     */
    public final double maxComponent() {

        return Math.max(this.x, this.y);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple added to those of another.
     *
     * @param toAdd      the tuple to add
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public final Tuple2D sum(final Tuple2D toAdd, final ETupleType resultType) {

        return new Tuple2D(this.x + toAdd.x, this.y + toAdd.y, resultType);
    }

    /**
     * Computes the result of adding two tuples, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public static Tuple2D sum(final Tuple2D tup1, final Tuple2D tup2, final ETupleType resultType) {

        return new Tuple2D(tup1.x + tup2.x, tup1.y + tup2.y, resultType);
    }

    /**
     * Creates a new vector whose coordinates are those of this vector minus those of another tuple.
     *
     * @param toSubtract the tuple to subtract
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public final Tuple2D difference(final Tuple2D toSubtract, final ETupleType resultType) {

        return new Tuple2D(this.x - toSubtract.x, this.y - toSubtract.y, resultType);
    }

    /**
     * Computes the result of subtracting one tuple from another, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the difference as a vector
     */
    public static Tuple2D difference(final Tuple2D tup1, final Tuple2D tup2, final ETupleType resultType) {

        return new Tuple2D(tup1.x - tup2.x, tup1.y - tup2.y, resultType);
    }

    /**
     * Computes the midpoint between two tuples.  The result has the same type as the first tuple provided
     * ({@code tup1}).
     *
     * @param tup1 the first tuple
     * @param tup2 the second tuple
     * @return the midpoint tuple
     */
    public static Tuple2D midpoint(final Tuple2D tup1, final Tuple2D tup2) {

        return new Tuple2D((tup1.x + tup2.x) * 0.5, (tup1.y + tup2.y) * 0.5, tup1.type);
    }

    /**
     * Creates the negation of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @return the negated tuple
     */
    public final Tuple2D negated() {

        return new Tuple2D(-this.x, -this.y, this.type);
    }

    /**
     * Creates the negation of a tuple as a new tuple.  The source tuple's type is retained.
     *
     * @param src the tuple whose coordinates to negate
     * @return the negated tuple
     */
    public static Tuple2D negated(final Tuple2D src) {

        return new Tuple2D(-src.x, -src.y, src.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param factor the factor by which to scale the tuple's coordinates
     * @return the scaled tuple
     */
    public final Tuple2D scaled(final double factor) {

        return new Tuple2D(this.x * factor, this.y * factor, this.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param xFactor the factor by which to scale the tuple's x coordinate
     * @param yFactor the factor by which to scale the tuple's y coordinate
     * @return the scaled tuple
     */
    public final Tuple2D scaled(final double xFactor, final double yFactor) {

        return new Tuple2D(this.x * xFactor, this.y * yFactor, this.type);
    }

    /**
     * Computes a vector by linearly interpolating two given tuples.  The result has the type of the first tuple given
     * ({@code p1}).
     *
     * @param p1 the first tuple (at t = 0)
     * @param p2 the second tuple (at t = 1)
     * @param t  the parameter value at which to interpolate/extrapolate
     * @return the resulting tuple (1 - t)p1 + (t)p2
     */
    public static Tuple2D lerp(final Tuple2D p1, final Tuple2D p2, final double t) {

        final double oneMinusT = 1.0 - t;
        final double xx = oneMinusT * p1.x + t * p2.x;
        final double yy = oneMinusT * p1.y + t * p2.y;

        return new Tuple2D(xx, yy, p1.type);
    }

    /**
     * Returns a new {@code Tuple2D} where the coordinates lie on the unit circle.  The tuple's type is retained.
     *
     * @return the normalized tuple; a tuple with coordinates x = 1, y = 0 if the given tuple has length 0
     */
    public final Tuple2D normalized() {

        final double xx;
        final double yy;

        final double len = length();
        if (len == 0.0) {
            xx = 1.0;
            yy = 0.0;
        } else {
            final double factor = 1.0 / len;
            xx = this.x * factor;
            yy = this.y * factor;
        }

        return new Tuple2D(xx, yy, this.type);
    }

    /**
     * Returns a new tuple whose components are the absolute value of the components of this tuple.  The tuple's type is
     * retained.
     *
     * @return the new tuple
     */
    public final Tuple2D abs() {

        final double absX = Math.abs(this.x);
        final double absY = Math.abs(this.y);

        return new Tuple2D(absX, absY, this.type);
    }

    /**
     * Computes the dot product of this vector with another tuple.
     *
     * @param vec the other tuple
     * @return the dot product
     */
    public final double dot(final Tuple2D vec) {

        return vec.x * this.x + vec.y * this.y;
    }

    /**
     * Computes the absolute value of the dot product of this vector with another tuple.
     *
     * @param vec the other tuple
     * @return the absolute value of the dot product
     */
    public final double absdot(final Tuple2D vec) {

        final double dot = dot(vec);
        return Math.abs(dot);
    }

    /**
     * Computes the vector from a start point to an end point.  The result has type VECTOR.
     *
     * @param start the start point
     * @param end   the end point
     * @return the vector (end - start)
     */
    public static Tuple2D vectorBetween(final Tuple2D start, final Tuple2D end) {

        return new Tuple2D(end.x - start.x, end.y - start.y, ETupleType.VECTOR);
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final int hashCode() {

        return Double.hashCode(this.x) + Double.hashCode(this.y) + this.type.hashCode();
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
        } else if (obj instanceof final Tuple2D tuple) {
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

        final String xStr = Double.toString(this.x);
        final String yStr = Double.toString(this.y);
        builder.addStrings(xStr, ", ", yStr);

        switch (this.type) {
            case POINT -> builder.addChar(')');
            case VECTOR -> builder.addChar(']');
            case NORMAL_VECTOR -> builder.addChar('}');
        }

        return builder.toString();
    }

    /**
     * Generates the string representation of the tuple in polar coordinates.
     *
     * @return the string representation
     */
    public final String toPolarString() {

        final CharHtmlBuilder builder = new CharHtmlBuilder(32);

        switch (this.type) {
            case POINT -> builder.addChar('(');
            case VECTOR -> builder.addChar('[');
            case NORMAL_VECTOR -> builder.addChar('{');
        }

        final double r = getR();
        final double thetaDeg = getThetaDeg();

        final String rStr = Double.toString(r);
        final String thetaStr = Double.toString(thetaDeg);
        builder.addStrings(rStr, "; ", thetaStr);

        switch (this.type) {
            case POINT -> builder.addChar(')');
            case VECTOR -> builder.addChar(']');
            case NORMAL_VECTOR -> builder.addChar('}');
        }

        return builder.toString();
    }
}
