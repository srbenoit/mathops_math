package dev.mathops.math;

import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utilities for dividing numbers where the result is a numeric type that will not overflow.  Supported number types
 * include {code Long}, {code Double}, {code Rational}, {code BigRational}, {code BigInteger}, and {code BigDecimal}.
 * Any other types passed will be treated as double.
 */
public class Divide extends NumberUtils {

    /**
     * Divides two numbers without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient, {code NaN} if {code divisor} was zero
     */
    public static Number numberDivNumber(final Number dividend, final Number divisor) {

        final Number result;

        if (isZero(divisor)) {
            result = Double.valueOf(Double.NaN);
        } else if (isZero(dividend)) {
            result = Long.valueOf(0L);
        } else if (isOne(divisor)) {
            result = dividend;
        } else if (dividend instanceof final Long l1) {
            result = longDivNumber(l1, divisor);
        } else if (dividend instanceof final Rational r1) {
            result = rationalDivNumber(r1, divisor);
        } else if (dividend instanceof final BigRational br1) {
            result = bigRationalDivNumber(br1, divisor);
        } else if (dividend instanceof final BigInteger bi1) {
            result = bigIntegerDivNumber(bi1, divisor);
        } else if (dividend instanceof final BigDecimal bd1) {
            final BigRational bigRat = NumberUtils.bigDecimalToBigRational(bd1);
            result = bigRationalDivNumber(bigRat, divisor);
        } else {
            final double d1 = dividend.doubleValue();
            final double d2 = divisor.doubleValue();
            result = Double.valueOf(d1 / d2);
        }

        return result;
    }

