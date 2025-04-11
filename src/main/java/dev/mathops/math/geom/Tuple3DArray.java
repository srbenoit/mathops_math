package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A base class for dynamically sized arrays of points, vectors, and normal vectors in the space with {@code double}
 * coordinates.
 */
public class Tuple3DArray {

    /** The largest allowed initial capacity. */
    private static final int MAX_INITIAL_CAP = 10000;

    /** The maximum array size (somewhat smaller than the largest signed integer). */
    private static final int MAX_ARRAY_SIZE = 2147480000;

    /** The maximum number of tuples to include in the string representation. */
    private static final int MAX_TUPLES_TO_PRINT = 100;

    /** The X coordinates of the tuples in this array. */
    private double[] x;

    /** The Y coordinates of the tuples in this array. */
    private double[] y;

    /** The Y coordinates of the tuples in this array. */
    private double[] z;

    /** The type of each tuple. */
    private ETupleType[] type;

    /** The number of tuples populated. */
    private int count;

    /**
     * Constructs a new {@code Tuple3DArray} with specified initial capacity.
     *
     * @param initialCapacity the initial capacity (clamped to [1, MAX_INITIAL_CAP])
     * @throws IllegalArgumentException if either coordinate is not finite
     */
    Tuple3DArray(final int initialCapacity) {

        final int clampedAbove = Math.min(MAX_INITIAL_CAP, initialCapacity);
        final int clamped = Math.max(clampedAbove, 1);

        this.x = new double[clamped];
        this.y = new double[clamped];
        this.z = new double[clamped];
        this.type = new ETupleType[clamped];
    }

    /**
     * Constructs a new {@code Tuple3DArray} with coordinate arrays copied from another {@code Tuple3DArray}.
     *
     * @param src the source whose coordinate arrays to copy
     */
    Tuple3DArray(final Tuple3DArray src) {

        this.x = src.x.clone();
        this.y = src.y.clone();
        this.z = src.z.clone();
        this.type = src.type.clone();
        this.count = src.count;
    }

    /**
     * Gets the number of tuples in the array.
     *
     * @return the number of tuples
     */
    public final int size() {

        return this.count;
    }

    /**
     * Clears the array.
     *
     * @param targetCapacity the number of tuples expected to be added after clearing the array
     */
    public final void clear(final int targetCapacity) {

        this.count = 0;

        if (this.x.length < targetCapacity) {
            final int clampedAbove = Math.min(MAX_INITIAL_CAP, targetCapacity);
            this.x = new double[clampedAbove];
            this.y = new double[clampedAbove];
            this.z = new double[clampedAbove];
            this.type = new ETupleType[clampedAbove];
        }
    }

    /**
     * Adds a new tuple to the end of this array.
     *
     * @param theX    the x coordinate
     * @param theY    the y coordinate
     * @param theZ    the z coordinate
     * @param theType the tuple's type
     * @throws IllegalStateException if the array size cannot be increased to accommodate the new tuple
     */
    public final void add(final double theX, final double theY, final double theZ, final ETupleType theType) throws IllegalStateException {

        if (theType == null) {
            final String msg = Res.get(Res.BAD_COORDINATES);
            throw new IllegalArgumentException(msg);
        }

        if (Double.isFinite(theX) && Double.isFinite(theY)) {
            if (this.count == this.x.length) {
                increaseSize();
            }
            this.x[this.count] = theX;
            this.y[this.count] = theY;
            this.z[this.count] = theZ;
            this.type[this.count] = theType;
            ++this.count;
        } else {
            final String msg = Res.get(Res.BAD_COORDINATES);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Increases the size of the array, if possible.
     *
     * @throws IllegalStateException if the array size cannot be increased to accommodate the new tuple
     */
    private void increaseSize() {

        if (this.count == MAX_ARRAY_SIZE) {
            throw new IllegalStateException("Maximum tuple array size exceeded!");
        }
        int newCap = Math.max(this.count + 2, this.count / 2 * 3);
        if (newCap > MAX_ARRAY_SIZE || newCap < this.count) {
            newCap = MAX_ARRAY_SIZE;
        }

        final double[] newX = new double[newCap];
        final double[] newY = new double[newCap];
        final double[] newZ = new double[newCap];

        System.arraycopy(this.x, 0, newX, 0, this.count);
        System.arraycopy(this.y, 0, newY, 0, this.count);
        System.arraycopy(this.z, 0, newZ, 0, this.count);

        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    /**
     * Gets the x coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the x coordinate
     */
    public final double getX(final int index) {

        return this.x[index];
    }

    /**
     * Gets the y coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the y coordinate
     */
    public final double getY(final int index) {

        return this.y[index];
    }

    /**
     * Gets the z coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the z coordinate
     */
    public final double getZ(final int index) {

        return this.z[index];
    }

    /**
     * Gets a tuple's type.
     *
     * @param index the index
     * @return the types
     */
    public final ETupleType getType(final int index) {

        return this.type[index];
    }

    /**
     * Gets the r coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the r coordinate
     */
    public final double getR(final int index) {

        return length(index);
    }

    /**
     * Gets the theta coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the theta coordinate
     */
    public final double getTheta(final int index) {

        final double xx = this.x[index];
        final double yy = this.x[index];

        final double result;

        if (yy == 0.0) {
            result = xx >= 0.0 ? 0.0 : 180.0;
        } else if (xx == 0.0) {
            result = yy >= 0.0 ? 90.0 : 270.0;
        } else {
            final double radians = Math.atan2(yy, xx);
            result = Math.toDegrees(radians);
        }

        return result;
    }

    /**
     * Gets the phi coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the phi coordinate
     */
    public final double getPhi(final int index) {

        final double xx = this.x[index];
        final double yy = this.x[index];
        final double zz = this.z[index];

        final double result;

        if (xx == 0.0 && yy == 0) {
            result = zz >= 0.0 ? 0.0 : 180.0;
        } else {
            final double len = length(index);
            final double radians = Math.acos(zz / len);
            result = Math.toDegrees(radians);
        }

        return result;
    }

    /**
     * Calculates the square of the length of the vector with a specified index.
     *
     * @param index the index
     * @return the squared length
     */
    public final double lengthSquared(final int index) {

        final double xx = this.x[index];
        final double yy = this.x[index];

        return xx * xx + yy * yy;
    }

    /**
     * Calculates the length of the vector with a specified index.
     *
     * @param index the index
     * @return the length
     */
    public final double length(final int index) {

        final double xx = this.x[index];
        final double yy = this.x[index];

        return Math.sqrt(xx * xx + yy * yy);
    }

    /**
     * Generates the string representation of the tuple.  Complete data is printed until the number of tuples exceeds
     * 100, after which tuple data is elided.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final int sz = this.count;
        final CharHtmlBuilder builder = new CharHtmlBuilder(20 * sz);

        final String s = Integer.toString(sz);
        builder.addStrings("Array of ", s, " tuples: [");

        if (sz > 0) {
            String xStr = Double.toString(this.x[0]);
            String yStr = Double.toString(this.y[0]);
            String zStr = Double.toString(this.z[0]);
            builder.addStrings(xStr, ",", yStr, ",", zStr);

            final int max = Math.min(sz, MAX_TUPLES_TO_PRINT);
            for (int i = 1; i < sz; ++i) {
                xStr = Double.toString(this.x[i]);
                yStr = Double.toString(this.y[i]);
                zStr = Double.toString(this.z[i]);
                builder.addStrings("|", xStr, ",", yStr, ",", zStr);
            }

            if (sz > max) {
                builder.addString("|...");
            }
        }

        builder.addChar(']');

        return builder.toString();
    }
}
