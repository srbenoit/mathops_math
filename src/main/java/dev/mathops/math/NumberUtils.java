package dev.mathops.math;

import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utilities for working with numbers.  Supported number types include {code Long}, {code Double}, {code Rational},
 * {code BigRational}, {code BigInteger}, and {code BigDecimal}.  Any other types passed will be treated as double.
 */
public class NumberUtils {

    /**
     * Negates a number.
     *
     * @param toNegate the number to negate
     * @return the negated number
     */
    public static Number negate(final Number toNegate) {

        final Number result;

        if (toNegate instanceof final Long l) {
            result = Long.valueOf(-l);
        } else if (toNegate instanceof final Rational r) {
            result = new Rational(-r.numerator, r.denominator);
        } else if (toNegate instanceof final BigRational r) {
            final BigInteger num = r.numerator.negate();
            result = new BigRational(num, r.denominator);
        } else if (toNegate instanceof final BigInteger bi) {
            result = bi.negate();
        } else if (toNegate instanceof final BigDecimal bd) {
            result = bd.negate();
        } else {
            final double negated = -toNegate.doubleValue();
            result = Double.valueOf(negated);
        }

        return result;
    }

    /**
     * Tests whether a number is zero.
     *
     * @param number the number to test
     * @return true if the number is zero
     */
    public static boolean isZero(final Number number) {

        final boolean result;

        if (number instanceof final Long l) {
            result = 0L == l.longValue();
        } else if (number instanceof final Rational r) {
            result = 0L == r.numerator;
        } else if (number instanceof final BigRational br) {
            result = BigInteger.ZERO.equals(br.numerator);
        } else if (number instanceof final BigInteger bi) {
            result = BigInteger.ZERO.equals(bi);
        } else if (number instanceof final BigDecimal bd) {
            result = 0 == BigDecimal.ZERO.compareTo(bd);
        } else {
            result = 0.0 == number.doubleValue();
        }

        return result;
    }

    /**
     * Tests whether a number is one.
     *
     * @param number the number to test
     * @return true if the number is zero
     */
    public static boolean isOne(final Number number) {

        final boolean result;

        if (number instanceof final Long l) {
            result = l.longValue() == 1L;
        } else if (number instanceof final Rational r) {
            result = r.numerator == 1L && r.denominator == 1L;
        } else if (number instanceof final BigRational br) {
            result = BigInteger.ONE.equals(br.numerator) && BigInteger.ONE.equals(br.denominator);
        } else if (number instanceof final BigInteger bi) {
            result = BigInteger.ONE.equals(bi);
        } else if (number instanceof final BigDecimal bd) {
            result = 0 == BigDecimal.ONE.compareTo(bd);
        } else {
            result = number.doubleValue() == 1.0;
        }

        return result;
    }

    /**
     * Converts a {code BigDecimal} to a {code BigRational}.
     *
     * @param value the value
     * @return the resulting {code BigRational}
     */
    public static BigRational bigDecimalToBigRational(final BigDecimal value) {

        final int scale = value.scale();

        final BigRational result;

        if (scale <= 0) {
            final BigInteger bigInt = value.toBigInteger();
            result = new BigRational(bigInt, BigInteger.ONE);
        } else {
            final BigInteger unscaled = value.unscaledValue();
            final BigInteger denom = BigInteger.TEN.pow(scale);
            result = new BigRational(unscaled, denom);
        }

        return result;
    }

    /**
     * Checks a {code BigInteger} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return A {code BigInteger} or {code Long} that can contain the value without overflow
     */
    public static Number simplifyBigInteger(final BigInteger value) {

        Number result;

        try {
            final long l1 = value.longValueExact();
            result = Long.valueOf(l1);
        } catch (final ArithmeticException ex2) {
            result = value;
        }

        return result;
    }

    /**
     * Checks a {code Rational} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return A {code Rational} or {code Long} that can contain the value without overflow
     */
    public static Number simplifyRational(final Rational value) {

        final Number result;

        if (value.denominator == 1L) {
            result = Long.valueOf(value.numerator);
        } else {
            result = value;
        }

        return result;
    }

    /**
     * Checks a {code BigRational} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return A {code BigRational}, {code Rational}, {code BigInteger}, or {code Long} that can contain the value
     *         without overflow
     */
    public static Number simplifyBigRational(final BigRational value) {

        Number result;

        if (BigInteger.ONE.equals(value.denominator)) {
            result = simplifyBigInteger(value.numerator);
        } else {
            try {
                final long numer = value.numerator.longValueExact();
                final long denom = value.denominator.longValueExact();
                result = new Rational(numer, denom);
            } catch (final ArithmeticException ex2) {
                result = value;
            }
        }

        return result;
    }

    /**
     * Checks a {code BigDecimal} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return A {code BigDecimal}, {code BigInteger}, or {code Long} that can contain the value without overflow
     */
    public static Number simplifyBigDecimal(final BigDecimal value) {

        Number result;

        try {
            final BigInteger bigIntResult = value.toBigIntegerExact();
            result = simplifyBigInteger(bigIntResult);
        } catch (final ArithmeticException ex) {
            result = value;
        }

        return result;
    }
}
