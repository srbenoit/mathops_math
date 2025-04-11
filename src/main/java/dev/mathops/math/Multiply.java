package dev.mathops.math;

import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utilities for multiplying numbers where the result is a numeric type that will not overflow.  Supported number types
 * include {code Long}, {code Double}, {code Rational}, {code BigRational}, {code BigInteger}, and {code BigDecimal}.
 * Any other types passed will be treated as double.
 */
public class Multiply extends NumberUtils {

    /**
     * Multiplies two numbers without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number numberTimesNumber(final Number factor1, final Number factor2) {

        final Number result;

        if (isZero(factor1) || isZero(factor2)) {
            result = Long.valueOf(0L);
        } else if (isOne(factor1)) {
            result = factor2;
        } else if (isOne(factor2)) {
            result = factor1;
        } else if (factor1 instanceof final Long l1) {
            result = longTimesNumber(l1, factor2);
        } else if (factor1 instanceof final Rational r1) {
            result = rationalTimesNumber(r1, factor2);
        } else if (factor1 instanceof final BigRational br1) {
            result = bigRationalTimesNumber(br1, factor2);
        } else if (factor1 instanceof final BigInteger bi1) {
            result = bigIntegerTimesNumber(bi1, factor2);
        } else if (factor1 instanceof final BigDecimal bd1) {
            result = bigDecimalTimesNumber(bd1, factor2);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }

    /**
     * Multiplies a {code Long} and a number without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesNumber(final Long factor1, final Number factor2) {

        final Number result;

        if (factor2 instanceof final Long l2) {
            result = longTimesLong(factor1, l2);
        } else if (factor2 instanceof final Rational r2) {
            result = longTimesRational(factor1, r2);
        } else if (factor2 instanceof final BigRational br2) {
            result = longTimesBigRational(factor1, br2);
        } else if (factor2 instanceof final BigInteger bi2) {
            result = longTimesBigInteger(factor1, bi2);
        } else if (factor2 instanceof final BigDecimal bd2) {
            result = longTimesBigDecimal(factor1, bd2);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }

    /**
     * Multiplies a {code Rational} and a number without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number rationalTimesNumber(final Rational factor1, final Number factor2) {

        final Number result;

        if (factor2 instanceof final Long l2) {
            result = longTimesRational(l2, factor1);
        } else if (factor2 instanceof final Rational r2) {
            result = rationalTimesRational(factor1, r2);
        } else if (factor2 instanceof final BigRational br2) {
            result = rationalTimesBigRational(factor1, br2);
        } else if (factor2 instanceof final BigInteger bi2) {
            result = rationalTimesBigInteger(factor1, bi2);
        } else if (factor2 instanceof final BigDecimal bd2) {
            final BigRational bigRat = bigDecimalToBigRational(bd2);
            result = rationalTimesBigRational(factor1, bigRat);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }

    /**
     * Multiplies a {code BigRational} and a number without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigRationalTimesNumber(final BigRational factor1, final Number factor2) {

        final Number result;

        if (factor2 instanceof final Long l2) {
            result = longTimesBigRational(l2, factor1);
        } else if (factor2 instanceof final Rational r2) {
            result = rationalTimesBigRational(r2, factor1);
        } else if (factor2 instanceof final BigRational br2) {
            result = bigRationalTimesBigRational(factor1, br2);
        } else if (factor2 instanceof final BigInteger bi2) {
            result = bigRationalTimesBigInteger(factor1, bi2);
        } else if (factor2 instanceof final BigDecimal bd2) {
            final BigRational bigRat = bigDecimalToBigRational(bd2);
            result = bigRationalTimesBigRational(factor1, bigRat);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }

    /**
     * Multiplies a {code BigInteger} and a number without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigIntegerTimesNumber(final BigInteger factor1, final Number factor2) {

        final Number result;

        if (factor2 instanceof final Long l2) {
            result = longTimesBigInteger(l2, factor1);
        } else if (factor2 instanceof final Rational r2) {
            result = rationalTimesBigInteger(r2, factor1);
        } else if (factor2 instanceof final BigRational br2) {
            result = bigRationalTimesBigInteger(br2, factor1);
        } else if (factor2 instanceof final BigInteger bi2) {
            result = bigIntegerTimesBigInteger(factor1, bi2);
        } else if (factor2 instanceof final BigDecimal bd2) {
            result = bigIntegerTimesBigDecimal(factor1, bd2);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }

    /**
     * Multiplies a {code BigDecimal} and a number without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigDecimalTimesNumber(final BigDecimal factor1, final Number factor2) {

        final Number result;

        if (factor2 instanceof final Long l2) {
            result = longTimesBigDecimal(l2, factor1);
        } else if (factor2 instanceof final Rational r2) {
            final BigRational bigRat = bigDecimalToBigRational(factor1);
            result = rationalTimesBigRational(r2, bigRat);
        } else if (factor2 instanceof final BigRational br2) {
            final BigRational bigRat = bigDecimalToBigRational(factor1);
            result = bigRationalTimesBigRational(br2, bigRat);
        } else if (factor2 instanceof final BigInteger bi2) {
            result = bigIntegerTimesBigDecimal(bi2, factor1);
        } else if (factor2 instanceof final BigDecimal bd2) {
            result = bigDecimalTimesBigDecimal(factor1, bd2);
        } else {
            final double d1 = factor1.doubleValue();
            final double d2 = factor2.doubleValue();
            result = Double.valueOf(d1 * d2);
        }

        return result;
    }


    /**
     * Computes the product of two {code Long} factors, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesLong(final Long factor1, final Long factor2) {

        Number result;

        final long l1 = factor1.longValue();
        final long l2 = factor2.longValue();

        try {
            final long prod = Math.multiplyExact(l1, l2);
            result = Long.valueOf(prod);
        } catch (final ArithmeticException ex) {
            final BigInteger bigFactor2 = BigInteger.valueOf(l2);
            result = BigInteger.valueOf(l1).multiply(bigFactor2);
        }

        return result;
    }

    /**
     * Computes the product of a {code Long}  and a {code Rational} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesRational(final Long factor1, final Rational factor2) {

        final long l1 = factor1.longValue();
        final long numer = factor2.numerator;
        final long denom = factor2.denominator;

        Number result;

        try {
            final long newNumer = Math.multiplyExact(l1, numer);
            result = simplifyRational(new Rational(newNumer, denom));
        } catch (final ArithmeticException ex) {
            final BigInteger val = BigInteger.valueOf(numer);
            final BigInteger bigNumer = BigInteger.valueOf(l1).multiply(val);
            final BigInteger bigDenom = BigInteger.valueOf(denom);
            result = simplifyBigRational(new BigRational(bigNumer, bigDenom));
        }

        return result;
    }

    /**
     * Computes the product of a {code Long}  and a {code BigRational} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesBigRational(final Long factor1, final BigRational factor2) {

        final long l1 = factor1.longValue();
        final BigInteger bigFactor1 = BigInteger.valueOf(l1);
        final BigInteger numer = factor2.numerator;
        final BigInteger newNumer = bigFactor1.multiply(numer);
        final BigRational bigResult = new BigRational(newNumer, factor2.denominator);

        return simplifyBigRational(bigResult);
    }

    /**
     * Computes the product of a {code Long}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesBigInteger(final Long factor1, final BigInteger factor2) {

        final long l1 = factor1.longValue();
        final BigInteger big1 = BigInteger.valueOf(l1);

        return big1.multiply(factor2);
    }

    /**
     * Computes the product of a {code Long}  and a {code BigDecimal} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number longTimesBigDecimal(final Long factor1, final BigDecimal factor2) {

        final long l1 = factor1.longValue();
        final BigDecimal big1 = BigDecimal.valueOf(l1);
        final BigDecimal bigResult = big1.multiply(factor2);

        return NumberUtils.simplifyBigDecimal(bigResult);
    }

    /**
     * Computes the product of two {code Rational} factors, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number rationalTimesRational(final Rational factor1, final Rational factor2) {

        final long numer1 = factor1.numerator;
        final long denom1 = factor1.denominator;
        final long numer2 = factor2.numerator;
        final long denom2 = factor2.denominator;

        Number result;

        try {
            final long newNumer = Math.multiplyExact(numer1, numer2);
            final long newDenom = Math.multiplyExact(denom1, denom2);

            result = NumberUtils.simplifyRational(new Rational(newNumer, newDenom));
        } catch (final ArithmeticException ex) {
            final BigInteger bigNumer2 = BigInteger.valueOf(numer2);
            final BigInteger bigNumer = BigInteger.valueOf(numer1).multiply(bigNumer2);
            final BigInteger bigDenom2 = BigInteger.valueOf(denom2);
            final BigInteger bigDenom = BigInteger.valueOf(denom1).multiply(bigDenom2);
            final BigRational bigResult = new BigRational(bigNumer, bigDenom);

            result = NumberUtils.simplifyBigRational(bigResult);
        }

        return result;
    }

    /**
     * Computes the product of a {code Rational}  and a {code BigRational} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number rationalTimesBigRational(final Rational factor1, final BigRational factor2) {

        final BigInteger numer1 = BigInteger.valueOf(factor1.numerator);
        final BigInteger newNumer = numer1.multiply(factor2.numerator);

        final BigInteger denom1 = BigInteger.valueOf(factor1.denominator);
        final BigInteger newDenom = denom1.multiply(factor2.denominator);

        final BigRational bigResult = new BigRational(newNumer, newDenom);

        return NumberUtils.simplifyBigRational(bigResult);
    }

    /**
     * Computes the product of a {code Rational}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number rationalTimesBigInteger(final Rational factor1, final BigInteger factor2) {

        final BigInteger numer1 = BigInteger.valueOf(factor1.numerator);
        final BigInteger denom1 = BigInteger.valueOf(factor1.denominator);
        final BigInteger newNumer = numer1.multiply(factor2);
        final BigRational bigResult = new BigRational(newNumer, denom1);

        return NumberUtils.simplifyBigRational(bigResult);
    }

    /**
     * Computes the product of two {code BigRational} factors, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigRationalTimesBigRational(final BigRational factor1, final BigRational factor2) {

        final BigInteger newNumer = factor1.numerator.multiply(factor2.numerator);
        final BigInteger newDenom = factor1.denominator.multiply(factor2.denominator);
        final BigRational bigResult = new BigRational(newNumer, newDenom);

        return NumberUtils.simplifyBigRational(bigResult);
    }


    /**
     * Computes the product of a {code BigRational}  and a {code BigInteger} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigRationalTimesBigInteger(final BigRational factor1, final BigInteger factor2) {

        final BigInteger newNumer = factor1.numerator.multiply(factor2);
        final BigRational bigResult = new BigRational(newNumer, factor1.denominator);

        return NumberUtils.simplifyBigRational(bigResult);
    }


    /**
     * Computes the product of a {code BigInteger} and a {code BigInteger} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigIntegerTimesBigInteger(final BigInteger factor1, final BigInteger factor2) {

        final BigInteger big1 = factor1.multiply(factor2);

        return NumberUtils.simplifyBigInteger(big1);
    }


    /**
     * Computes the product of a {code BigInteger} and a {code BigDecimal} factor, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigIntegerTimesBigDecimal(final BigInteger factor1, final BigDecimal factor2) {

        final BigDecimal big1 = new BigDecimal(factor1);
        final BigDecimal bigResult = big1.multiply(factor2);

        return NumberUtils.simplifyBigDecimal(bigResult);
    }


    /**
     * Computes the product of two {code BigDecimal} factors, without risk of overflow.
     *
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    public static Number bigDecimalTimesBigDecimal(final BigDecimal factor1, final BigDecimal factor2) {

        final BigDecimal bigResult = factor1.multiply(factor2);

        return NumberUtils.simplifyBigDecimal(bigResult);
    }
}
