package dev.mathops.math;

import dev.mathops.commons.number.BigIrrational;
import dev.mathops.commons.number.BigRational;
import dev.mathops.commons.number.EIrrationalFactor;
import dev.mathops.commons.number.Irrational;
import dev.mathops.commons.number.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Utility method to test whether two {@code Number} values are equal, or to determine which is lesser and which is
 * greater.  Note that NaN values are not supported, and this class will assume any floating-point arguments are not
 * NaN.
 *
 * <p>
 * This class supports {@code Byte}, {@code Short}, {@code Int}, {@code Long}, {@code Float}, {@code Double},
 * {@code BigInteger}, {@code BigDecimal}, {@code Rational}, {@code BigRational}, {@code Irrational}, and
 * {@code BigIrrational} values.
 */
public final class NumberComparator implements Comparator<Number> {

    /** The single instance. */
    public static final NumberComparator INSTANCE = new NumberComparator();

    /**
     * Private constructor to prevent instantiation.
     */
    private NumberComparator() {

        // No action
    }

    /**
     * Tests whether two numbers are numerically equal.
     *
     * @param o1 the first number
     * @param o2 the second number
     * @return {@code true} if the numbers are numerically equal; {@code false} if not
     */
    public boolean isNumericallyEqual(final Number o1, final Number o2) {

        return compare(o1, o2) == 0;
    }

    /**
     * Compares its two arguments for order. Returns -1, 0, or 1 as the first argument is numerically less than, equal
     * to, or greater than the second.
     *
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     * @throws NullPointerException if an argument is {@code null}
     */
    @Override
    public int compare(final Number o1, final Number o2) {

        final int result;

        final int sign1 = NumberUtils.signum(o1);
        final int sign2 = NumberUtils.signum(o2);

        if (sign1 == sign2) {
            if (sign1 == 0) {
                // Both values are zero
                result = 0;
            } else if (sign1 > 0) {
                // Both values are positive and nonzero
                result = comparePositive(o1, o2);
            } else {
                // Both values are negative and nonzero - make them both positive to compare
                final Number pos1 = NumberUtils.negate(o1);
                final Number pos2 = NumberUtils.negate(o2);
                result = -comparePositive(pos1, pos2);
            }
        } else {
            result = sign1 > sign2 ? 1 : -1;
        }

        return result;
    }

    /**
     * Compares its two positive nonzero arguments for order. Returns -1, 0, or 1 as the first argument is numerically
     * less than, equal to, or greater than the second.
     *
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     * @throws NullPointerException if an argument is {@code null}
     */
    private static int comparePositive(final Number o1, final Number o2) {

        final int result;

        if ((o1 instanceof Integer || o1 instanceof Long || o1 instanceof Short || o1 instanceof Byte)
            && (o2 instanceof Integer || o2 instanceof Long || o2 instanceof Short || o2 instanceof Byte)) {
            final long v1 = o1.longValue();
            final long v2 = o2.longValue();
            result = Long.compare(v1, v2);
        } else if (o1 instanceof Float && o2 instanceof Float) {
            final float v1 = o1.floatValue();
            final float v2 = o2.floatValue();
            result = Float.compare(v1, v2);
        } else if ((o1 instanceof Double || o1 instanceof Float)
                   && (o2 instanceof Double || o2 instanceof Float)) {
            final double v1 = o1.doubleValue();
            final double v2 = o2.doubleValue();
            result = Double.compare(v1, v2);
        } else if (o1 instanceof final BigInteger big1 && o2 instanceof final BigInteger big2) {
            result = big1.compareTo(big2);
        } else if (o1 instanceof final BigDecimal big1 && o2 instanceof final BigDecimal big2) {
            result = big1.compareTo(big2);
        } else {
            // More complex comparison - convert both numbers to a rational or irrational to facilitate comparison
            final Number num1 = RationalConverter.toRationalOrIrrational(o1);
            final Number num2 = RationalConverter.toRationalOrIrrational(o2);

            result = switch (num1) {
                case final Rational rat1 -> comparePositiveRational(rat1, num2);
                case final BigRational rat1 -> comparePositiveBigRational(rat1, num2);
                case final Irrational irr1 -> comparePositiveIrrational(irr1, num2);
                case final BigIrrational irr1 -> comparePositiveBigIrrational(irr1, num2);
                case null, default ->
                        throw new IllegalArgumentException("Unexpected number type from RationalConverter");
            };
        }

        return result;
    }

