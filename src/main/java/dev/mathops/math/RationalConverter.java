package dev.mathops.math;

import dev.mathops.commons.number.BigIrrational;
import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.Irrational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Utility method that can convert any finite {@code Number} to a {@code Rational}, {@code BigRational},
 * {@code Irrational}, or {@code BigIrrational} with no loss of precision.
 */
public enum RationalConverter {
    ;

    /** The maximum value that can fit in a long as a {@code BigInteger}. */
    private static final BigInteger BIG_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);

    /** The minimum value that can fit in a long as a {@code BigInteger}. */
    private static final BigInteger BIG_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);

    /**
     * Converts any finite number to one of the four target classes.  If the input number is already one of these, it is
     * simply returned.  Otherwise, the number is converted.
     *
     * @param number the number
     * @return the converted number (a {@code Rational}, a {@code BigRational}, an {@code Irrational} or a
     *         {@code BigIrrational})
     * @throws IllegalArgumentException if the input is an infinite or NaN value
     */
    public static Number toRationalOrIrrational(final Number number) {

        final Number result;

        if (number instanceof Rational
            || number instanceof BigRational
            || number instanceof Irrational
            || number instanceof BigIrrational) {
            result = number;
        } else if (number instanceof Long
                   || number instanceof Integer
                   || number instanceof Short
                   || number instanceof Byte
                   || number instanceof AtomicInteger
                   || number instanceof AtomicLong
                   || number instanceof LongAdder
                   || number instanceof LongAccumulator) {
            // Integer types no larger than a long convert to {@code Rational}
            final long value = number.longValue();
            result = new Rational(value, 1L);
        } else if (number instanceof final Float f) {
            final float value = f.floatValue();
            if (Float.isFinite(value)) {
                final int bits = Float.floatToIntBits(value);

                final int sign = (bits & 0x80000000) >> 31; // 0 if positive, 1 if negative
                final int exponent = ((bits & 0x7f800000) >> 23) - 127; // From -126 to 127 (if finite)
                final int significand = 0x00800000 | (bits & 0x007fffff); // Adds the leading "1."

                // The number is "significand x 2^{-22} x 2^{exponent}"
                final int totalExponent = exponent - 22;

                if (totalExponent >= 0) {
                    // Value is an integer
                    if (totalExponent < 39) {
                        // Value will fit in a long
                        final long numer = (long) significand << totalExponent;
                        result = new Rational(sign == 0 ? numer : -numer);
                    } else {
                        // Value needs a BigInteger
                        final BigInteger big1 = BigInteger.valueOf((long) significand);
                        final BigInteger base = BigInteger.valueOf(2L);
                        final BigInteger big2 = base.pow(totalExponent);
                        final BigInteger numer = big1.multiply(big2);
                        if (sign == 0) {
                            result = new BigRational(numer);
                        } else {
                            final BigInteger negated = numer.negate();
                            result = new BigRational(negated);
                        }
                    }
                } else {
                    // Value is not an integer
                    final BigInteger numer = BigInteger.valueOf((long) significand);
                    final BigInteger base = BigInteger.valueOf(2L);
                    final BigInteger denom = base.pow(-totalExponent);
                    if (sign == 0) {
                        result = new BigRational(numer, denom);
                    } else {
                        final BigInteger negated = numer.negate();
                        result = new BigRational(negated, denom);
                    }
                }
            } else {
                throw new IllegalArgumentException("Infinite and NaN values not supported.");
            }

        } else if (number instanceof final BigInteger bigInt) {
            if (bigInt.compareTo(BIG_MAX_LONG) > 0 || bigInt.compareTo(BIG_MIN_LONG) < 0) {
                // value is greater than the greatest Long or less than the least Long, so we must use a BigRational
                result = new BigRational(bigInt);
            } else {
                final long longValue = bigInt.longValue();
                result = new Rational(longValue);
            }
        } else if (number instanceof final BigDecimal bigDec) {
            result = NumberUtils.bigDecimalToBigRational(bigDec);
        } else {
            // Treat all other types as "double"
            final double value = number.doubleValue();
            if (Double.isFinite(value)) {
                final long bits = Double.doubleToLongBits(value);

                final long sign = (bits & 0x8000000000000000L) >> 63; // 0 if positive, 1 if negative
                final long exponent = ((bits & 0x7ff0000000000000L) >> 52) - 1023; // From -1022 to 1023 (if finite)
                final long significand = 0x0010000000000000L | (bits & 0x000fffffffffffffL); // Adds the leading "1."

                // The number is "significand x 2^{-51} x 2^{exponent}"
                final int totalExponent = (int) (exponent) - 51;

                if (totalExponent >= 0) {
                    // Value is an integer
                    if (totalExponent < 10) {
                        // Value will fit in a long
                        final long numer = (long) significand << totalExponent;
                        result = new Rational(sign == 0L ? numer : -numer);
                    } else {
                        // Value needs a BigInteger
                        final BigInteger big1 = BigInteger.valueOf((long) significand);
                        final BigInteger base = BigInteger.valueOf(2L);
                        final BigInteger big2 = base.pow(totalExponent);
                        final BigInteger numer = big1.multiply(big2);
                        if (sign == 0L) {
                            result = new BigRational(numer);
                        } else {
                            final BigInteger negated = numer.negate();
                            result = new BigRational(negated);
                        }
                    }
                } else {
                    // Value is not an integer
                    final BigInteger numer = BigInteger.valueOf((long) significand);
                    final BigInteger base = BigInteger.valueOf(2L);
                    final BigInteger denom = base.pow(-totalExponent);
                    if (sign == 0L) {
                        result = new BigRational(numer, denom);
                    } else {
                        final BigInteger negated = numer.negate();
                        result = new BigRational(negated, denom);
                    }
                }
            } else {
                throw new IllegalArgumentException("Infinite and NaN values not supported.");
            }
        }

        return result;
    }
}