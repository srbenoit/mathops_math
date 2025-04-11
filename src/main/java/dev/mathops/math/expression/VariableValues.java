package dev.mathops.math.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * A value store for named real-valued variables.
 */
public final class VariableValues {

    /** Values for variables. */
    private final Map<String, Number> values;

    /**
     * Constructs a new {@code VariableValues}.
     */
    public VariableValues() {

        this.values = new HashMap<>(10);
    }

    /**
     * Sets a variable value.
     *
     * @param name  the variable name
     * @param value the variable value (null to un-set)
     */
    public void set(final String name, final Number value) {

        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }

        if (value == null) {
            this.values.remove(name);
        } else {
            this.values.put(name, value);
        }
    }

    /**
     * Gets a variable value.
     *
     * @param name the variable name
     * @return the variable value (null if un-set)
     */
    public Number get(final String name) {

        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }

        return this.values.get(name);
    }
}