    /**
     * Compares a positive {@code Rational} to a positive number that is either a {@code Rational}, {@code BigRational},
     * {@code Irrational}, or {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int comparePositiveRational(final Rational v1, final Number o2) {

        final int result;

        switch (o2) {
            case final Rational v2 -> result = compRationalRational(v1, v2);
            case final BigRational v2 -> result = compRationalBigRational(v1, v2);
            case final Irrational v2 -> result = compRationalIrrational(v1, v2);
            case final BigIrrational v2 -> result = compRationalBigIrrational(v1, v2);
            case null, default -> {
                final String msg = Res.get(Res.BAD_TYPE_RATIONAL_CONVERT);
                throw new IllegalArgumentException(msg);
            }
        }

        return result;
    }

    /**
     * Compares a positive {@code BigRational} to a positive number that is either a {@code Rational},
     * {@code BigRational}, {@code Irrational}, or {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int comparePositiveBigRational(final BigRational v1, final Number o2) {

        final int result;

        switch (o2) {
            case final Rational v2 -> result = -compRationalBigRational(v2, v1);
            case final BigRational v2 -> result = compBigRationalBigRational(v1, v2);
            case final Irrational v2 -> result = compBigRationalIrrational(v1, v2);
            case final BigIrrational v2 -> result = compBigRationalBigIrrational(v1, v2);
            case null, default -> {
                final String msg = Res.get(Res.BAD_TYPE_RATIONAL_CONVERT);
                throw new IllegalArgumentException(msg);
            }
        }

        return result;
    }

    /**
     * Compares a positive {@code Irrational} to a positive number that is either a {@code Rational},
     * {@code BigRational}, {@code Irrational}, or {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int comparePositiveIrrational(final Irrational v1, final Number o2) {

        final int result;

        switch (o2) {
            case final Rational v2 -> result = -compRationalIrrational(v2, v1);
            case final BigRational v2 -> result = -compBigRationalIrrational(v2, v1);
            case final Irrational v2 -> result = compIrrationalIrrational(v1, v2);
            case final BigIrrational v2 -> result = compIrrationalBigIrrational(v1, v2);
            case null, default -> {
                final String msg = Res.get(Res.BAD_TYPE_RATIONAL_CONVERT);
                throw new IllegalArgumentException(msg);
            }
        }

        return result;
    }

    /**
     * Compares a positive {@code BigIrrational} to a positive number that is either a {@code Rational},
     * {@code BigRational}, {@code Irrational}, or {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param o2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int comparePositiveBigIrrational(final BigIrrational v1, final Number o2) {

        final int result;

        switch (o2) {
            case final Rational v2 -> result = -compRationalBigIrrational(v2, v1);
            case final BigRational v2 -> result = -compBigRationalBigIrrational(v2, v1);
            case final Irrational v2 -> result = -compIrrationalBigIrrational(v2, v1);
            case final BigIrrational v2 -> result = compBigIrrationalBigIrrational(v1, v2);
            case null, default -> {
                final String msg = Res.get(Res.BAD_TYPE_RATIONAL_CONVERT);
                throw new IllegalArgumentException(msg);
            }
        }

        return result;
    }

    /**
     * Compares a positive {@code Rational} to a positive {@code Rational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compRationalRational(final Rational v1, final Rational v2) {

        int result;

        try {
            final long ad = Math.multiplyExact(v1.numerator, v2.denominator);
            final long bc = Math.multiplyExact(v1.denominator, v2.numerator);

            final long adMinusBc = Math.subtractExact(ad, bc);
            result = Long.compare(adMinusBc, 0L);
        } catch (final ArithmeticException ex) {
            final BigInteger n1 = BigInteger.valueOf(v1.numerator);
            final BigInteger d1 = BigInteger.valueOf(v1.denominator);
            final BigInteger n2 = BigInteger.valueOf(v2.numerator);
            final BigInteger d2 = BigInteger.valueOf(v2.denominator);

            final BigInteger n1d2 = n1.multiply(d2);
            final BigInteger d1n2 = d1.multiply(n2);

            final BigInteger n1d2MinusD1n2 = n1d2.subtract(d1n2);
            result = n1d2MinusD1n2.signum();
        }

        return result;
    }

    /**
     * Compares a positive {@code Rational} to a positive {@code BigRational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compRationalBigRational(final Rational v1, final BigRational v2) {

        final BigInteger n1 = BigInteger.valueOf(v1.numerator);
        final BigInteger d1 = BigInteger.valueOf(v1.denominator);
        final BigInteger n1d2 = n1.multiply(v2.denominator);
        final BigInteger d1n2 = d1.multiply(v2.numerator);

        final BigInteger n1d2MinusD1n2 = n1d2.subtract(d1n2);

        return n1d2MinusD1n2.signum();
    }

    /**
     * Compares a positive {@code Rational} to a positive {@code Irrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compRationalIrrational(final Rational v1, final Irrational v2) {

        int result;

        if (v2.factor == EIrrationalFactor.PI) {

            final BigInteger a = BigInteger.valueOf(v1.numerator);
            final BigInteger b = BigInteger.valueOf(v1.denominator);
            final BigInteger c = BigInteger.valueOf(v2.numerator);
            final BigInteger d = BigInteger.valueOf(v2.denominator);

            final BigInteger ad = a.multiply(d);
            final BigInteger bc = b.multiply(c);

            // TODO: Check if ad-3bc is positive (ad is greater), or ad-4bc is negative (second is greater)
            if (ad.subtract(bc.multiply(BigInteger.valueOf(3))).signum() > 0) {
                result = 1;
            } else if (ad.subtract(bc.multiply(BigInteger.valueOf(4))).signum() < 0) {
                result = -1;
            } else {
                result = Double.compare(v1.doubleValue(), v2.doubleValue());
            }

        } else if (v2.factor == EIrrationalFactor.E) {

            final BigInteger a = BigInteger.valueOf(v1.numerator);
            final BigInteger b = BigInteger.valueOf(v1.denominator);
            final BigInteger c = BigInteger.valueOf(v2.numerator);
            final BigInteger d = BigInteger.valueOf(v2.denominator);

            final BigInteger ad = a.multiply(d);
            final BigInteger bc = b.multiply(c);

            // TODO: Check if ad-2bc is positive (ad is greater), or ad-3bc is negative (second is greater)
            if (ad.subtract(bc.multiply(BigInteger.valueOf(2))).signum() > 0) {
                result = 1;
            } else if (ad.subtract(bc.multiply(BigInteger.valueOf(3))).signum() < 0) {
                result = -1;
            } else {
                result = Double.compare(v1.doubleValue(), v2.doubleValue());
            }
        } else {
            // Sqrt
            result = Double.compare(v1.doubleValue(), v2.doubleValue());
        }

        return result;
    }

    /**
     * Compares a positive {@code Rational} to a positive {@code Irrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compRationalBigIrrational(final Rational v1, final BigIrrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }

    /**
     * Compares a positive {@code BigRational} to a positive {@code BigRational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compBigRationalBigRational(final BigRational v1, final BigRational v2) {

        final BigInteger n1d2 = v1.numerator.multiply(v2.denominator);
        final BigInteger d1n2 = v1.denominator.multiply(v2.numerator);

        final BigInteger n1d2MinusD1n2 = n1d2.subtract(d1n2);

        return n1d2MinusD1n2.signum();
    }

    /**
     * Compares a positive {@code BigRational} to a positive {@code Irrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compBigRationalIrrational(final BigRational v1, final Irrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }

    /**
     * Compares a positive {@code BigRational} to a positive {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compBigRationalBigIrrational(final BigRational v1, final BigIrrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }

    /**
     * Compares a positive {@code Irrational} to a positive {@code Irrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compIrrationalIrrational(final Irrational v1, final Irrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }

    /**
     * Compares a positive {@code Irrational} to a positive {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compIrrationalBigIrrational(final Irrational v1, final BigIrrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }

    /**
     * Compares a positive {@code BigIrrational} to a positive {@code BigIrrational}.
     *
     * @param v1 the first object to be compared
     * @param v2 the second object to be compared
     * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second
     */
    private static int compBigIrrationalBigIrrational(final BigIrrational v1, final BigIrrational v2) {

        return Double.compare(v1.doubleValue(), v2.doubleValue());
    }
}
