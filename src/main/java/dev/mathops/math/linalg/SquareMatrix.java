package dev.mathops.math.linalg;

import dev.mathops.text.builder.HtmlBuilder;
import org.w3c.dom.html.HTMLDListElement;

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
            throw new IllegalArgumentException("Matrix entries must be finite.");
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
            throw new IllegalArgumentException("Matrix entries must be finite.");
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
            result = this.m[0][0] * this.m[1][1] * this.m[2][2]
                     + this.m[0][1] * this.m[1][2] * this.m[2][0]
                     + this.m[0][2] * this.m[1][0] * this.m[2][1]
                     - this.m[0][2] * this.m[1][1] * this.m[2][0]
                     - this.m[0][1] * this.m[1][0] * this.m[2][2]
                     - this.m[0][0] * this.m[1][2] * this.m[2][1];
        } else {
            // FIXME
            throw new UnsupportedOperationException("Determinant of matrices larger than 3x3 not yet implemented");
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

        // FIXME
        throw new UnsupportedOperationException("Matrix inverse not yet implemented");
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

        final int mN = n();

        final HtmlBuilder builder = new HtmlBuilder(20 * mN * mN);

        builder.add('[');
        for (int r = 0; r < mN; ++r) {
            builder.add('[');
            for (int c = 0; c < mN; ++c) {
                if (c > 0) {
                    builder.add(", ");
                }
                builder.add(Double.toString(this.m[r][c]));
            }
            builder.addln(']');
        }
        builder.addln(']');

        return builder.toString();
    }
}
