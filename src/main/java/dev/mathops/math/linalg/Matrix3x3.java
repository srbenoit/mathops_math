package dev.mathops.math.linalg;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple3D;
import dev.mathops.math.geom.Tuple3DArray;
import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A 3x3 square matrix whose entries are {code double} values.  This matrix can serve as a linear (as opposed to more
 * general affine) transformation for points and vectors in 3-space.
 */
public class Matrix3x3 {

    /** The identity matrix. */
    public static final Matrix3x3 IDENTITY = new Matrix3x3(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);

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

    /** The row 2, column 1 matrix entry. */
    public final double m21;

    /** The row 2, column 2 matrix entry. */
    public final double m22;

    /** The row 2, column 3 matrix entry. */
    public final double m23;

    /** The row 3, column 1 matrix entry. */
    public final double m31;

    /** The row 3, column 2 matrix entry. */
    public final double m32;

    /** The row 3, column 3 matrix entry. */
    public final double m33;

    /** The inverse matrix; {code null} until computed. */
    private Matrix3x3 inverse;

    /**
     * Creates a new matrix.
     *
     * @param e11 the m11 entry
     * @param e12 the m12 entry
     * @param e13 the m13 entry
     * @param e21 the m21 entry
     * @param e22 the m22 entry
     * @param e23 the m23 entry
     * @param e31 the m31 entry
     * @param e32 the m32 entry
     * @param e33 the m33 entry
     */
    public Matrix3x3(final double e11, final double e12, final double e13, final double e21, final double e22,
                     final double e23, final double e31, final double e32, final double e33) {

        this.m11 = e11;
        this.m12 = e12;
        this.m13 = e13;
        this.m21 = e21;
        this.m22 = e22;
        this.m23 = e23;
        this.m31 = e31;
        this.m32 = e32;
        this.m33 = e33;
    }

    /**
     * Creates a new matrix as a copy of an existing matrix.
     *
     * @param src the source matrix to copy
     */
    public Matrix3x3(final Matrix3x3 src) {

        this.m11 = src.m11;
        this.m12 = src.m12;
        this.m13 = src.m13;
        this.m21 = src.m21;
        this.m22 = src.m22;
        this.m23 = src.m23;
        this.m31 = src.m31;
        this.m32 = src.m32;
        this.m33 = src.m33;
    }

    /**
     * Parses a 3x3 matrix from its string representation.
     *
     * @param str the string to parse
     * @return the parsed matrix
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any matrix entry is not a valid number
     */
    public static Matrix3x3 parse(final String str) throws IllegalArgumentException, NumberFormatException {

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
        if (rows.length != 3) {
            throw new IllegalArgumentException("Invalid matrix string: " + rows.length + " rows when 3 were expected");
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

        final String[] cells1 = rows[0].split(",");
        if (cells1.length != 3) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 1");
        }

        final String[] cells2 = rows[1].split(",");
        if (cells2.length != 3) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 2");
        }

