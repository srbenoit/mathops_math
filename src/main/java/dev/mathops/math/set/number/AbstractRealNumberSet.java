package dev.mathops.math.set.number;

/**
 * The base class for classes that represent sets of real numbers.
 */
public abstract class AbstractRealNumberSet {

    /**
     * Tests whether the set is empty.
     *
     * @return {@code true} if the set contains no numbers; {@code false} if the set has at least one number
     */
    public abstract boolean isEmpty();

    /**
     * Tests whether the set contains a number.
     *
     * @param number the number
     * @return {@code true} if the set contains the number; {@code false} if not
     */
    public abstract boolean contains(Number number);

    /**
     * Returns the lower bound for the set.
     *
     * @return the lower bound ({@code Double.NEGATIVE_INFINITY} if not bounded below)
     */
    public abstract Number lowerBound();

    /**
     * Returns the upper bound for the set.
     *
     * @return the upper bound ({@code Double.POSITIVE_INFINITY} if not bounded above)
     */
    public abstract Number upperBound();
}
