package dev.mathops.math.set.point2;

import java.awt.geom.Area;

/**
 * A point set trimmed by an Area.
 */
public class TrimmedPointSet extends AbstractPlanePointSet {

    /** The point set. */
    public final AbstractPlanePointSet pointSet;

    /** The area to which to trim the point set. */
    public final Area trimArea;

    /**
     * Constructs a new {@code TrimmedPointSet}.
     *
     * @param thePointSet the point set
     * @param theTrimArea the area to which to trim the point set
     */
    public TrimmedPointSet(final AbstractPlanePointSet thePointSet, final Area theTrimArea) {

        super();

        this.pointSet = thePointSet;
        this.trimArea = theTrimArea;
    }
}
