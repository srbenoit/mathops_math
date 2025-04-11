package dev.mathops.math.geom;

import dev.mathops.text.builder.CharHtmlBuilder;

/**
 * A base class for dynamically sized arrays of homogeneous points, vectors, and normal vectors in the space with
 * {@code double} coordinates.
 */
public class Tuple4DArray {

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

    /** The Z coordinates of the tuples in this array. */
    private double[] z;

    /** The W coordinates of the tuples in this array. */
    private double[] w;

    /** The type of each tuple. */
    private ETupleType[] type;

    /** The number of tuples populated. */
    private int count;

    /**
     * Constructs a new {@code Tuple4DArray} with specified initial capacity.
     *
     * @param initialCapacity the initial capacity (clamped to [1, MAX_INITIAL_CAP])
     * @throws IllegalArgumentException if either coordinate is not finite
     */
    Tuple4DArray(final int initialCapacity) {

        final int clampedAbove = Math.min(MAX_INITIAL_CAP, initialCapacity);
        final int clamped = Math.max(clampedAbove, 1);

        this.x = new double[clamped];
        this.y = new double[clamped];
        this.z = new double[clamped];
        this.w = new double[clamped];
        this.type = new ETupleType[clamped];
    }

    /**
     * Constructs a new {@code Tuple4DArray} with coordinate arrays copied from another {@code Tuple3DArray}.
     *
     * @param src the source whose coordinate arrays to copy
     */
    Tuple4DArray(final Tuple4DArray src) {

        this.x = src.x.clone();
        this.y = src.y.clone();
        this.z = src.z.clone();
        this.w = src.w.clone();
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
            this.w = new double[clampedAbove];
            this.type = new ETupleType[clampedAbove];
        }
    }

    /**
     * Adds a new tuple to the end of this array.
     *
     * @param theX    the x coordinate
     * @param theY    the y coordinate
     * @param theZ    the z coordinate
     * @param theW    the w coordinate
     * @param theType the tuple's type
     * @throws IllegalStateException if the array size cannot be increased to accommodate the new tuple
     */
    public final void add(final double theX, final double theY, final double theZ, final double theW,
                          final ETupleType theType) throws IllegalStateException {

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
            this.w[this.count] = theW;
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
        final double[] newW = new double[newCap];

        System.arraycopy(this.x, 0, newX, 0, this.count);
        System.arraycopy(this.y, 0, newY, 0, this.count);
        System.arraycopy(this.z, 0, newZ, 0, this.count);
        System.arraycopy(this.w, 0, newW, 0, this.count);

        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
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
     * Gets the w coordinate of the point with a specified index.
     *
     * @param index the index
     * @return the w coordinate
     */
    public final double getW(final int index) {

        return this.w[index];
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
            String wStr = Double.toString(this.w[0]);
            builder.addStrings(xStr, ",", yStr, ",", zStr, ",", wStr);

            final int max = Math.min(sz, MAX_TUPLES_TO_PRINT);
            for (int i = 1; i < sz; ++i) {
                xStr = Double.toString(this.x[i]);
                yStr = Double.toString(this.y[i]);
                zStr = Double.toString(this.z[i]);
                wStr = Double.toString(this.w[i]);
                builder.addStrings("|", xStr, ",", yStr, ",", zStr, ",", wStr);
            }

            if (sz > max) {
                builder.addString("|...");
            }
        }

        builder.addChar(']');

        return builder.toString();
    }
}
