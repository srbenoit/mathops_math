package dev.mathops.math;

import dev.mathops.commons.number.Rational;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for the RationalConverter class.
 */
final class TestRationalConverter {

    /**
     * Constructs a new {@code TestRationalConverter}.
     */
    TestRationalConverter() {

        // No action
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Convert Rational to Rational")
    void testRational() {

        final Rational input = new Rational(100003L, 100007L);
        final Number output = RationalConverter.toRationalOrIrrational(input);

        assertSame(input, output, "Rational is not converted to itself");
    }
}
