package dev.mathops.math.expression;

/** Built-in real-valued functions of two real arguments. */
public enum EFunctionOf2 {

    /** atan2(number, number). */
    ATAN2("atan2"),

    /** hypot(number, number). */
    HYPOT("hypot"),

    /** pow(number, number). */
    POW("pow");

    /** The function label. */
    public final String label;

    /**
     * Constructs a new {@code EFunctionOf2}.
     *
     * @param theLabel the label
     */
    EFunctionOf2(final String theLabel) {

        this.label = theLabel;
    }
}
