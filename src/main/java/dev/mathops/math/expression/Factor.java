package dev.mathops.math.expression;

import dev.mathops.math.NumberUtils;
import dev.mathops.text.lexparse.AbstractProduction;

/**
 * A factor in a term.
 */
public class Factor extends AbstractProduction {

    /** The parenthesized inner expression. */
    private final Expr parenthesized;

    /** The real-valued function of a real argument. */
    private final FunctionOf1 functionOf1;

    /** The real-valued function of two real arguments. */
    private final FunctionOf2 functionOf2;

    /** The number. */
    private final Number number;

    /** The real-valued variable name. */
    private final String varName;

    /** The real-valued symbol. */
    private final ESymbol symbol;

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theParenthesized the parenthesized inner expression
     */
    public Factor(final int theStartPosition, final int theLength, final Expr theParenthesized) {

        super(theStartPosition, theLength);

        this.parenthesized = theParenthesized;
        this.functionOf1 = null;
        this.functionOf2 = null;
        this.number = null;
        this.varName = null;
        this.symbol = null;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theParenthesized the parenthesized inner expression
     */
    public Factor(final Expr theParenthesized) {

        this(0, 0, theParenthesized);
    }

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theRFunctionR    the real-valued function of a real argument
     */
    public Factor(final int theStartPosition, final int theLength, final FunctionOf1 theRFunctionR) {

        super(theStartPosition, theLength);

        this.parenthesized = null;
        this.functionOf1 = theRFunctionR;
        this.functionOf2 = null;
        this.number = null;
        this.varName = null;
        this.symbol = null;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theRFunctionR the real-valued function of a real argument
     */
    public Factor(final FunctionOf1 theRFunctionR) {

        this(0, 0, theRFunctionR);
    }

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theRFunctionRR   the real-valued function of two real arguments
     */
    public Factor(final int theStartPosition, final int theLength, final FunctionOf2 theRFunctionRR) {

        super(theStartPosition, theLength);

        this.parenthesized = null;
        this.functionOf1 = null;
        this.functionOf2 = theRFunctionRR;
        this.number = null;
        this.varName = null;
        this.symbol = null;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theRFunctionRR the real-valued function of two real arguments
     */
    public Factor(final FunctionOf2 theRFunctionRR) {

        this(0, 0, theRFunctionRR);
    }

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theNumber        the number value
     */
    public Factor(final int theStartPosition, final int theLength, final Number theNumber) {

        super(theStartPosition, theLength);

        this.parenthesized = null;
        this.functionOf1 = null;
        this.functionOf2 = null;
        this.number = theNumber;
        this.varName = null;
        this.symbol = null;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theNumber the number value
     */
    public Factor(final Number theNumber) {

        this(0, 0, theNumber);
    }

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theVarName       the name of the integer-valued variable
     */
    public Factor(final int theStartPosition, final int theLength, final String theVarName) {

        super(theStartPosition, theLength);

        this.parenthesized = null;
        this.functionOf1 = null;
        this.functionOf2 = null;
        this.number = null;
        this.varName = theVarName;
        this.symbol = null;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theVarName the name of the integer-valued variable
     */
    public Factor(final String theVarName) {

        this(0, 0, theVarName);
    }

    /**
     * Constructs a new {@code Factor}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theSymbol        the symbol
     */
    public Factor(final int theStartPosition, final int theLength, final ESymbol theSymbol) {

        super(theStartPosition, theLength);

        this.parenthesized = null;
        this.functionOf1 = null;
        this.functionOf2 = null;
        this.number = null;
        this.varName = null;
        this.symbol = theSymbol;
    }

    /**
     * Constructs a new synthetic {@code Factor}.
     *
     * @param theSymbol the symbol
     */
    public Factor(final ESymbol theSymbol) {

        this(0, 0, theSymbol);
    }

    /**
     * Gets the parenthesized expression.
     *
     * @return the expression; {code null} if this factor is not a parenthesized expression
     */
    public final Expr getParenthesized() {

        return this.parenthesized;
    }

    /**
     * Gets the function of one argument.
     *
     * @return the function; {code null} if this factor is not a function of one argument
     */
    public final FunctionOf1 getFunctionOf1() {

        return this.functionOf1;
    }

    /**
     * Gets the function of two arguments.
     *
     * @return the function; {code null} if this factor is not a function of two arguments
     */
    public final FunctionOf2 getFunctionOf2() {

        return this.functionOf2;
    }

    /**
     * Gets the number value.
     *
     * @return the number value; {code null} if this factor is not a number value
     */
    public final Number getNumber() {

        return this.number;
    }

    /**
     * Gets the variable name.
     *
     * @return the variable name; {code null} if this factor is not a variable reference
     */
    public final String getVarName() {

        return this.varName;
    }

    /**
     * Gets the symbol.
     *
     * @return the symbol; {code null} if this factor is not a symbol
     */
    public final ESymbol getSymbol() {

        return this.symbol;
    }

    /**
     * Tests whether the factor is constant with respect to a given variable name.
     *
     * @param theVarName the variable name
     * @return true if the factor is constant with respect to the variable
     */
    public final boolean isConstantWithRespectTo(final String theVarName) {

        final boolean constant;

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    if (this.varName == null || this.number != null) {
                        constant = true;
                    } else {
                        constant = !this.varName.equals(theVarName);
                    }
                } else {
                    constant = this.functionOf2.isConstantWithRespectTo(theVarName);
                }
            } else {
                constant = this.functionOf1.isConstantWithRespectTo(theVarName);
            }
        } else {
            constant = this.parenthesized.isConstantWithRespectTo(theVarName);
        }

        return constant;
    }

    /**
     * Tests whether the factor is constant (has no variable references).
     *
     * @return true if the factor is constant
     */
    public final boolean isConstant() {

        final boolean constant;

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    // must be variable or number or symbol (first is not constant, latter two are)
                    constant = this.varName == null;
                } else {
                    constant = this.functionOf2.isConstant();
                }
            } else {
                constant = this.functionOf1.isConstant();
            }
        } else {
            constant = this.parenthesized.isConstant();
        }

