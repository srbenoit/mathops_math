package dev.mathops.math.set.point2;

import dev.mathops.math.geom.Tuple2D;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of points defined by a list of individual points.
 */
public class PlanePointList extends AbstractPlanePointSet {

    /** The list of points. */
    public final List<Tuple2D> points;

    /**
     * Constructs a new {@code PlanePointList}.
     */
    public PlanePointList() {

        super();

        this.points = new ArrayList<>(10);
    }

    /**
     * Constructs a new {@code PlanePointList}.
     *
     * @param capacity the initial capacity of the list
     */
    public PlanePointList(final int capacity) {

        super();

        this.points = new ArrayList<>(capacity);
    }
}
