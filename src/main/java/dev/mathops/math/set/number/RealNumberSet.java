package dev.mathops.math.set.number;

import dev.mathops.math.NumberComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A mutable set of finitely many real numbers.
 */
public class RealNumberSet extends AbstractRealNumberSet {

    /** The sorted list of numbers. */
    private final List<Number> contents;

    /**
     * Constructs a new {@code RealNumberSet}.
     *
     * @param capacity the initial capacity of the set
     */
    public RealNumberSet(final int capacity) {

        super();

        this.contents = new ArrayList<>(capacity);
    }

    /**
     * Constructs a new {@code RealNumberSet}.
     *
     * @param elements the initial set elements
     */
    public RealNumberSet(final Number... elements) {

        super();

        if (elements == null || elements.length == 0) {
            this.contents = new ArrayList<>(0);
        } else {
            this.contents = new ArrayList<>(elements.length);
            Collections.addAll(this.contents, elements);
            this.contents.sort(NumberComparator.INSTANCE);
        }
    }

    /**
     * Tests whether the set is empty.
     *
     * @return {@code true} if the set contains no numbers; {@code false} if the set has at least one number
     */
    public boolean isEmpty() {

        return this.contents.isEmpty();
    }

    /**
     * Tests whether the set contains a number.
     *
     * @param number the number
     * @return {@code true} if the set contains the number; {@code false} if not
     */
    public boolean contains(final Number number) {

        boolean contains = false;

        for (final Number test : this.contents) {
            if (NumberComparator.INSTANCE.isNumericallyEqual(test, number)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    /**
     * Returns the lower bound for the set.
     *
     * @return the lower bound ({@code Double.NEGATIVE_INFINITY} if not bounded below or 0.0 if the set is empty)
     */
    public Number lowerBound() {

        return this.contents.isEmpty() ? Double.valueOf(0.0) : this.contents.getFirst();
    }

    /**
     * Returns the upper bound for the set.
     *
     * @return the upper bound ({@code Double.POSITIVE_INFINITY} if not bounded above or 0.0 if the set is empty)
     */
    public Number upperBound() {

        return this.contents.isEmpty() ? Double.valueOf(0.0) : this.contents.getLast();
    }
}