    /**
     * Divides a {code Long} and a number without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number longDivNumber(final Long dividend, final Number divisor) {

        final Number result;

        if (divisor instanceof final Long l2) {
            result = longDivLong(dividend, l2);
        } else if (divisor instanceof final Rational r2) {
            final Rational recip = r2.reciprocal();
            result = Multiply.longTimesRational(dividend, recip);
        } else if (divisor instanceof final BigRational br2) {
            final BigRational recip = br2.reciprocal();
            result = Multiply.longTimesBigRational(dividend, recip);
        } else if (divisor instanceof final BigInteger bi2) {
            result = longDivBigInteger(dividend, bi2);
        } else if (divisor instanceof final BigDecimal bd2) {
            final BigRational bigRat = NumberUtils.bigDecimalToBigRational(bd2);
            final BigRational recip = bigRat.reciprocal();
            result = Multiply.longTimesBigRational(dividend, recip);
        } else {
            final double d1 = dividend.doubleValue();
            final double d2 = divisor.doubleValue();
            result = Double.valueOf(d1 / d2);
        }

        return result;
    }

    /**
     * Divides a {code Rational} and a number without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number rationalDivNumber(final Rational dividend, final Number divisor) {

        final Number result;

        if (divisor instanceof final Long l2) {
            result = rationalDivLong(dividend, l2);
        } else if (divisor instanceof final Rational r2) {
            final Rational recip = r2.reciprocal();
            result = Multiply.rationalTimesRational(dividend, recip);
        } else if (divisor instanceof final BigRational br2) {
            final BigRational recip = br2.reciprocal();
            result = Multiply.rationalTimesBigRational(dividend, recip);
        } else if (divisor instanceof final BigInteger bi2) {
            result = rationalDivBigInteger(dividend, bi2);
        } else if (divisor instanceof final BigDecimal bd2) {
            final BigRational bigRat = NumberUtils.bigDecimalToBigRational(bd2);
            final BigRational recip = bigRat.reciprocal();
            result = Multiply.rationalTimesBigRational(dividend, recip);
        } else {
            final double d1 = dividend.doubleValue();
            final double d2 = divisor.doubleValue();
            result = Double.valueOf(d1 / d2);
        }

        return result;
    }

    /**
     * Divides a {code BigRational} and a number without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigRationalDivNumber(final BigRational dividend, final Number divisor) {

        final Number result;

        if (divisor instanceof final Long l2) {
            result = bigRationalDivLong(dividend, l2);
        } else if (divisor instanceof final Rational r2) {
            final Rational recip = r2.reciprocal();
            result = Multiply.rationalTimesBigRational(recip, dividend);
        } else if (divisor instanceof final BigRational br2) {
            final BigRational recip = br2.reciprocal();
            result = Multiply.bigRationalTimesBigRational(dividend, recip);
        } else if (divisor instanceof final BigInteger bi2) {
            result = bigRationalDivBigInteger(dividend, bi2);
        } else if (divisor instanceof final BigDecimal bd2) {
            final BigRational bigRat = NumberUtils.bigDecimalToBigRational(bd2);
            result = Multiply.bigRationalTimesBigRational(dividend, bigRat);
        } else {
            final double d1 = dividend.doubleValue();
            final double d2 = divisor.doubleValue();
            result = Double.valueOf(d1 / d2);
        }

        return result;
    }

    /**
     * Divides a {code BigInteger} and a number without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigIntegerDivNumber(final BigInteger dividend, final Number divisor) {

        final Number result;

        if (divisor instanceof final Long l2) {
            result = bigIntegerDivLong(dividend, l2);
        } else if (divisor instanceof final Rational r2) {
            final Rational recip = r2.reciprocal();
            result = Multiply.rationalTimesBigInteger(recip, dividend);
        } else if (divisor instanceof final BigRational br2) {
            final BigRational recip = br2.reciprocal();
            result = Multiply.bigRationalTimesBigInteger(recip, dividend);
        } else if (divisor instanceof final BigInteger bi2) {
            result = bigIntegerDivBigInteger(dividend, bi2);
        } else if (divisor instanceof final BigDecimal bd2) {
            final BigRational bigRat = NumberUtils.bigDecimalToBigRational(bd2);
            final BigRational recip = bigRat.reciprocal();
            result = Multiply.bigRationalTimesBigInteger(recip, dividend);
        } else {
            final double d1 = dividend.doubleValue();
            final double d2 = divisor.doubleValue();
            result = Double.valueOf(d1 / d2);
        }

        return result;
    }

    /**
     * Computes the quotient of two {code Long} factors, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number longDivLong(final Long dividend, final Long divisor) {

        final long l1 = dividend.longValue();
        final long l2 = divisor.longValue();
        return simplifyRational(new Rational(l1, l2));
    }

    /**
     * Computes the quotient of a {code Long}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number longDivBigInteger(final Long dividend, final BigInteger divisor) {

        final long val = dividend.longValue();
        final BigInteger bi1 = BigInteger.valueOf(val);

        return simplifyBigRational(new BigRational(bi1, divisor));
    }

    /**
     * Computes the quotient of a {code Rational} and a {code Long} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number rationalDivLong(final Rational dividend, final Long divisor) {

        Number result;

        final long l2 = divisor.longValue();

        try {
            final long newDenom = Math.multiplyExact(dividend.denominator, l2);

            result = NumberUtils.simplifyRational(new Rational(dividend.numerator, newDenom));
        } catch (final ArithmeticException ex) {
            final BigInteger bi2 = BigInteger.valueOf(l2);
            final BigInteger n1 = BigInteger.valueOf(dividend.numerator);
            final BigInteger d1 = BigInteger.valueOf(dividend.denominator);
            final BigInteger newDenom = d1.multiply(bi2);

            result = NumberUtils.simplifyBigRational(new BigRational(n1, newDenom));
        }

        return result;
    }

    /**
     * Computes the quotient of a {code Rational}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number rationalDivBigInteger(final Rational dividend, final BigInteger divisor) {

        final BigInteger n1 = BigInteger.valueOf(dividend.numerator);
        final BigInteger d1 = BigInteger.valueOf(dividend.denominator);
        final BigInteger newDenom = d1.multiply(divisor);

        return NumberUtils.simplifyBigRational(new BigRational(n1, newDenom));
    }

    /**
     * Computes the quotient of a {code BigRational}  and a {code Long} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigRationalDivLong(final BigRational dividend, final Long divisor) {

        final long l2 = divisor.longValue();
        final BigInteger bi2 = BigInteger.valueOf(l2);
        final BigInteger newDenom = dividend.denominator.multiply(bi2);

        return NumberUtils.simplifyBigRational(new BigRational(dividend.numerator, newDenom));
    }

    /**
     * Computes the quotient of a {code BigRational}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigRationalDivBigInteger(final BigRational dividend, final BigInteger divisor) {

        final BigInteger newDenom = dividend.denominator.multiply(divisor);

        return NumberUtils.simplifyBigRational(new BigRational(dividend.numerator, newDenom));
    }

    /**
     * Computes the quotient of a {code BigInteger} and a {code Long} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigIntegerDivLong(final BigInteger dividend, final Long divisor) {

        final long val = divisor.longValue();
        final BigInteger bi2 = BigInteger.valueOf(val);

        return simplifyBigRational(new BigRational(dividend, bi2));
    }

    /**
     * Computes the quotient of a {code BigInteger} and a {code BigInteger} factor, without risk of overflow.
     *
     * @param dividend the dividend (numerator)
     * @param divisor  the divisor (denominator)
     * @return the quotient
     */
    public static Number bigIntegerDivBigInteger(final BigInteger dividend, final BigInteger divisor) {

        return simplifyBigRational(new BigRational(dividend, divisor));
    }
}
