package dev.mathops.math.linalg;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A 4x4 matrix whose entries are {code double} values.  This matrix can serve as an general transformation for points
 * and vectors in 3-space.
 */
public class Matrix4x4 {

    /** The identity matrix. */
    public static final Matrix4x4 IDENTITY = new Matrix4x4(1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0);

    /** A commonly used string. */
    private static final String L = "[ ";

    /** A commonly used string. */
    private static final String R = " ]";

    /** A commonly used string. */
    private static final String COMMA = ", ";

    /** The row 1, column 1 matrix entry. */
    public final double m11;

    /** The row 1, column 2 matrix entry. */
    public final double m12;

    /** The row 1, column 3 matrix entry. */
    public final double m13;

    /** The row 1, column 4 matrix entry. */
    public final double m14;

    /** The row 2, column 1 matrix entry. */
    public final double m21;

    /** The row 2, column 2 matrix entry. */
    public final double m22;

    /** The row 2, column 3 matrix entry. */
    public final double m23;

    /** The row 2, column 4 matrix entry. */
    public final double m24;

    /** The row 3, column 1 matrix entry. */
    public final double m31;

    /** The row 3, column 2 matrix entry. */
    public final double m32;

    /** The row 3, column 3 matrix entry. */
    public final double m33;

    /** The row 3, column 4 matrix entry. */
    public final double m34;

    /** The row 4, column 1 matrix entry. */
    public final double m41;

    /** The row 4, column 2 matrix entry. */
    public final double m42;

    /** The row 4, column 3 matrix entry. */
    public final double m43;

    /** The row 4, column 4 matrix entry. */
    public final double m44;

    /** The inverse matrix; {code null} until computed. */
    private Matrix4x4 inverse;

    /**
     * Creates a new matrix.
     *
     * @param e11 the m11 entry
     * @param e12 the m12 entry
     * @param e13 the m13 entry
     * @param e14 the m14 entry
     * @param e21 the m21 entry
     * @param e22 the m22 entry
     * @param e23 the m23 entry
     * @param e24 the m24 entry
     * @param e31 the m31 entry
     * @param e32 the m32 entry
     * @param e33 the m33 entry
     * @param e34 the m34 entry
     * @param e41 the m41 entry
     * @param e42 the m42 entry
     * @param e43 the m43 entry
     * @param e44 the m44 entry
     */
    public Matrix4x4(final double e11, final double e12, final double e13, final double e14, final double e21,
                     final double e22, final double e23, final double e24, final double e31, final double e32,
                     final double e33, final double e34, final double e41, final double e42, final double e43,
                     final double e44) {

        this.m11 = e11;
        this.m12 = e12;
        this.m13 = e13;
        this.m14 = e14;
        this.m21 = e21;
        this.m22 = e22;
        this.m23 = e23;
        this.m24 = e24;
        this.m31 = e31;
        this.m32 = e32;
        this.m33 = e33;
        this.m34 = e34;
        this.m41 = e41;
        this.m42 = e42;
        this.m43 = e43;
        this.m44 = e44;
    }

    /**
     * Creates a new matrix as a copy of an existing matrix.
     *
     * @param src the source matrix to copy
     */
    public Matrix4x4(final Matrix4x4 src) {

        this.m11 = src.m11;
        this.m12 = src.m12;
        this.m13 = src.m13;
        this.m14 = src.m14;
        this.m21 = src.m21;
        this.m22 = src.m22;
        this.m23 = src.m23;
        this.m24 = src.m24;
        this.m31 = src.m31;
        this.m32 = src.m32;
        this.m33 = src.m33;
        this.m34 = src.m34;
        this.m41 = src.m41;
        this.m42 = src.m42;
        this.m43 = src.m43;
        this.m44 = src.m44;
    }

    /**
     * Parses a 4x4 matrix from its string representation.
     *
     * @param str the string to parse
     * @return the parsed matrix
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any matrix entry is not a valid number
     */
    public static Matrix4x4 parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trim1 = str.trim();
        final int len1 = trim1.length();

