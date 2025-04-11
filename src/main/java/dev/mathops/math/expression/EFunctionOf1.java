package dev.mathops.math.expression;

/** Built-in real-valued functions of one real argument. */
public enum EFunctionOf1 {

    /** abs(number). */
    ABS("abs"),

    /** acos(number). */
    ACOS("acos"),

    /** asin(number). */
    ASIN("asin"),

    /** atan(number). */
    ATAN("atan"),

    /** cbrt(number). */
    CBRT("cbrt"),

    /** cos(number). */
    COS("cos"),

    /** exp(number). */
    EXP("exp"),

    /** expm1(number). */
    EXPM1("expm1"),

    /** log(number). */
    LOG("log"),

    /** log1p(number). */
    LOG1P("log1p"),

    /** log10(number). */
    LOG10("log10"),

    /** log2(number). */
    LOG2("log2"),

    /** sin(number). */
    SIN("sin"),

    /** sqrt(number). */
    SQRT("sqrt"),

    /** tan(number). */
    TAN("tan"),

    /** toDeg(number). */
    TO_DEG("toDeg"),

    /** toRad(number). */
    TO_RAD("toRad");

    /** The function label. */
    public final String label;

    /**
     * Constructs a new {@code EFunctionOf1}.
     *
     * @param theLabel the label
     */
    EFunctionOf1(final String theLabel) {

        this.label = theLabel;
    }
}
