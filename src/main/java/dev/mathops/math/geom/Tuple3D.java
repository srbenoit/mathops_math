package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A base class for points, vectors, and normal vectors in space with {@code double} coordinates.
 */
public final class Tuple3D {

    /** A small value used to test for zero determinant (in linear independence test). */
    public static final double EPSILON = 0.0000001;

    /** The X coordinate of this {@code Tuple3D}. */
    public final double x;

    /** The Y coordinate of this {@code Tuple3D}. */
    public final double y;

    /** The z coordinate of this {@code Tuple3D}. */
    public final double z;

    /** The tuple's type. */
    public final ETupleType type;

    /**
     * Constructs a new {@code Tuple3D} with specified coordinates.
     *
     * @param xx      the x coordinate
     * @param yy      the y coordinate
     * @param zz      the z coordinate
     * @param theType the tuple's type
     * @throws IllegalArgumentException if either coordinate is not finite
     */
    public Tuple3D(final double xx, final double yy, final double zz, final ETupleType theType) throws IllegalArgumentException {

        if (theType == null) {
            final String msg = Res.get(Res.NULL_TUPLE_TYPE);
            throw new IllegalArgumentException(msg);
        }

        if (Double.isFinite(xx) && Double.isFinite(yy) && Double.isFinite(zz)) {
            this.x = xx;
            this.y = yy;
            this.z = zz;
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
    public Tuple3D(final Tuple3D src) {

        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
        this.type = src.type;
    }

    /**
     * Returns a {code Tuple3D} whose components are specified in cylindrical coordinates (r, theta, z).
     * <pre>
     * x = r cos(theta)
     * y = r sin(theta)
     * </pre>
     *
     * @param r        the radius
     * @param thetaDeg the theta coordinate (angle from the positive x-axis, in degrees)
     * @param z        the z coordinate
     * @param type     the type for the new tuple
     * @return the generated {code Vec3D}
     */
    public static Tuple3D forCylindrical(final double r, final double thetaDeg, final double z, final ETupleType type) {

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

        return new Tuple3D(x, y, z, type);
    }

    /**
     * Returns a {code Tuple3D} whose components are specified in spherical coordinates (r, theta, phi).
     * <pre>
     * x = r sin(phi) cos(theta)
     * y = r sin(phi) sin(theta)
     * z = r cos(phi)
     * </pre>
     *
     * @param r        the radius
     * @param thetaDeg the theta coordinate (angle from the positive x-axis, in degrees)
     * @param phiDeg   the phi coordinate (angle from the positive x-axis, in degrees)
     * @param type     the type for the new tuple
     * @return the generated {code Vec3D}
     */
    public static Tuple3D forSpherical(final double r, final double thetaDeg, final double phiDeg,
                                       final ETupleType type) {

        final double x;
        final double y;
        final double z;

        double rSinPhi;
        if (phiDeg == 0.0) {
            // Cos = 1, sin = 0
            z = r;
            rSinPhi = 0.0;
        } else if (phiDeg == 90.0) {
            // Cos = 0, sin = 1
            z = 0.0;
            rSinPhi = r;
        } else if (phiDeg == 180.0) {
            // Cos = -1, sin = 0
            z = -r;
            rSinPhi = 0.0;
        } else {
            final double phiRad = Math.toRadians(phiDeg);
            z = r * Math.cos(phiRad);
            rSinPhi = r * Math.sin(phiRad);
        }

        if (thetaDeg == 0.0) {
            x = rSinPhi;
            y = 0.0;
        } else if (thetaDeg == 90.0) {
            x = 0.0;
            y = rSinPhi;
        } else if (thetaDeg == 180.0) {
            x = -rSinPhi;
            y = 0.0;
        } else if (thetaDeg == 270.0) {
            x = 0.0;
            y = -rSinPhi;
        } else {
            final double thetaRad = Math.toRadians(thetaDeg);
            x = rSinPhi * Math.cos(thetaRad);
            y = rSinPhi * Math.sin(thetaRad);
        }

        return new Tuple3D(x, y, z, type);
    }

    /**
     * Parses a {code Tuple3D} from its string representation.  This can either be in Cartesian form:
     * <pre>
     *     Point: (x, y, z)
     *     Vector: [x, y, z]
     *     Normal vector: {x, y, z}
     * </pre>
     *
     * <p>
     * or in Cylindrical form (distinguished by the use of ';' rather than ',' to separate components)
     * <pre>
     *     Point: (R; ThetaDeg; Z)
     *     Vector: [R; ThetaDeg; Z]
     *     Normal vector: {R; ThetaDeg; Z}
     * </pre>
     *
     * <p>
     * or in Spherical form (distinguished by the use of ':' rather than ',' to separate components)
     * <pre>
     *     Point: (R: ThetaDeg: PhiDeg)
     *     Vector: [R: ThetaDeg: PhiDeg]
     *     Normal vector: {R: ThetaDeg: PhiDeg}
     * </pre>
     *
     * @param str the string to parse
     * @return the parsed tuple
     * @throws NumberFormatException    if the x or y or z coordinate is not in a valid format
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static Tuple3D parse(final String str) throws IllegalArgumentException, NumberFormatException {

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

        final int separator;
        final int index2;

        int index1 = trimmed.indexOf(GeomConstants.COMMA);
        if (index1 == -1) {
            index1 = trimmed.indexOf(GeomConstants.SEMICOLON);
            if (index1 == -1) {
                index1 = trimmed.indexOf(GeomConstants.COLON);
                if (index1 == -1) {
                    throw makeBadTupleStringException(str);
                }
                index2 = trimmed.indexOf(GeomConstants.COLON, index1 + 1);
                if (index2 == -1) {
                    throw makeBadTupleStringException(str);
                }
                separator = GeomConstants.COLON;

            } else {
                index2 = trimmed.indexOf(GeomConstants.SEMICOLON, index1 + 1);
                if (index2 == -1) {
                    throw makeBadTupleStringException(str);
                }
                separator = GeomConstants.SEMICOLON;
            }
        } else {
            index2 = trimmed.indexOf(GeomConstants.COMMA, index1 + 1);
            if (index2 == -1) {
                throw makeBadTupleStringException(str);
            }
            separator = GeomConstants.COMMA;
        }

        final String xStr = trimmed.substring(1, index1);
        final String xTrimmed = xStr.trim();
        final double x = Double.parseDouble(xTrimmed);

        final String yStr = trimmed.substring(index1 + 1, index2);
        final String yTrimmed = yStr.trim();
        final double y = Double.parseDouble(yTrimmed);

        final String zStr = trimmed.substring(index2 + 1, len - 1);
        final String zTrimmed = zStr.trim();
        final double z = Double.parseDouble(zTrimmed);

        final Tuple3D result;
        if (separator == GeomConstants.COLON) {
            result = Tuple3D.forSpherical(x, y, z, type);
        } else if (separator == GeomConstants.SEMICOLON) {
            result = Tuple3D.forCylindrical(x, y, z, type);
        } else {
            result = new Tuple3D(x, y, z, type);
        }

        return result;
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
    public final Tuple3D as(final ETupleType newType) {

        return this.type == newType ? this : new Tuple3D(this.x, this.y, this.z, newType);
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
     * Gets the theta coordinate (the azimuthal angle).
     *
     * @return the theta coordinate
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
     * Gets the phi coordinate (the polar angle).
     *
     * @return the phi coordinate
     */
    public final double getPhiDeg() {

        final double result;

        if (this.x == 0.0 && this.y == 0) {
            result = this.z >= 0.0 ? 0.0 : 180.0;
        } else {
            final double len = length();
            final double radians = Math.acos(this.z / len);
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

        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Calculates the length of the vector.
     *
     * @return the length
     */
    public final double length() {

        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Computes the square of the distance between two tuples.
     *
     * @param p1 the first tuple
     * @param p2 the second tuple
     * @return the square of the distance
     */
    public final double distanceSquared(final Tuple3D p1, final Tuple3D p2) {

        final double dx = p1.x - p2.x;
        final double dy = p1.y - p2.y;
        final double dz = p1.z - p2.z;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Computes the distance between two tuples.
     *
     * @param tup1 the first tuple
     * @param tup2 the second tuple
     * @return the distance
     */
    public final double distance(final Tuple3D tup1, final Tuple3D tup2) {

        final double distSq = distanceSquared(tup1, tup2);
        return Math.sqrt(distSq);
    }

    /**
     * Returns the minimum coordinate of this tuple.
     *
     * @return the minimum coordinate
     */
    public final double minComponent() {

        return Math.min(Math.min(this.x, this.y), this.z);
    }

    /**
     * Returns the maximum coordinate of this tuple.
     *
     * @return the maximum coordinate
     */
    public final double maxComponent() {

        return Math.max(Math.max(this.x, this.y), this.z);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple added to those of another tuple.
     *
     * @param toAdd      the tuple to add
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public final Tuple3D sum(final Tuple3D toAdd, final ETupleType resultType) {

        return new Tuple3D(this.x + toAdd.x, this.y + toAdd.y, this.z + toAdd.z, resultType);
    }

    /**
     * Computes the result of adding two tuples, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the sum
     */
    public static Tuple3D sum(final Tuple3D tup1, final Tuple3D tup2, final ETupleType resultType) {

        return new Tuple3D(tup1.x + tup2.x, tup2.y + tup2.y, tup2.z + tup2.z, resultType);
    }

    /**
     * Creates a new tuple whose coordinates are those of this tuple minus those of another tuple.
     *
     * @param toSubtract the tuple to subtract
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public final Tuple3D difference(final Tuple3D toSubtract, final ETupleType resultType) {

        return new Tuple3D(this.x - toSubtract.x, this.y - toSubtract.y, this.z - toSubtract.z, resultType);
    }

    /**
     * Computes the result of subtracting one tuple from another, as a new tuple.
     *
     * @param tup1       the first tuple
     * @param tup2       the second tuple
     * @param resultType the type for the resulting tuple
     * @return the difference
     */
    public static Tuple3D difference(final Tuple3D tup1, final Tuple3D tup2, final ETupleType resultType) {

        return new Tuple3D(tup1.x - tup2.x, tup1.y - tup2.y, tup1.z - tup2.z, resultType);
    }

    /**
     * Computes the midpoint between two tuples.  The result has the same type as the first tuple provided
     * ({@code tup1}).
     *
     * @param tup1 the first tuple
     * @param tup2 the second tuple
     * @return the midpoint tuple
     */
    public static Tuple3D midpoint(final Tuple3D tup1, final Tuple3D tup2) {

        return new Tuple3D((tup1.x + tup2.x) * 0.5, (tup1.y + tup2.y) * 0.5, (tup1.z + tup2.z) * 0.5, tup1.type);
    }

    /**
     * Creates the negation of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @return the negated tuple
     */
    public final Tuple3D negated() {

        return new Tuple3D(-this.x, -this.y, -this.z, this.type);
    }

    /**
     * Creates the negation of a tuple as a new tuple.  The source tuple's type is retained.
     *
     * @param src the tuple whose coordinates to negate
     * @return the negated tuple
     */
    public static Tuple3D negated(final Tuple3D src) {

        return new Tuple3D(-src.x, -src.y, -src.z, src.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param factor the factor by which to scale the tuple's coordinates
     * @return the scaled tuple
     */
    public final Tuple3D scaled(final double factor) {

        return new Tuple3D(this.x * factor, this.y * factor, this.z * factor, this.type);
    }

    /**
     * Generates a scaled version of this tuple as a new tuple.  The tuple's type is retained.
     *
     * @param xFactor the factor by which to scale the tuple's x coordinate
     * @param yFactor the factor by which to scale the tuple's y coordinate
     * @param zFactor the factor by which to scale the tuple's z coordinate
     * @return the scaled tuple
     */
    public final Tuple3D scaled(final double xFactor, final double yFactor, final double zFactor) {

        return new Tuple3D(this.x * xFactor, this.y * yFactor, this.z * zFactor, this.type);
    }

    /**
     * Computes a point by linearly interpolating two given tuples.  The result has the type of the first tuple given
     * ({@code p1}).
     *
     * @param p1 the first tuple (at t = 0)
     * @param p2 the second tuple (at t = 1)
     * @param t  the parameter value at which to interpolate/extrapolate
     * @return the resulting point (1 - t)p1 + (t)p2
     */
    public static Tuple3D lerp(final Tuple3D p1, final Tuple3D p2, final double t) {

        final double oneMinusT = 1.0 - t;
        final double xx = oneMinusT * p1.x + t * p2.x;
        final double yy = oneMinusT * p1.y + t * p2.y;
        final double zz = oneMinusT * p1.z + t * p2.z;

        return new Tuple3D(xx, yy, zz, p1.type);
    }

    /**
     * Returns a new {@code Tuple3D} where the coordinates lie on the unit sphere.  The tuple's type is retained.
     *
     * @return the normalized tuple; a tuple with coordinates x = 1, y = z = 0 if the given tuple has length 0
     */
    public final Tuple3D normalized() {

        final double xx;
        final double yy;
        final double zz;

        final double len = length();
        if (len == 0.0) {
            xx = 1.0;
            yy = 0.0;
            zz = 0.0;
        } else {
            final double factor = 1.0 / len;
            xx = this.x * factor;
            yy = this.y * factor;
            zz = this.z * factor;
        }

        return new Tuple3D(xx, yy, zz, this.type);
    }

    /**
     * Returns a new tuple whose components are the absolute value of the components of this tuple.  The tuple's type is
     * * retained.
     *
     * @return the new tuple
     */
    public final Tuple3D abs() {

        final double absX = Math.abs(this.x);
        final double absY = Math.abs(this.y);
        final double absZ = Math.abs(this.z);
        return new Tuple3D(absX, absY, absZ, this.type);
    }

    /**
     * Computes the dot product of this tuple with another tuple.
     *
     * @param vec the other tuple
     * @return the dot product
     */
    public final double dot(final Tuple3D vec) {

        return vec.x * this.x + vec.y * this.y + vec.z * this.z;
    }

    /**
     * Computes the absolute value of the dot product of this tuple with another tuple.
     *
     * @param vec the other tuple
     * @return the absolute value of the dot product
     */
    public final double absdot(final Tuple3D vec) {

        final double dot = dot(vec);
        return Math.abs(dot);
    }

    /**
     * Computes the cross product of this tuple with another tuple.
     *
     * @param vec        the other tuple
     * @param resultType the type for the resulting tuple
     * @return the cross product (this cross vec)
     */
    public final Tuple3D cross(final Tuple3D vec, final ETupleType resultType) {

        return new Tuple3D(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z,
                this.x * vec.y - this.y * vec.x, resultType);
    }

    /**
     * Constructs a vector between two tuples.  The result has type VECTOR.
     *
     * @param start the start tuple
     * @param end   the end tuple
     * @return the vector
     */
    public static Tuple3D vectorBetween(final Tuple3D start, final Tuple3D end) {

        return new Tuple3D(end.x - start.x, end.y - start.y, end.z - start.z, ETupleType.VECTOR);
    }

    /**
     * Tests whether three tuples (considered as vectors) are linearly independent.
     *
     * @param v1 the first tuple
     * @param v2 the second tuple
     * @param v3 the third tuple
     * @return true if the vectors are linearly independent; false if linearly dependent
     */
    public static boolean linearlyIndependent(final Tuple3D v1, final Tuple3D v2, final Tuple3D v3) {

        // If determinant of matrix whose columns are the three vectors is nonzero, vectors are
        // linearly independent. Rather than test against zero (risky for IEEE floating point
        // arithmetic), we compare to a small epsilon.

        final double t1 = v1.x * (v2.y * v3.z - v3.y * v2.z);
        final double t2 = v1.y * (v2.x * v3.z - v3.x * v2.z);
        final double t3 = v1.z * (v2.x * v3.y - v3.x * v2.y);

        return t1 - t2 + t3 > EPSILON;
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public final int hashCode() {

        return Double.hashCode(this.x) + Double.hashCode(this.y) + Double.hashCode(this.z) + this.type.hashCode();
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
        } else if (obj instanceof final Tuple3D tuple) {
            equal = tuple.type == this.type && tuple.x == this.x && tuple.y == this.y && tuple.z == this.z;
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
        builder.addStrings(xStr, ", ", yStr, ", ", zStr);

        switch (this.type) {
            case POINT -> builder.addChar(')');
            case VECTOR -> builder.addChar(']');
            case NORMAL_VECTOR -> builder.addChar('}');
        }

        return builder.toString();
    }
}