        return constant;
    }

    /**
     * Evaluates the factor.
     *
     * @param variables variable values
     * @return the result; null if unable to evaluate
     */
    public final Number eval(final VariableValues variables) {

        Number value = null;

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    if (this.number == null) {
                        if (this.varName == null) {
                            if (this.symbol != null) {
                                value = Double.valueOf(this.symbol.value);
                            }
                        } else {
                            value = variables.get(this.varName);
                        }
                    } else {
                        value = this.number;
                    }
                } else {
                    value = this.functionOf2.eval(variables);
                }
            } else {
                value = this.functionOf1.eval(variables);
            }
        } else {
            value = this.parenthesized.eval(variables);
        }

        return value;
    }

    /**
     * Generates the derivative of this factor with respect to a given variable.
     *
     * @param theVarName the name of the variable with respect to which to differentiate
     * @return the derivative
     */
    public final Factor differentiate(final String theVarName) {

        final Factor result;

//        Log.info("    Differentiating factor ", this);

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    if (this.varName != null && this.varName.equals(theVarName)) {
                        result = new Factor(1);
                    } else {
                        result = new Factor(0);
                    }
                } else {
                    result = new Factor(this.functionOf2.differentiate(theVarName));
                }
            } else {
                result = new Factor(this.functionOf1.differentiate(theVarName));
            }
        } else {
            result = new Factor(this.parenthesized.differentiate(theVarName));
        }

        return result;
    }

    /**
     * Attempts to simplify the factor.
     *
     * @return the simplified factor
     */
    public final Factor simplify() {

        final Factor result;

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    // Other types do not simplify further
                    result = this;
                } else {
                    result = this.functionOf2.simplify();
                }
            } else {
                result = this.functionOf1.simplify();
            }
        } else {
            final Expr inner = this.parenthesized.simplify();

            // If inner expression has one term with one factor, return that factor with the inner term's sign applied
            if (inner.getNumTerms() == 1) {
                final Term innerTerm = inner.getTerm(0);

                if (innerTerm.getNumFactors() == 1) {
                    final ExpressionTokens.PlusMinusTok op = innerTerm.getSign();
                    final Factor innerFactor = innerTerm.getFactor(0);

                    if ((int) op.op == '-') {
                        if (innerFactor.parenthesized == null) {
                            if (innerFactor.number == null) {
                                result = new Factor(inner);
                            } else {
                                result = new Factor(NumberUtils.negate(innerFactor.number));
                            }
                        } else {
                            result = new Factor(innerFactor.parenthesized.negate());
                        }
                    } else {
                        result = innerFactor;
                    }
                } else {
                    result = new Factor(inner);
                }
            } else {
                result = new Factor(inner);
            }
        }

//        Log.info("Factor ", this, " simplified to ", result);

        return result;
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(50);

        if (this.parenthesized == null) {
            if (this.functionOf1 == null) {
                if (this.functionOf2 == null) {
                    if (this.number == null) {
                        if (this.varName == null) {
                            if (this.symbol == null) {
                                sb.append("{NULL}");
                            } else {
                                sb.append(this.symbol.name());
                            }
                        } else {
                            sb.append(this.varName);
                        }
                    } else {
                        final String numString = this.number.toString();
                        if (numString.endsWith(".0")) {
                            sb.append(numString, 0, numString.length() - 2);
                        } else {
                            sb.append(numString);
                        }
                    }
                } else {
                    sb.append(this.functionOf2);
                }
            } else {
                sb.append(this.functionOf1);
            }
        } else {
            sb.append('(');
            sb.append(this.parenthesized);
            sb.append(')');
        }

        return sb.toString();
    }
}
