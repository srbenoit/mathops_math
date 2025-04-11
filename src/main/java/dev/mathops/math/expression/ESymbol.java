package dev.mathops.math.expression;

/** Symbols that represent real number values. */
public enum ESymbol {

    /** Pi. */
    PI(Math.PI);

    /** The value as a {@code Double}. */
    public final double value;

    /**
     * Constructs a new {@code ESymbol}.
     *
     * @param theValue the value as a {@code Double}
     */
    ESymbol(final double theValue) {

        this.value = theValue;
    }
}
