package dev.mathops.math.linalg;

import dev.mathops.math.geom.ETupleType;
import dev.mathops.math.geom.Tuple2D;
import dev.mathops.math.geom.Tuple2DArray;
import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A 2x2 square matrix whose entries are {code double} values.  This matrix can serve as a linear (as opposed to more
 * general affine) transformation for points and vectors in the plane.
 */
public class Matrix2x2 {

    /** The identity matrix. */
    public static final Matrix2x2 IDENTITY = new Matrix2x2(1.0, 0.0, 0.0, 1.0);

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

    /** The row 2, column 1 matrix entry. */
    public final double m21;

    /** The row 2, column 2 matrix entry. */
    public final double m22;

    /** The inverse matrix; {code null} until computed. */
    private Matrix2x2 inverse;

    /**
     * Creates a new 2x2 square matrix.
     *
     * @param e11 the m11 entry
     * @param e12 the m12 entry
     * @param e21 the m21 entry
     * @param e22 the m22 entry
     */
    public Matrix2x2(final double e11, final double e12, final double e21, final double e22) {

        this.m11 = e11;
        this.m12 = e12;
        this.m21 = e21;
        this.m22 = e22;
    }

    /**
     * Parses a 2x2 matrix from its string representation.
     *
     * @param str the string to parse
     * @return the parsed matrix
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NumberFormatException    if any matrix entry is not a valid number
     */
    public static Matrix2x2 parse(final String str) throws IllegalArgumentException, NumberFormatException {

        final String trim1 = str.trim();
        final int len1 = trim1.length();

        if ('[' != (int) trim1.charAt(0) || ']' != (int) trim1.charAt(len1 - 1)) {
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
        if (rows.length != 2) {
            throw new IllegalArgumentException("Invalid matrix string: " + rows.length + " rows when 2 were expected");
        }

        rows[0] = rows[0].trim();

        rows[1] = rows[1].trim();
        if (rows[1].isEmpty() || (int) rows[1].charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid matrix string: missing '[..]' row delimiters");
        }
        rows[1] = rows[1].substring(1);

        final String[] cells1 = rows[0].split(",");
        if (cells1.length != 2) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 1");
        }

        final String[] cells2 = rows[1].split(",");
        if (cells2.length != 2) {
            throw new IllegalArgumentException("Invalid matrix string: bad number of cells in row 2");
        }

        final String m11Trimmed = cells1[0].trim();
        final double m11 = Double.parseDouble(m11Trimmed);

        final String m12Trimmed = cells1[1].trim();
        final double m12 = Double.parseDouble(m12Trimmed);

        final String m21Trimmed = cells2[0].trim();
        final double m21 = Double.parseDouble(m21Trimmed);

        final String m22Trimmed = cells2[1].trim();
        final double m22 = Double.parseDouble(m22Trimmed);

        return new Matrix2x2(m11, m12, m21, m22);
    }

    /**
     * Creates a matrix that scales vectors and points by factors in the x and y directions.
     *
     * @param sx the scaling factor in the x direction
     * @param sy the scaling factor in the y direction
     * @return the matrix
     */
    public static Matrix2x2 makeScale(final double sx, final double sy) {

        return new Matrix2x2(sx, 0.0, 0.0, sy);
    }

    /**
     * Creates a matrix that rotates vectors and points by some angle (where positive angles rotate the x-axis toward
     * the y-axis).
     *
     * @param angle the angle (in radians)
     * @return the matrix
     */
    public static Matrix2x2 makeRotate(final double angle) {

        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        return new Matrix2x2(cos, -sin, sin, cos);
    }

    /**
     * Transforms a source tuple into a destination tuple.
     *
     * @param src the source tuple to be transformed
     * @return the transformed tuple
     * @throws ArithmeticException if the tuple to transform is a normal vector, and this matrix is not invertible
     */
    public final Tuple2D transform(final Tuple2D src) throws ArithmeticException {

        final double newX;
        final double newY;

        switch (src.type) {
            case NORMAL_VECTOR:
                // Normal vector is transformed by transpose of inverse (note m21/m12 swapped below)
                final Matrix2x2 inv = inverse();
                newX = src.x * inv.m11 + src.y * inv.m21;
                newY = src.x * inv.m12 + src.y * inv.m22;
                break;

            case POINT:
            case VECTOR:
            default:
                newX = src.x * this.m11 + src.y * this.m12;
                newY = src.x * this.m21 + src.y * this.m22;
                break;
        }

        return new Tuple2D(newX, newY, src.type);
    }

