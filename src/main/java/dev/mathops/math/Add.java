/**************************************************************************************************
 * Copyright (C) 2024 Steve Benoit
 *
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <<a href="https://www.gnu.org/licenses/">https://www.gnu.org/licenses/</a>>.
 *************************************************************************************************/
package dev.mathops.math;

import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utilities for adding numbers where the result is a numeric type that will not overflow.  Supported number types
 * include {@code Long}, {@code BigInteger}, {@code Double}, {@code BigDecimal}, {@code Rational}, {@code BigRational},
 * {@code Irrational}, and {@code BigIrrational}. Any other types passed will be treated as double.
 */
public class Add extends NumberUtils {

    /**
     * Adds two numbers without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number numberPlusNumber(final Number term1, final Number term2) {

        final Number result;

        if (isZero(term1)) {
            result = term2;
        } else if (isZero(term2)) {
            result = term1;
        } else if (term1 instanceof final Long l1) {
            result = longPlusNumber(l1, term2);
        } else if (term1 instanceof final Rational r1) {
            result = rationalPlusNumber(r1, term2);
        } else if (term1 instanceof final BigRational br1) {
            result = bigRationalPlusNumber(br1, term2);
        } else if (term1 instanceof final BigInteger bi1) {
            result = bigIntegerPlusNumber(bi1, term2);
        } else if (term1 instanceof final BigDecimal bd1) {
            result = bigDecimalPlusNumber(bd1, term2);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Adds a {code Long} and a number without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusNumber(final Long term1, final Number term2) {

        final Number result;

        if (term2 instanceof final Long l2) {
            result = longPlusLong(term1, l2);
        } else if (term2 instanceof final Rational r2) {
            result = longPlusRational(term1, r2);
        } else if (term2 instanceof final BigRational br2) {
            result = longPlusBigRational(term1, br2);
        } else if (term2 instanceof final BigInteger bi2) {
            result = longPlusBigInteger(term1, bi2);
        } else if (term2 instanceof final BigDecimal bd2) {
            result = longPlusBigDecimal(term1, bd2);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Adds a {code Rational} and a number without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number rationalPlusNumber(final Rational term1, final Number term2) {

        final Number result;

        if (term2 instanceof final Long l2) {
            final long theValue = l2.longValue();
            final Rational r2 = new Rational(theValue);
            return rationalPlusRational(r2, term1);
        } else if (term2 instanceof final Rational r2) {
            result = rationalPlusRational(term1, r2);
        } else if (term2 instanceof final BigRational br2) {
            result = rationalPlusBigRational(term1, br2);
        } else if (term2 instanceof final BigInteger bi2) {
            result = rationalPlusBigInteger(term1, bi2);
        } else if (term2 instanceof final BigDecimal bd2) {
            final BigRational bigRat = bigDecimalToBigRational(bd2);
            result = rationalPlusBigRational(term1, bigRat);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Adds a {code BigRational} and a number without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigRationalPlusNumber(final BigRational term1, final Number term2) {

        final Number result;

        if (term2 instanceof final Long l2) {
            result = longPlusBigRational(l2, term1);
        } else if (term2 instanceof final Rational r2) {
            result = rationalPlusBigRational(r2, term1);
        } else if (term2 instanceof final BigRational br2) {
            result = bigRationalPlusBigRational(term1, br2);
        } else if (term2 instanceof final BigInteger bi2) {
            result = bigRationalPlusBigInteger(term1, bi2);
        } else if (term2 instanceof final BigDecimal bd2) {
            final BigRational bigRat = bigDecimalToBigRational(bd2);
            result = bigRationalPlusBigRational(term1, bigRat);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Adds a {code BigInteger} and a number without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigIntegerPlusNumber(final BigInteger term1, final Number term2) {

        final Number result;

        if (term2 instanceof final Long l2) {
            result = longPlusBigInteger(l2, term1);
        } else if (term2 instanceof final Rational r2) {
            result = rationalPlusBigInteger(r2, term1);
        } else if (term2 instanceof final BigRational br2) {
            result = bigRationalPlusBigInteger(br2, term1);
        } else if (term2 instanceof final BigInteger bi2) {
            final BigInteger sum = term1.add(bi2);
            result = simplifyBigInteger(sum);
        } else if (term2 instanceof final BigDecimal bd2) {
            result = bigIntegerPlusBigDecimal(term1, bd2);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Adds a {code BigDecimal} and a number without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigDecimalPlusNumber(final BigDecimal term1, final Number term2) {

        final Number result;

        if (term2 instanceof final Long l2) {
            result = longPlusBigDecimal(l2, term1);
        } else if (term2 instanceof final Rational r2) {
            final BigRational bigRat = bigDecimalToBigRational(term1);
            result = rationalPlusBigRational(r2, bigRat);
        } else if (term2 instanceof final BigRational br2) {
            final BigRational bigRat = bigDecimalToBigRational(term1);
            result = bigRationalPlusBigRational(br2, bigRat);
        } else if (term2 instanceof final BigInteger bi2) {
            result = bigIntegerPlusBigDecimal(bi2, term1);
        } else if (term2 instanceof final BigDecimal bd2) {
            final BigDecimal sum = term1.add(bd2);
            result = simplifyBigDecimal(sum);
        } else {
            final double d1 = term1.doubleValue();
            final double d2 = term2.doubleValue();
            result = Double.valueOf(d1 + d2);
        }

        return result;
    }

    /**
     * Computes the product of two {code Long} terms, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusLong(final Long term1, final Long term2) {

        Number result;

        final long l1 = term1.longValue();
        final long l2 = term2.longValue();

        try {
            final long sum = Math.addExact(l1, l2);
            result = Long.valueOf(sum);
        } catch (final ArithmeticException ex) {
            final BigInteger bigterm2 = BigInteger.valueOf(l2);
            result = BigInteger.valueOf(l1).add(bigterm2);
        }

        return result;
    }

    /**
     * Computes the product of a {code Long}  and a {code Rational} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusRational(final Long term1, final Rational term2) {

        final long value = term1.longValue();
        final Rational r1 = new Rational(value);

        return rationalPlusRational(r1, term2);
    }

    /**
     * Computes the product of a {code Long}  and a {code BigRational} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusBigRational(final Long term1, final BigRational term2) {

        // A + N/D = (AD + N) / D

        final long l1 = term1.longValue();
        final BigInteger a = BigInteger.valueOf(l1);
        final BigInteger ad = a.multiply(term2.denominator);
        final BigInteger newNumer = ad.add(term2.numerator);

        return simplifyBigRational(new BigRational(newNumer, term2.denominator));
    }

    /**
     * Computes the product of a {code Long}  and a {code BigInteger} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusBigInteger(final Long term1, final BigInteger term2) {

        final long l1 = term1.longValue();
        return BigInteger.valueOf(l1).add(term2);
    }

    /**
     * Computes the product of a {code Long}  and a {code BigDecimal} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number longPlusBigDecimal(final Long term1, final BigDecimal term2) {

        final long l1 = term1.longValue();
        final BigDecimal big1 = BigDecimal.valueOf(l1);
        final BigDecimal sum = big1.add(term2);
        return simplifyBigDecimal(sum);
    }

    /**
     * Computes the product of two {code Rational} terms, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number rationalPlusRational(final Rational term1, final Rational term2) {

        // A/B + C/D = (AD + BC) / (BD)

        Number result;

        try {
            final long ad = Math.multiplyExact(term1.numerator, term2.denominator);
            final long bc = Math.multiplyExact(term1.denominator, term2.numerator);
            final long bd = Math.multiplyExact(term1.denominator, term2.denominator);
            final long adPlusBc = Math.addExact(ad, bc);

            result = simplifyRational(new Rational(adPlusBc, bd));
        } catch (final ArithmeticException ex) {
            final BigInteger n1 = BigInteger.valueOf(term1.numerator);
            final BigInteger d1 = BigInteger.valueOf(term1.denominator);
            final BigInteger n2 = BigInteger.valueOf(term2.numerator);
            final BigInteger d2 = BigInteger.valueOf(term2.denominator);

            final BigInteger n1d2 = n1.multiply(d2);
            final BigInteger d1n2 = d1.multiply(n2);
            final BigInteger d1d2 = d1.multiply(d2);
            final BigInteger newNumer = n1d2.add(d1n2);

            result = simplifyBigRational(new BigRational(newNumer, d1d2));
        }

        return result;
    }

    /**
     * Computes the product of a {code Rational}  and a {code BigRational} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number rationalPlusBigRational(final Rational term1, final BigRational term2) {

        // A/B + C/D = (AD + BC) / (BD)

        final BigInteger n1 = BigInteger.valueOf(term1.numerator);
        final BigInteger d1 = BigInteger.valueOf(term1.denominator);

        final BigInteger n1d2 = n1.multiply(term2.denominator);
        final BigInteger d1n2 = d1.multiply(term2.numerator);
        final BigInteger d1d2 = d1.multiply(term2.denominator);
        final BigInteger newNumer = n1d2.add(d1n2);

        return simplifyBigRational(new BigRational(newNumer, d1d2));
    }

    /**
     * Computes the product of a {code Rational}  and a {code BigInteger} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number rationalPlusBigInteger(final Rational term1, final BigInteger term2) {

        // A/B + C = (A + BC) / B

        final BigInteger n1 = BigInteger.valueOf(term1.numerator);
        final BigInteger d1 = BigInteger.valueOf(term1.denominator);

        final BigInteger bc = d1.multiply(term2);
        final BigInteger newNumer = n1.add(bc);

        return simplifyBigRational(new BigRational(newNumer, d1));
    }

    /**
     * Computes the product of two {code BigRational} terms, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigRationalPlusBigRational(final BigRational term1, final BigRational term2) {

        // A/B + C/D = (AD + BC) / (BD)

        final BigInteger n1d2 = term1.numerator.multiply(term2.denominator);
        final BigInteger d1n2 = term1.denominator.multiply(term2.numerator);
        final BigInteger d1d2 = term1.denominator.multiply(term2.denominator);
        final BigInteger newNumer = n1d2.add(d1n2);

        return simplifyBigRational(new BigRational(newNumer, d1d2));
    }

    /**
     * Computes the product of a {code BigRational}  and a {code BigInteger} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigRationalPlusBigInteger(final BigRational term1, final BigInteger term2) {

        // A/B + C = (A + BC) / B

        final BigInteger d1n2 = term1.denominator.multiply(term2);
        final BigInteger newNumer = term1.numerator.add(term1.denominator);

        return simplifyBigRational(new BigRational(newNumer, term1.denominator));
    }

    /**
     * Computes the product of a {code BigInteger} and a {code BigDecimal} term, without risk of overflow.
     *
     * @param term1 the first term
     * @param term2 the second term
     * @return the product
     */
    public static Number bigIntegerPlusBigDecimal(final BigInteger term1, final BigDecimal term2) {

        final BigDecimal sum = new BigDecimal(term1).add(term2);

        return simplifyBigDecimal(sum);
    }
}
