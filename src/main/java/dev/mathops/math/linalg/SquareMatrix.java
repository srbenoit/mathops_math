package dev.mathops.math.linalg;

import dev.mathops.commons.log.Log;
import dev.mathops.text.builder.HtmlBuilder;

import java.util.Arrays;
import java.util.Optional;

/**
 * A square matrix.
 */
public class SquareMatrix {

    /** The matrix entries, indexed by [row][col]. */
    private final double[][] m;

    /**
     * Constructs a new {@code SquareMatrix} whose entries are all zero.
     *
     * @param n the number of rows and columns
     * @throws IllegalArgumentException if {@code n} is less than 1
     */
    public SquareMatrix(final int n) {

        if (n < 1) {
            throw new IllegalArgumentException("Matrix size must be 1 or greater.");
        }

        this.m = new double[n][n];
    }

    /**
     * Constructs a copy of an existing {@code SquareMatrix}.
     *
     * @param source the source matrix to copy
     * @throws IllegalArgumentException if {@code source} is null
     */
    public SquareMatrix(final SquareMatrix source) {

        if (source == null) {
            throw new IllegalArgumentException("Source matrix may not be null");
        }

        final int n = source.n();
        this.m = new double[n][n];

        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                this.m[r][c] = source.get(r, c);
            }
        }
    }

    /**
     * Constructs a square matrix with a specified list of values.
     *
     * @param values the array of values (must be a perfect square), in row-major order (row 0 from left to right, then
     *               row 1 from left to right, etc.)
     * @throws IllegalArgumentException if {@code values} is null or has a length that is not a perfect square of a
     *                                  positive integer, or if any value is not a finite number
     */
    public SquareMatrix(final double[][] values) {

        if (values == null) {
            throw new IllegalArgumentException("Matrix elements array may not be null");
        }
        final int n = values.length;
        for (final double[] value : values) {
            if (value == null || value.length != n) {
                throw new IllegalArgumentException("Matrix elements array had inconsistent rows");
            }
        }

        this.m = new double[n][];
        for (int i = 0; i < n; ++i) {
            this.m[i] = values[i].clone();
        }
    }

    /**
     * Constructs a square matrix with a specified list of values.
     *
     * @param values the array of values (must be a perfect square), in row-major order (row 0 from left to right, then
     *               row 1 from left to right, etc.)
     * @throws IllegalArgumentException if {@code values} is null or has a length that is not a perfect square of a
     *                                  positive integer, or if any value is not a finite number
     */
    public SquareMatrix(final double... values) {

        if (values == null) {
            throw new IllegalArgumentException("Matrix elements array may not be null");
        }

        final int numValues = values.length;
        final double doubleRoot = Math.sqrt((double) numValues);
        final int n = (int) Math.round(doubleRoot);
        final int nSquared = n * n;

        if (nSquared != numValues) {
            throw new IllegalArgumentException("Number of elements must be a perfect square.");
        }

        this.m = new double[n][n];
        int r = 0;
        int c = 0;
        for (final double value : values) {
            if (Double.isFinite(value)) {
                m[r][c] = value;
                ++c;
                if (c == n) {
                    c = 0;
                    ++r;
                }
            } else {
                throw new IllegalArgumentException("Matrix entries must be finite.");
            }
        }
    }

    /**
     * Constructs a new {@code SquareMatrix} with entries on the main diagonal of 1.0 and entries of 0.0 elsewhere.
     *
     * @param n the number of rows and columns
     * @return the constructed matrix
     */
    public static SquareMatrix identity(final int n) {

        final SquareMatrix result = new SquareMatrix(n);

        for (int i = 0; i < n; ++i) {
            result.m[i][i] = 1.0;
        }

        return result;
    }

    /**
     * Constructs a new {@code SquareMatrix} with given entries on the main diagonal and entries of 0.0 elsewhere.
     *
     * @param values the array of values for the main diagonal
     * @return the constructed matrix
     * @throws IllegalArgumentException if {@code values} is null or has length 0, or if any value is not a finite
     *                                  number
     */
    public static SquareMatrix diag(final double... values) {

        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Matrix elements array may not be null or empty");
        }

        final int n = values.length;

        final SquareMatrix result = new SquareMatrix(n);

        for (int i = 0; i < n; ++i) {
            final double value = values[i];
            if (Double.isFinite(value)) {
                result.m[i][i] = value;
            } else {
                throw new IllegalArgumentException("Matrix entries must be finite.");
            }
        }

        return result;
    }

    /**
     * Gets the number of rows and columns in the matrix.
     *
     * @return the number of rows / number of columns
     */
    public final int n() {

        return this.m.length;
    }

    /**
     * Gets the matrix entry at a particular row and column.
     *
     * @param r the zero-based row index
     * @param c the zero-based column index
     * @return the matrix entry
     */
    public final double get(final int r, final int c) {

        return this.m[r][c];
    }

    /**
     * Sets the matrix entry at a particular row and column.
     *
     * @param r     the zero-based row index
     * @param c     the zero-based column index
     * @param value the new matrix entry value
     * @throws IllegalArgumentException if {@code value} is not finite
     */
    public final void set(final int r, final int c, final double value) {

        if (Double.isFinite(value)) {
            this.m[r][c] = value;
        } else {
            throw new IllegalArgumentException("Matrix entries must be finite.");
        }
    }

    /**
     * Returns the sum of this matrix and another.
     *
     * @param other the matrix to add to this matrix (must be the same size as this matrix)
     * @return the constructed sum
     * @throws IllegalArgumentException if {@code other} is not the same size as this matrix
     */
    public final SquareMatrix sum(final SquareMatrix other) {

        final int mN = n();
        final int oN = other.n();

        if (mN == oN) {
            final SquareMatrix result = new SquareMatrix(this);
            result.add(other);

            return result;
        } else {
            throw new IllegalArgumentException("Matrices being added must be the same size.");
        }
    }

    /**
     * Adds another matrix's entries to this matrix's entries.
     *
     * @param other the matrix to add to this matrix(must be the same size as this matrix)
     * @throws IllegalArgumentException if {@code other} is not the same size as this matrix
     */
    public final void add(final SquareMatrix other) {

        final int mN = n();
        final int oN = other.n();

        if (mN == oN) {
            for (int r = 0; r < mN; ++r) {
                for (int c = 0; c < mN; ++c) {
                    this.m[r][c] += other.get(r, c);
                }
            }
        } else {
            throw new IllegalArgumentException("Matrices being added must be the same size.");
        }
    }

    /**
     * Returns the product of this matrix (on the left)  and another (on the right).
     *
     * @param other the matrix to add to this matrix (must be the same size as this matrix)
     * @return the constructed sum
     * @throws IllegalArgumentException if {@code other} is not the same size as this matrix
     */
    public final SquareMatrix product(final SquareMatrix other) {

        final int mN = n();
        final int oN = other.n();

        if (mN == oN) {
            final SquareMatrix result = new SquareMatrix(mN);

            for (int r = 0; r < mN; ++r) {
                for (int c = 0; c < mN; ++c) {
                    double accum = 0.0;
                    for (int i = 0; i < mN; ++i) {
                        accum += this.m[r][i] * other.get(i, c);
                    }
                    result.set(r, c, accum);
                }
            }

            return result;
        } else {
            throw new IllegalArgumentException("Matrices being multiplied must be the same size.");
        }
    }

    /**
     * Returns a new matrix computed by scaling this matrix by a scalar factor.
     *
     * @param scalar the scalar to multiply by this matrix to construct the new matrix
     * @return the constructed scaled matrix
     * @throws IllegalArgumentException if {@code scalar} is not finite
     */
    public final SquareMatrix scalarProduct(final double scalar) {

        final SquareMatrix result = new SquareMatrix(this);
        result.scale(scalar);

        return result;
    }

    /**
     * Scales this matrix by a scalar factor;
     *
     * @param scalar the scalar to multiply by this matrix to construct the new matrix
     * @throws IllegalArgumentException if {@code scalar} is not finite
     */
    public final void scale(final double scalar) {

        if (Double.isFinite(scalar)) {
            final int mN = n();
            for (int r = 0; r < mN; ++r) {
                for (int c = 0; c < mN; ++c) {
                    this.m[r][c] *= scalar;
                }
            }
        } else {
            throw new IllegalArgumentException("Scalar must be finite.");
        }
    }

    /**
     * Tests whether this matrix is the identity matrix.
     *
     * @param epsilon the maximum amount by which wny martix element can very from the exact value (1.0 or 0.0) and
     *                still be considered an identity matrix
     * @return true if this object is the identity matrix, within the specified epsilon
     */
    public final boolean isIdentity(final double epsilon) {

        boolean result = true;

        final int mN = n();
        for (int r = 0; r < mN; ++r) {
            for (int c = 0; c < mN; ++c) {
                final double expect = r == c ? 1.0 : 0.0;
                final double diff = this.m[r][c] - expect;
                if (Math.abs(diff) > epsilon) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Computes the determinant of the matrix.
     *
     * @return the determinant
     */
    public final double determinant() {

        final int mN = n();

        double result;

        if (mN == 1) {
            result = this.m[0][0];
        } else if (mN == 2) {
            result = this.m[0][0] * this.m[1][1] - this.m[0][1] * this.m[1][0];
        } else if (mN == 3) {
            result = determinant3x3();
        } else {
            result = generalDeterminant(mN);
        }

        return result;
    }

    /**
     * Computes the determinant of the matrix when it is known to be 3x3.
     *
     * @return the determinant
     */
    private double determinant3x3() {
        return this.m[0][0] * this.m[1][1] * this.m[2][2]
               + this.m[0][1] * this.m[1][2] * this.m[2][0]
               + this.m[0][2] * this.m[1][0] * this.m[2][1]
               - this.m[0][2] * this.m[1][1] * this.m[2][0]
               - this.m[0][1] * this.m[1][0] * this.m[2][2]
               - this.m[0][0] * this.m[1][2] * this.m[2][1];
    }

    /**
     * Computes the determinant of the matrix when it is known to be 4x4 or larger.
     *
     * <p>
     * This method performs Gaussian elimination with partial pivots, keeping track of sign changes, then take the
     * product of entries on the diagonal.
     *
     * @return the determinant
     */
    private double generalDeterminant(final int mN) {

        // Make a copy of the matrix to work on
        final double[][] temp = new double[mN][];
        for (int i = 0; i < mN; ++i) {
            temp[i] = this.m[i].clone();
        }
        // Log.fine(toString(temp));

        boolean negate = false;
        for (int col = 0; col < mN - 1; ++col) {
            // Find the row with the greatest element magnitude in the current column, starting from the row
            // corresponding to the current column
            int maxRow = col;
            double max = Math.abs(temp[col][col]);
            for (int i = col + 1; i < mN; ++i) {
                final double v = Math.abs(temp[i][col]);
                if (v > max) {
                    max = v;
                    maxRow = i;
                }
            }

            // Swap rows if needed ("pivot")
            if (maxRow != col) {
                final double[] x = temp[maxRow];
                temp[maxRow] = temp[col];
                temp[col] = x;
                negate = !negate;
                // Log.fine(toString(temp));
            }

            // Zero out entries in the column below the main diagonal
            for (int rr = col + 1; rr < mN; ++rr) {
                if (temp[rr][col] != 0.0) {
                    final double factor = -temp[rr][col] / temp[col][col];
                    temp[rr][col] = 0.0;
                    for (int cc = col + 1; cc < mN; ++cc) {
                        temp[rr][cc] = Math.fma(factor, temp[col][cc], temp[rr][cc]);
                    }
                }
            }
            // Log.fine(toString(temp));
        }

        double result = temp[0][0];
        for (int i = 1; i < mN; ++i) {
            result *= temp[i][i];
        }
        if (negate) {
            result = -result;
        }

        return result;
    }

    /**
     * Computes the transpose of the matrix.
     *
     * @return the transpose
     */
    public final SquareMatrix transpose() {

        final int mN = n();
        final SquareMatrix result = new SquareMatrix(mN);

        for (int r = 0; r < mN; ++r) {
            for (int c = 0; c < mN; ++c) {
                result.set(r, c, this.m[c][r]);
            }
        }

        return result;
    }

    /**
     * Computes the inverse of the matrix.
     *
     * @return the inverse, if one exists
     */
    public final Optional<SquareMatrix> inverse() {

        Optional<SquareMatrix> result;

        final int mN = n();

        if (mN == 1) {
            if (this.m[0][0] == 0.0) {
                result = Optional.empty();
            } else {
                final double value = 1.0 / this.m[0][0];
                result = Optional.of(new SquareMatrix(value));
            }
        } else if (mN == 2) {
            final double edt = this.m[0][0] * this.m[1][1] - this.m[1][0] * this.m[0][1];

            if (edt == 0.0) {
                result = Optional.empty();
            } else {
                final double factor = 1.0 / edt;
                final SquareMatrix inv = new SquareMatrix(this.m[1][1] * factor, -this.m[0][1] * factor,
                        -this.m[1][0] * factor, this.m[0][0] * factor);
                result = Optional.of(inv);
            }
        } else if (mN == 3) {
            result = inverse3x3();
        } else {
            result = generalInverse(mN);
        }

        return result;
    }

    /**
     * Computes the inverse of the matrix when it is known to be 3x3.
     *
     * @return the inverse, if one exists
     */
    private Optional<SquareMatrix> inverse3x3() {

        final double a = this.m[0][0];
        final double b = this.m[0][1];
        final double c = this.m[0][2];
        final double d = this.m[1][0];
        final double e = this.m[1][1];
        final double f = this.m[1][2];
        final double g = this.m[2][0];
        final double h = this.m[2][1];
        final double i = this.m[2][2];

        Optional<SquareMatrix> result;

        final double det = a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
        if (det == 0.0) {
            result = Optional.empty();
        } else {
            final double factor = 1.0 / det;
            final SquareMatrix inv = new SquareMatrix(
                    (e * i - f * h) * factor, (c * h - b * i) * factor, (b * f - c * e) * factor,
                    (f * g - d * i) * factor, (a * i - c * g) * factor, (c * d - a * f) * factor,
                    (d * h - e * g) * factor, (b * g - a * h) * factor, (a * e - b * d) * factor);
            result = Optional.of(inv);
        }

        return result;
    }

    /**
     * Computes the inverse of the matrix when it is known to be 4x4 or larger.
     *
     * <p>
     * This method performs full Gauss-Jordan elimination with partial pivots with an adjoined identity matrix.
     *
     * @return the inverse, if one exists
     */
    private Optional<SquareMatrix> generalInverse(final int mN) {

        // Make a copy of the matrix to work on, and an identity matrix to start from
        final double[][] temp = new double[mN][];
        final double[][] inv = new double[mN][mN];
        for (int i = 0; i < mN; ++i) {
            temp[i] = this.m[i].clone();
            inv[i][i] = 1.0;
        }
//        Log.fine("Start");
//        Log.fine(toString(temp), toString(inv));

        // We do Gaussian elimination first, getting the matrix to upper-triangular form, so we can calculate the
        // determinant and see if the matrix is singular.
        for (int col = 0; col < mN - 1; ++col) {
            // Find the row with the greatest element magnitude in the current column, starting from the row
            // corresponding to the current column
            int maxRow = col;
            double max = Math.abs(temp[col][col]);
            for (int i = col + 1; i < mN; ++i) {
                final double v = Math.abs(temp[i][col]);
                if (v > max) {
                    max = v;
                    maxRow = i;
                }
            }

            // Swap rows if needed ("pivot")
            if (maxRow != col) {
                final double[] x = temp[maxRow];
                temp[maxRow] = temp[col];
                temp[col] = x;
                final double[] y = inv[maxRow];
                inv[maxRow] = inv[col];
                inv[col] = y;
//
//                Log.fine("Swap " + maxRow + ", " + col);
//                Log.fine(toString(temp), toString(inv));
            }

            // Zero out entries in the column below the main diagonal
            if (temp[col][col] == 0.0) {
                // Determinant will be zero
                break;
            }

            for (int rr = col + 1; rr < mN; ++rr) {
                if (temp[rr][col] != 0.0) {
                    final double factor = -temp[rr][col] / temp[col][col];
                    temp[rr][col] = 0.0;
                    for (int cc = col + 1; cc < mN; ++cc) {
                        temp[rr][cc] = Math.fma(factor, temp[col][cc], temp[rr][cc]);
                    }
                    for (int cc = 0; cc < mN; ++cc) {
                        final double top = inv[col][cc];
                        final double entry = inv[rr][cc];
                        inv[rr][cc] = Math.fma(factor, top, entry);
                    }
                }
            }
//            Log.fine("Zeroed out column " + col);
//            Log.fine(toString(temp), toString(inv));
        }

        double det = temp[0][0];
        for (int i = 1; i < mN; ++i) {
            det *= temp[i][i];
        }

        final Optional<SquareMatrix> result;

        if (det == 0.0) {
            result = Optional.empty();
        } else {
            // Matrix is not singular - continue to find its inverse (Note: this means none of the entries on the main
            // diagonal are zero, so we need not check for that condition

            for (int row = 0; row < mN; ++row) {

                // Scale the main diagonal entry to 1.0;
                final double factor = 1.0 / temp[row][row];
                temp[row][row] = 1.0;
                for (int cc = row + 1; cc < mN; ++cc) {
                    temp[row][cc] *= factor;
                }
                for (int cc = 0; cc < mN; ++cc) {
                    inv[row][cc] *= factor;
                }

//                Log.fine("Scaled leading entry to 1.0 in column " + row);
//                Log.fine(toString(temp), toString(inv));

                for (int rr = 0; rr < row; ++rr) {
                    final double factor2 = -temp[rr][row];
                    temp[rr][row] = 0.0;

                    // NOTE: No need to actually update the "temp" matrix at this point, but we do so for debugging
                    for (int cc = row + 1; cc < mN; ++cc) {
                        final double diag = temp[row][cc];
                        final double entry = temp[rr][cc];
                        temp[rr][cc] = Math.fma(factor2, diag, entry);
                    }
                    for (int cc = 0; cc < mN; ++cc) {
                        final double diag = inv[row][cc];
                        final double entry = inv[rr][cc];
                        inv[rr][cc] = Math.fma(factor2, diag, entry);
                    }
                }

//                Log.fine("Zeroed above column " + row);
//                Log.fine(toString(temp), toString(inv));
            }

            final SquareMatrix inverse = new SquareMatrix(inv);

            result = Optional.of(inverse);
        }

        return result;
    }

    /**
     * Generates a hash code for the object.
     *
     * @return the hash code
     */
    public int hashCode() {

        int hash = 0;

        final int n = this.m.length;
        for (int i = 0; i < n; ++i) {
            hash += Arrays.hashCode(this.m[i]);
        }

        return hash;
    }

    /**
     * Tests whether this object is equal to another.
     *
     * @param o the other object
     * @return true if the objects are equal
     */
    public boolean equals(final Object o) {

        boolean equal;

        if (o == this) {
            equal = true;
        } else if (o instanceof final SquareMatrix sq) {
            final int mN = n();
            final int oN = sq.n();
            if (mN == oN) {
                equal = true;
                for (int r = 0; r < mN; ++r) {
                    for (int c = 0; c < mN; ++c) {
                        if (this.m[r][c] != sq.get(r, c)) {
                            equal = false;
                            break;
                        }
                    }
                }
            } else {
                equal = false;
            }
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
    public String toString() {

        return toString(this.m);
    }

    /**
     * Generates the string representation of the matrix.
     *
     * @param entries the array of matrix entries
     * @return the string representation
     */
    public static String toString(final double[][] entries) {

        final int mN = entries.length;

        // compute column widths so we can align entries
        final int[] colWidths = new int[mN];
        for (int row = 0; row < mN; ++row) {
            for (int col = 0; col < mN; ++col) {
                final String s = Double.toString(entries[row][col]);
                final int len = s.length();
                if (len > colWidths[col]) {
                    colWidths[col] = len;
                }
            }
        }

        final HtmlBuilder builder = new HtmlBuilder(20 * mN * mN);

        builder.add('[');
        for (int row = 0; row < mN; ++row) {
            if (row > 0) {
                builder.addln().add(' ');
            }
            builder.add('[');
            for (int col = 0; col < mN; ++col) {
                if (col > 0) {
                    builder.add(", ");
                }
                final String s = Double.toString(entries[row][col]);
                final String padded = HtmlBuilder.pad(s, colWidths[col]);
                builder.add(padded);
            }
            builder.add(']');
        }
        builder.addln(']');

        return builder.toString();
    }
}