    /**
     * Transforms all tuples in a source tuple array into corresponding indexes in a destination array.  There is no
     * difference between point and vector transformations.
     *
     * @param src  the source array of tuples to be transformed
     * @param dest the destination array to which to set to the transformed tuples (all existing data in the destination
     *             array is cleared first)
     */
    public final void transformArray(final Tuple2DArray src, final Tuple2DArray dest) {

        final int sz = src.size();
        dest.clear(sz);

        Matrix2x2 inv = null;

        for (int i = 0; i < sz; ++i) {
            final double srcX = src.getX(i);
            final double srcY = src.getY(i);
            final ETupleType type = src.getType(i);

            final double newX;
            final double newY;

            switch (type) {
                case NORMAL_VECTOR:
                    // Normal vector is transformed by transpose of inverse (note m21/m12 swapped below)
                    if (inv == null) {
                        inv = inverse();
                    }
                    newX = srcX * inv.m11 + srcY * inv.m21;
                    newY = srcX * inv.m12 + srcY * inv.m22;
                    break;

                case POINT:
                case VECTOR:
                default:
                    newX = srcX * this.m11 + srcY * this.m12;
                    newY = srcX * this.m21 + srcY * this.m22;
                    break;
            }

            dest.add(newX, newY, type);
        }
    }

    /**
     * Multiplies two matrices and returns the product.
     *
     * @param left  the left matrix
     * @param right the right matrix
     * @return the product
     */
    public static Matrix2x2 product(final Matrix2x2 left, final Matrix2x2 right) {

        final double m11 = left.m11 * right.m11 + left.m12 * right.m21;
        final double m12 = left.m11 * right.m12 + left.m12 * right.m22;

        final double m21 = left.m21 * right.m11 + left.m22 * right.m21;
        final double m22 = left.m21 * right.m12 + left.m22 * right.m22;

        return new Matrix2x2(m11, m12, m21, m22);
    }

    /**
     * Returns the transpose matrix for this matrix.
     *
     * @return the transpose matrix
     */
    public final Matrix2x2 transpose() {

        return new Matrix2x2(//
                this.m11, this.m21, //
                this.m12, this.m22);
    }

    /**
     * Computes the determinant.
     *
     * @return the determinant
     */
    public final double determinant() {

        return this.m11 * this.m22 - this.m21 * this.m12;
    }

    /**
     * Returns the inverse matrix for this matrix, if one exists.
     *
     * @return the inverse matrix
     * @throws ArithmeticException if the inverse could not be computed
     */
    public final Matrix2x2 inverse() throws ArithmeticException {

        if (this.inverse == null) {
            final double det = determinant();
            if (det == 0.0) {
                throw new ArithmeticException("Singular matrix");
            }
            final double factor = 1.0 / det;

            final double e11 = this.m22 * factor;
            final double e12 = -this.m12 * factor;
            final double e21 = -this.m21 * factor;
            final double e22 = this.m11 * factor;

            this.inverse = new Matrix2x2( //
                    e11, e12, //
                    e21, e22);
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

        return Double.hashCode(this.m11 + this.m12 + this.m21 + this.m22);
    }

    /**
     * Tests whether this object is equal to another. Equality requires {@code o} be a {@code SquareMatrix2D} with the
     * same value for each entry.
     *
     * @param obj the object against which to test
     * @return true if this object is equal to {@code obj}.
     */
    @Override
    public final boolean equals(final Object obj) {

        final boolean equal;

        if (obj == this) {
            equal = true;
        } else if (obj instanceof Matrix2x2 mat) {
            equal = this.m11 == mat.m11 //
                    && this.m12 == mat.m12 //
                    && this.m21 == mat.m21 //
                    && this.m22 == mat.m22;
        } else {
            equal = false;
        }

        return equal;
    }

    /**
     * Generates the string representation of the matrix.
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
        htm.addlnString(R);
        htm.addString(L);
        htm.addDouble(this.m21);
        htm.addString(COMMA);
        htm.addDouble(this.m22);
        htm.addlnStrings(R, R);

        return htm.toString();
    }
}
