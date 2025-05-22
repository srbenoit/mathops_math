package dev.mathops.math;

import dev.mathops.commons.number.BigIrrational;
import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.EIrrationalFactor;
import dev.mathops.commons.number.Irrational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utilities for working with numbers.  Supported number types include {@code Byte}, {@code Short}, {@code Integer},
 * {@code Long}, {@code Float}, {@code Double}, {@code BigInteger}, {@code BigDecimal}, {@code Rational},
 * {@code BigRational}, {@code Irrational}, and {@code BigIrrational}.  Any unsupported type passed will be converted to
 * their double value and treated as a {@code Double}.
 */
public enum NumberUtils {
    ;

    /**
     * Negates a number.
     *
     * @param toNegate the number to negate
     * @return the negated number
     */
    public static Number negate(final Number toNegate) {

        Number result = null;

        if (toNegate != null) {
            switch (toNegate) {
                case final Long l -> {
                    final long value = l.longValue();
                    result = Long.valueOf(-value);
                }
                case final Integer i -> {
                    final int value = i.intValue();
                    result = Integer.valueOf(-value);
                }
                case final Short sh -> {
                    final int value = sh.intValue();
                    result = Short.valueOf((short) -value);
                }
                case final Byte b -> {
                    final int value = b.intValue();
                    result = Byte.valueOf((byte) -value);
                }
                case final Float f -> {
                    final float value = f.floatValue();
                    result = Float.valueOf(-value);
                }
                case final Double d -> {
                    final double value = d.doubleValue();
                    result = Double.valueOf(-value);
                }
                case final BigInteger bi -> result = bi.negate();
                case final BigDecimal bd -> result = bd.negate();
                case final Rational r -> result = new Rational(-r.numerator, r.denominator);
                case final BigRational r -> {
                    final BigInteger num = r.numerator.negate();
                    result = new BigRational(num, r.denominator);
                }
                case final Irrational irr ->
                        result = new Irrational(irr.factor, irr.base, -irr.numerator, irr.denominator);
                case final BigIrrational irr -> {
                    final BigInteger num = irr.numerator.negate();
                    result = new BigIrrational(irr.factor, irr.base, num, irr.denominator);
                }
                default -> {
                    final double negated = -toNegate.doubleValue();
                    result = Double.valueOf(negated);
                }
            }
        }

        return result;
    }

    /**
     * Returns the signum of a number, -1 if the number is negative, 0 if the number is zero, and +1 if the number is
     * positive.
     *
     * @param number the number to test
     * @return the signum
     */
    public static int signum(final Number number) {

        final int result;

        if (number instanceof Integer || number instanceof Short || number instanceof Byte) {
            final int v = number.intValue();
            result = Integer.signum(v);
        } else if (number instanceof final Long l) {
            final long v = l.longValue();
            result = Long.signum(v);
        } else if (number instanceof final Float f) {
            final float v = f.floatValue();
            result = Float.compare(v, 0.0f);
        } else if (number instanceof final Double d) {
            final double v = d.doubleValue();
            result = Double.compare(v, 0.0);
        } else if (number instanceof final Rational r) {
            // Numerator carries sign
            result = Long.signum(r.numerator);
        } else if (number instanceof final BigRational br) {
            // Numerator carries sign
            result = br.numerator.signum();
        } else if (number instanceof final BigInteger bi) {
            result = bi.signum();
        } else if (number instanceof final BigDecimal bd) {
            result = bd.signum();
        } else if (number instanceof final Irrational ir) {
            // Numerator carries sign
            result = Long.signum(ir.numerator);
        } else if (number instanceof final BigIrrational bir) {
            // Numerator carries sign
            result = bir.numerator.signum();
        } else {
            // Unexpected type, default to its double value
            final double v = number.doubleValue();
            result = Double.compare(v, 0.0);
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

        return switch (number) {
            case final Integer i -> 0 == i.intValue();
            case final Short sh -> 0 == sh.intValue();
            case final Byte b -> 0 == b.intValue();
            case final Long l -> 0L == l.longValue();
            case final BigInteger bi -> BigInteger.ZERO.equals(bi);
            case final BigDecimal bd -> 0 == BigDecimal.ZERO.compareTo(bd);
            case final Rational r -> 0L == r.numerator;
            case final BigRational br -> BigInteger.ZERO.equals(br.numerator);
            case final Irrational irr -> 0L == irr.numerator;
            case final BigIrrational irr -> BigInteger.ZERO.equals(irr.numerator);
            default -> 0.0 == number.doubleValue();
        };
    }

    /**
     * Tests whether a number is one.
     *
     * @param number the number to test
     * @return true if the number is zero
     */
    public static boolean isOne(final Number number) {

        return switch (number) {
            case final Integer i -> 1 == i.intValue();
            case final Short sh -> 1 == sh.intValue();
            case final Byte b -> 1 == b.intValue();
            case final Long l -> l.longValue() == 1L;
            case final BigInteger bi -> BigInteger.ONE.equals(bi);
            case final BigDecimal bd -> 0 == BigDecimal.ONE.compareTo(bd);
            case final Rational r -> r.numerator == 1L && r.denominator == 1L;
            case final BigRational br -> BigInteger.ONE.equals(br.numerator) && BigInteger.ONE.equals(br.denominator);
            case final Irrational irr -> irr.factor == EIrrationalFactor.SQRT && irr.base == 1L
                                         && irr.numerator == 1L && irr.denominator == 1L;
            case final BigIrrational irr -> irr.factor == EIrrationalFactor.SQRT && irr.base == 1L
                                            && BigInteger.ONE.equals(irr.numerator)
                                            && BigInteger.ONE.equals(irr.denominator);
            default -> number.doubleValue() == 1.0;
        };
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
     * Checks a {@code BigInteger} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return a {@code BigInteger}, {@code Long}, or {@code Integer} that can contain the value without overflow
     */
    public static Number simplifyBigInteger(final BigInteger value) {

        Number result;

        try {
            final long l1 = value.longValueExact();
            if (l1 > (long) Integer.MAX_VALUE || l1 < (long) Integer.MIN_VALUE) {
                result = Long.valueOf(l1);
            } else {
                result = Integer.valueOf((int) l1);
            }
        } catch (final ArithmeticException ex) {
            result = value;
        }

        return result;
    }

    /**
     * Checks a {code Rational} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return a {@code Rational}, {@code Long}, or {@code Integer} that can contain the value without overflow
     */
    public static Number simplifyRational(final Rational value) {

        final Number result;

        if (value.denominator == 1L) {
            final long l1 = value.numerator;
            if (l1 > (long) Integer.MAX_VALUE || l1 < (long) Integer.MIN_VALUE) {
                result = Long.valueOf(l1);
            } else {
                result = Integer.valueOf((int) l1);
            }
        } else {
            result = value;
        }

        return result;
    }

    /**
     * Checks a {code BigRational} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return a {@code BigRational}, {@code Rational}, {@code BigInteger}, {@code Long}, or {@code Integer} that can
     *         contain the value without overflow
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
            } catch (final ArithmeticException ex) {
                result = value;
            }
        }

        return result;
    }

    /**
     * Checks a {@code BigDecimal} to see if it can fit without overflow into a smaller type.
     *
     * @param value the value to test
     * @return A {@code BigDecimal}, {@code BigInteger}, {@code Long}, or {@code Integer} that can contain the value
     *         without overflow
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