        if ((int) trim1.charAt(0) != '[' || (int) trim1.charAt(len1 - 1) != ']') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' matrix delimiters");
        }

        // Trim off outermost "[" and "]"
        final String trim2 = trim1.substring(1, len1 - 1).trim();
        final int len2 = trim2.length();

        if ((int) trim2.charAt(0) != '[' || (int) trim2.charAt(len2 - 1) != ']') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' row delimiters");
        }

        // Trim off "[" from first and "]" from last row
        final String trim3 = trim2.substring(1, len2 - 1);

        // Break into rows
        final String[] rows = trim3.split("]");
        if (rows.length != 4) {
            throw new IllegalArgumentException("Invalid matrix string: " + rows.length + " rows when 4 were expected");
        }

        rows[0] = rows[0].trim();

        rows[1] = rows[1].trim();
        if (rows[1].isEmpty() || (int) rows[1].charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' row delimiters");
        }
        rows[1] = rows[1].substring(1);

        rows[2] = rows[2].trim();
        if (rows[2].isEmpty() || (int) rows[2].charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' row delimiters");
        }
        rows[2] = rows[2].substring(1);

        rows[3] = rows[3].trim();
        if (rows[3].isEmpty() || (int) rows[3].charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' row delimiters");
        }
        rows[3] = rows[3].substring(1);

        final String[] cells1 = rows[0].split(",");
        if (cells1.length != 4) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 1");
        }

        final String[] cells2 = rows[1].split(",");
        if (cells2.length != 4) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 2");
        }

        final String[] cells3 = rows[2].split(",");
        if (cells3.length != 4) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 3");
        }

        final String[] cells4 = rows[3].split(",");
        if (cells4.length != 4) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 4");
        }

        final String m11Trimmed = cells1[0].trim();
        final double m11 = Double.parseDouble(m11Trimmed);

        final String m12Trimmed = cells1[1].trim();
        final double m12 = Double.parseDouble(m12Trimmed);

        final String m13Trimmed = cells1[2].trim();
        final double m13 = Double.parseDouble(m13Trimmed);

        final String m14Trimmed = cells1[3].trim();
        final double m14 = Double.parseDouble(m14Trimmed);

        final String m21Trimmed = cells2[0].trim();
        final double m21 = Double.parseDouble(m21Trimmed);

        final String m22Trimmed = cells2[1].trim();
        final double m22 = Double.parseDouble(m22Trimmed);

        final String m23Trimmed = cells2[2].trim();
        final double m23 = Double.parseDouble(m23Trimmed);

        final String m24Trimmed = cells2[3].trim();
        final double m24 = Double.parseDouble(m24Trimmed);

        final String m31Trimmed = cells3[0].trim();
        final double m31 = Double.parseDouble(m31Trimmed);

        final String m32Trimmed = cells3[1].trim();
        final double m32 = Double.parseDouble(m32Trimmed);

        final String m33Trimmed = cells3[2].trim();
        final double m33 = Double.parseDouble(m33Trimmed);

        final String m34Trimmed = cells3[3].trim();
        final double m34 = Double.parseDouble(m34Trimmed);

        final String m41Trimmed = cells4[0].trim();
        final double m41 = Double.parseDouble(m41Trimmed);

        final String m42Trimmed = cells4[1].trim();
        final double m42 = Double.parseDouble(m42Trimmed);

        final String m43Trimmed = cells4[2].trim();
        final double m43 = Double.parseDouble(m43Trimmed);

        final String m44Trimmed = cells4[3].trim();
        final double m44 = Double.parseDouble(m44Trimmed);

        return new Matrix4x4(m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44);
    }

    /**
     * Creates a matrix that scales vectors and points by factors in the x and y directions, and has no translation.
     *
     * @param sx the scaling factor in the x direction
     * @param sy the scaling factor in the y direction
     * @param sz the scaling factor in the z direction
     * @return the matrix
     */
    public static Matrix4x4 makeScale(final double sx, final double sy, final double sz) {

        return new Matrix4x4(sx, 0.0, 0.0, 0.0,
                0.0, sy, 0.0, 0.0,
                0.0, 0.0, sz, 0.0,
                0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the x-axis, and has no translation.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix4x4 makeRotateAboutX(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix4x4(1.0, 0.0, 0.0, 0.0,
                0.0, cos, -sin, 0.0,
                0.0, sin, cos, 0.0,
                0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the y-axis, and has no translation.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix4x4 makeRotateAboutY(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix4x4(cos, 0.0, sin, 0.0,
                0.0, 1.0, 0.0, 0.0,
                -sin, 0.0, cos, 0.0,
                0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the z-axis, and has no translation. Positive
     * angles rotate the x-axis toward the y-axis.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix4x4 makeRotateAboutZ(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix4x4(cos, -sin, 0.0, 0.0,
                sin, cos, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Generates a hash code for the vector.
     *
     * @return the hash code
     */
    @Override
    public final int hashCode() {

        return Double.hashCode(this.m11 + this.m12 + this.m13 + this.m14 //
                + this.m21 + this.m22 + this.m23 + this.m24 //
                + this.m31 + this.m32 + this.m33 + this.m34 //
                + this.m41 + this.m42 + this.m43 + this.m44);
    }

    /**
     * Tests whether this object is equal to another. Equality requires {@code o} be a {@code Matrix3x3} with the same
     * value for each coordinate.
     *
     * @param obj the object against which to test
     * @return true if this object is equal to {@code o}.
     */
    @Override
    public boolean equals(final Object obj) {

        final boolean equal;

        if (obj == this) {
            equal = true;
        } else if (obj instanceof Matrix4x4 mat) {
            equal = this.m11 == mat.m11 && this.m12 == mat.m12 && this.m13 == mat.m13 && this.m14 == mat.m14 //
                    && this.m21 == mat.m21 && this.m22 == mat.m22 && this.m23 == mat.m23 && this.m24 == mat.m24 //
                    && this.m31 == mat.m31 && this.m32 == mat.m32 && this.m33 == mat.m33 && this.m34 == mat.m34 //
                    && this.m41 == mat.m41 && this.m42 == mat.m42 && this.m43 == mat.m43 && this.m44 == mat.m44;
        } else {
            equal = false;
        }

        return equal;
    }

    /**
     * Generates the string representation of the vector.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final CharHtmlBuilder htm = new CharHtmlBuilder(100);

        htm.addStrings(L, L);
        htm.addDouble(this.m11);
        htm.addString(COMMA);
        htm.addDouble(this.m12);
        htm.addString(COMMA);
        htm.addDouble(this.m13);
        htm.addString(COMMA);
        htm.addDouble(this.m14);
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m21);
        htm.addString(COMMA);
        htm.addDouble(this.m22);
        htm.addString(COMMA);
        htm.addDouble(this.m23);
        htm.addString(COMMA);
        htm.addDouble(this.m24);
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m31);
        htm.addString(COMMA);
        htm.addDouble(this.m32);
        htm.addString(COMMA);
        htm.addDouble(this.m33);
        htm.addString(COMMA);
        htm.addDouble(this.m34);
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m41);
        htm.addString(COMMA);
        htm.addDouble(this.m42);
        htm.addString(COMMA);
        htm.addDouble(this.m43);
        htm.addString(COMMA);
        htm.addDouble(this.m44);
        htm.addlnStrings(R, R);

        return htm.toString();
    }
}
