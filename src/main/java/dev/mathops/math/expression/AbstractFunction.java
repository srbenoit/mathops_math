package dev.mathops.math.expression;

import dev.mathops.text.lexparse.AbstractProduction;

/**
 * A base class for functions.
 */
public abstract class AbstractFunction extends AbstractProduction {

    /** The natural log of 2. */
    public static final double LN2 = Math.log(2);

    /**
     * Constructs a new {@code AbstractFunction}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength the number of tokens in the source string that were matched to produce this production
     */
    protected AbstractFunction(final int theStartPosition, final int theLength) {

        super(theStartPosition, theLength);
    }

    /**
     * Evaluates the function.
     *
     * @param variables variable values
     * @return the result; null if unable to evaluate
     */
    public abstract Number eval(VariableValues variables);

    /**
     * Generates the derivative of the function with respect to a given variable.
     *
     * @param varName the name of the variable with respect to which to differentiate
     * @return the derivative
     */
    public abstract Expr differentiate(String varName);
}