        final String[] cells3 = rows[2].split(",");
        if (cells3.length != 3) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 3");
        }

        final String m11Trimmed = cells1[0].trim();
        final double m11 = Double.parseDouble(m11Trimmed);

        final String m12Trimmed = cells1[1].trim();
        final double m12 = Double.parseDouble(m12Trimmed);

        final String m13Trimmed = cells1[2].trim();
        final double m13 = Double.parseDouble(m13Trimmed);

        final String m21Trimmed = cells2[0].trim();
        final double m21 = Double.parseDouble(m21Trimmed);

        final String m22Trimmed = cells2[1].trim();
        final double m22 = Double.parseDouble(m22Trimmed);

        final String m23Trimmed = cells2[2].trim();
        final double m23 = Double.parseDouble(m23Trimmed);

        final String m31Trimmed = cells3[0].trim();
        final double m31 = Double.parseDouble(m31Trimmed);

        final String m32Trimmed = cells3[1].trim();
        final double m32 = Double.parseDouble(m32Trimmed);

        final String m33Trimmed = cells3[2].trim();
        final double m33 = Double.parseDouble(m33Trimmed);

        return new Matrix3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
    }

    /**
     * Creates a matrix that scales vectors and points by factors in the x and y directions.
     *
     * @param sx the scaling factor in the x direction
     * @param sy the scaling factor in the y direction
     * @param sz the scaling factor in the z direction
     * @return the matrix
     */
    public static Matrix3x3 makeScale(final double sx, final double sy, final double sz) {

        return new Matrix3x3(sx, 0.0, 0.0, 0.0, sy, 0.0, 0.0, 0.0, sz);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the x-axis.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix3x3 makeRotateAboutX(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix3x3(1.0, 0.0, 0.0, 0.0, cos, -sin, 0.0, sin, cos);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the y-axis.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix3x3 makeRotateAboutY(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix3x3(cos, 0.0, sin, 0.0, 1.0, 0.0, -sin, 0.0, cos);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle about the z-axis.  Positive angles rotate the
     * x-axis toward the y-axis.
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix3x3 makeRotateAboutZ(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix3x3(cos, -sin, 0.0, sin, cos, 0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Transforms a source tuple into a destination tuple.
     *
     * @param src the source tuple to be transformed
     * @return the transformed tuple
     * @throws ArithmeticException if the tuple to transform is a normal vector, and this matrix is not invertible
     */
    public final Tuple3D transform(final Tuple3D src) throws ArithmeticException {

        final double newX;
        final double newY;
        final double newZ;

        switch (src.type) {
            case NORMAL_VECTOR:
                // Normal vector is transformed by transpose of inverse (note swapped elements below)
                final Matrix3x3 inv = inverse();
                newX = src.x * inv.m11 + src.y * inv.m21 + src.z * inv.m31;
                newY = src.x * inv.m12 + src.y * inv.m22 + src.z * inv.m32;
                newZ = src.x * inv.m13 + src.y * inv.m23 + src.z * inv.m33;
                break;

            case POINT:
            case VECTOR:
            default:
                newX = src.x * this.m11 + src.y * this.m12 + src.z * this.m13;
                newY = src.x * this.m21 + src.y * this.m22 + src.z * this.m23;
                newZ = src.x * this.m31 + src.y * this.m32 + src.z * this.m33;
                break;
        }

        return new Tuple3D(newX, newY, newZ, src.type);
    }

    /**
     * Transforms all tuples in a source tuple array into corresponding indexes in a destination array.  There is no
     * difference between point and vector transformations.
     *
     * @param src  the source array of tuples to be transformed
     * @param dest the destination array to which to set to the transformed tuples (all existing data in the destination
     *             array is cleared first)
     */
    public final void transformArray(final Tuple3DArray src, final Tuple3DArray dest) {

        final int sz = src.size();
        dest.clear(sz);

        Matrix3x3 inv = null;

        for (int i = 0; i < sz; ++i) {
            final double srcX = src.getX(i);
            final double srcY = src.getY(i);
            final double srcZ = src.getZ(i);
            final ETupleType type = src.getType(i);

            final double newX;
            final double newY;
            final double newZ;

            switch (type) {
                case NORMAL_VECTOR:
                    // Normal vector is transformed by transpose of inverse (note swapped entries below)
                    if (inv == null) {
                        inv = inverse();
                    }
                    newX = srcX * inv.m11 + srcY * inv.m21 + srcZ * inv.m31;
                    newY = srcX * inv.m12 + srcY * inv.m22 + srcZ * inv.m32;
                    newZ = srcX * inv.m13 + srcY * inv.m23 + srcZ * inv.m33;
                    break;

                case POINT:
                case VECTOR:
                default:
                    newX = srcX * this.m11 + srcY * this.m12 + srcZ * this.m13;
                    newY = srcX * this.m21 + srcY * this.m22 + srcZ * this.m23;
                    newZ = srcX * this.m31 + srcY * this.m32 + srcZ * this.m33;
                    break;
            }

            dest.add(newX, newY, newZ, type);
        }
    }

    /**
     * Multiplies two matrices and returns the product.
     *
     * @param left  the left matrix
     * @param right the right matrix
     * @return the product
     */
    public static Matrix3x3 product(final Matrix3x3 left, final Matrix3x3 right) {

        final double m11 = left.m11 * right.m11 + left.m12 * right.m21 + left.m13 * right.m31;
        final double m12 = left.m11 * right.m12 + left.m12 * right.m22 + left.m13 * right.m32;
        final double m13 = left.m11 * right.m13 + left.m12 * right.m23 + left.m13 * right.m33;

        final double m21 = left.m21 * right.m11 + left.m22 * right.m21 + left.m23 * right.m31;
        final double m22 = left.m21 * right.m12 + left.m22 * right.m22 + left.m23 * right.m32;
        final double m23 = left.m21 * right.m13 + left.m22 * right.m23 + left.m23 * right.m33;

        final double m31 = left.m31 * right.m11 + left.m32 * right.m21 + left.m33 * right.m31;
        final double m32 = left.m31 * right.m12 + left.m32 * right.m22 + left.m33 * right.m32;
        final double m33 = left.m31 * right.m13 + left.m32 * right.m23 + left.m33 * right.m33;

        return new Matrix3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
    }

    /**
     * Returns the transpose matrix for this matrix.
     *
     * @return the transpose matrix
     */
    public final Matrix3x3 transpose() {

        return new Matrix3x3(//
                this.m11, this.m21, m31, //
                this.m12, this.m22, m32, //
                this.m13, this.m23, m33);
    }

    /**
     * Computes the determinant.
     *
     * @return the determinant
     */
    public final double determinant() {

        return this.m11 * (this.m22 * this.m33 - this.m32 * this.m23) //
                - this.m21 * (this.m12 * this.m33 - this.m32 * this.m13) //
                + this.m31 * (this.m12 * this.m23 - this.m22 * this.m13);
    }

    /**
     * Returns the inverse matrix for this matrix, if one exists.
     *
     * @return the inverse matrix
     * @throws ArithmeticException if the inverse could not be computed
     */
    public final Matrix3x3 inverse() throws ArithmeticException {

        if (this.inverse == null) {
            // Compute the left-hand column of the cofactor matrix
            final double c11 = this.m22 * this.m33 - this.m32 * this.m23;
            final double c21 = this.m32 * this.m13 - this.m12 * this.m33;
            final double c31 = this.m12 * this.m23 - this.m22 * this.m13;

            // From this, calculate the determinant
            final double det = this.m11 * c11 + this.m21 * c21 + this.m31 * c31;
            if (det == 0.0) {
                throw new ArithmeticException("Singular matrix");
            }

            // Finish the rest of the cofactor matrix

            final double c12 = this.m31 * this.m23 - this.m21 * this.m33;
            final double c22 = this.m11 * this.m33 - this.m31 * this.m13;
            final double c32 = this.m21 * this.m13 - this.m11 * this.m23;

            final double c13 = this.m21 * this.m32 - this.m31 * this.m22;
            final double c23 = this.m31 * this.m12 - this.m11 * this.m32;
            final double c33 = this.m11 * this.m22 - this.m21 * this.m12;

            // Inverse = (1/det) (transpose of cofactor matrix)
            final double d = 1.0 / det;

            this.inverse = new Matrix3x3( //
                    c11 * d, c21 * d, c31 * d, //
                    c12 * d, c22 * d, c32 * d, //
                    c13 * d, c23 * d, c33 * d);
        }

        return this.inverse;
    }

    /**
     * Generates a hash code for the vector.
     *
     * @return the hash code
     */
    @Override
    public final int hashCode() {

        return Double.hashCode(this.m11 + this.m12 + this.m13 //
                + this.m21 + this.m22 + this.m23 //
                + this.m31 + this.m32 + this.m33);
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
        } else if (obj instanceof Matrix3x3 mat) {
            equal = this.m11 == mat.m11 && this.m12 == mat.m12 && this.m13 == mat.m13 //
                    && this.m21 == mat.m21 && this.m22 == mat.m22 && this.m23 == mat.m23 //
                    && this.m31 == mat.m31 && this.m32 == mat.m32 && this.m33 == mat.m33;
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
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m21);
        htm.addString(COMMA);
        htm.addDouble(this.m22);
        htm.addString(COMMA);
        htm.addDouble(this.m23);
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m31);
        htm.addString(COMMA);
        htm.addDouble(this.m32);
        htm.addString(COMMA);
        htm.addDouble(this.m33);
        htm.addlnStrings(R, R);

        return htm.toString();
    }
}
