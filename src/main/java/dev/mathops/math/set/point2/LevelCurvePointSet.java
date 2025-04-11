package dev.mathops.math.set.point2;

import dev.mathops.math.function.parametric.IParametricSurface;
import dev.mathops.math.geom.Ray2D;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of points defined as the level curve of a parametric surface at a specified height value.
 */
public class LevelCurvePointSet extends AbstractPlanePointSet {

    /** The surface. */
    public final IParametricSurface surface;

    /**
     * The height that defines the curve.  The curve will be found by locating roots of the function formed by
     * subtracting this height from the surface. The numerical method used will likely miss roots that are just tangent
     * to the surface. Please define surfaces such that the level curve slices the surface with surface extending both
     * above and below the level curve.
     */
    public final double height;

    /**
     * Constructs a new {@code FunctionPointSet}.
     *
     * @param theSurface the surface
     * @param theHeight  the y function
     */
    public LevelCurvePointSet(final IParametricSurface theSurface, final double theHeight) {

        super();

        this.surface = theSurface;
        this.height = theHeight;
    }

    /**
     * Finds all points along the level curve with a specified $y$ coordinate, along with their gradient vectors. The
     * result is a list of {@code Ray2D}s, with base point located at the point on the level surface, and direction
     * giving the direction normal to the level curve at that point.
     *
     * <p>
     * A rendering tool can use the gradient vectors to identify "connected" points to form paths, or to subdivide the
     * domain to locate more points if a connected nearby point cannot be identified.
     *
     * @param y the y coordinate
     * @return a list of rays found; empty if none were found
     */
    public List<Ray2D> eval(final double y) {

        // TODO: scan the domain and search for points.

        return new ArrayList<>(10);
    }
}
