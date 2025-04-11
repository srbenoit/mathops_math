package dev.mathops.math.expression;

import java.util.Locale;

/**
 * A function of two real arguments.
 */
public class FunctionOf2 extends AbstractFunction {

    /** The function. */
    private final EFunctionOf2 function;

    /** The first argument. */
    private final Expr argument1;

    /** The second argument. */
    private final Expr argument2;

    /**
     * Constructs a new synthetic {@code FunctionOf2}.
     *
     * @param theFunction the function
     * @param theArg1     the argument1
     * @param theArg2     the argument2
     */
    public FunctionOf2(final EFunctionOf2 theFunction, final Expr theArg1, final Expr theArg2) {

        super(0, 0);

        this.function = theFunction;
        this.argument1 = theArg1;
        this.argument2 = theArg2;
    }

    /**
     * Constructs a new {@code FunctionOf2}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theFunction      the function
     * @param theArg1          the argument1
     * @param theArg2          the argument2
     */
    public FunctionOf2(final int theStartPosition, final int theLength, final EFunctionOf2 theFunction,
                       final Expr theArg1, final Expr theArg2) {

        super(theStartPosition, theLength);

        this.function = theFunction;
        this.argument1 = theArg1;
        this.argument2 = theArg2;
    }

    /**
     * Constructs a new synthetic 'atan2' {@code FunctionOf2}.
     *
     * @param theArg1 the first argument
     * @param theArg2 the second argument
     * @return the function
     */
    public static FunctionOf2 atan2(final Expr theArg1, final Expr theArg2) {

        return new FunctionOf2(EFunctionOf2.ATAN2, theArg1, theArg2);
    }

    /**
     * Constructs a new synthetic 'hypot' {@code FunctionOf2}.
     *
     * @param theArg1 the first argument
     * @param theArg2 the second argument
     * @return the function
     */
    public static FunctionOf2 hypot(final Expr theArg1, final Expr theArg2) {

        return new FunctionOf2(EFunctionOf2.HYPOT, theArg1, theArg2);
    }

    /**
     * Constructs a new synthetic 'pow' {@code FunctionOf2}.
     *
     * @param theArg1 the first argument
     * @param theArg2 the second argument
     * @return the function
     */
    public static FunctionOf2 pow(final Expr theArg1, final Expr theArg2) {

        return new FunctionOf2(EFunctionOf2.POW, theArg1, theArg2);
    }

    /**
     * Tests whether the function is constant with respect to a given variable name.
     *
     * @param varName the variable name
     * @return true if the function is constant with respect to the variable
     */
    public final boolean isConstantWithRespectTo(final String varName) {

        return this.argument1.isConstantWithRespectTo(varName) && this.argument2.isConstantWithRespectTo(varName);
    }

    /**
     * Tests whether the function is constant (has no variable references).
     *
     * @return true if the function is constant
     */
    public final boolean isConstant() {

        return this.argument1.isConstant() && this.argument2.isConstant();
    }

    /**
     * Gets the function.
     *
     * @return the function
     */
    public final EFunctionOf2 getFunction() {

        return this.function;
    }

    /**
     * Gets the first argument.
     *
     * @return the first argument
     */
    public final Expr getArgument1() {

        return this.argument1;
    }

    /**
     * Gets the second argument.
     *
     * @return the second argument
     */
    public final Expr getArgument2() {

        return this.argument2;
    }

    /**
     * Evaluates the function.
     *
     * @param variables variable values
     * @return the result; null if unable to evaluate
     */
    @Override
    public final Number eval(final VariableValues variables) {

        final Number value;

        final Number eval1Out = this.argument1.eval(variables);
        final Number eval2Out = this.argument2.eval(variables);

        if (eval1Out == null || eval2Out == null) {
            value = null;
        } else {
            final double arg1Val = eval1Out.doubleValue();
            final double arg2Val = eval2Out.doubleValue();

            value = switch (this.function) {
                case ATAN2 -> Double.valueOf(Math.atan2(arg1Val, arg2Val));
                case HYPOT -> Double.valueOf(Math.hypot(arg1Val, arg2Val));
                case POW -> Double.valueOf(Math.pow(arg1Val, arg2Val));
            };
        }

        return value;
    }

    /**
     * Generates the derivative of this factor with respect to a given variable.
     *
     * @param varName the name of the variable with respect to which to differentiate
     * @return the derivative
     */
    @Override
    public final Expr differentiate(final String varName) {

        final Expr arg1Diff = this.argument1.differentiate(varName);
        final Expr arg2Diff = this.argument2.differentiate(varName);
        final Factor arg1Fact = new Factor(this.argument1);
        final Factor arg2Fact = new Factor(this.argument2);
        final Factor arg1DiffFact = new Factor(arg1Diff);
        final Factor arg2DiffFact = new Factor(arg2Diff);

        final Expr result;

        switch (this.function) {
            // diff(atan(arg1/arg2)) = (arg1 * diff(arg2) - arg2 * diff(arg1)) / (arg1^1 + arg2^2)
            case ATAN2 -> {
                final Expr numer1 = new Expr(new Term(arg1Fact, ExpressionTokens.TimesDivCaretTok.TIMES,
                        new Factor(arg2Diff)), new Term(ExpressionTokens.PlusMinusTok.MINUS, arg2Fact,
                        ExpressionTokens.TimesDivCaretTok.TIMES, arg1DiffFact), false);
                final Expr denom1 = new Expr(new Term(arg1Fact, ExpressionTokens.TimesDivCaretTok.TIMES, arg1Fact),
                        new Term(arg2Fact, ExpressionTokens.TimesDivCaretTok.TIMES, arg2Fact), false);
                result = new Expr(new Term(new Factor(numer1), ExpressionTokens.TimesDivCaretTok.DIV,
                        new Factor(denom1)), false).simplify();
            }

            // diff(hypot(arg1, arg2)) = (arg1 * diff(arg1) + arg2 * diff(arg2)) / hypot(arg1, arg2)
            case HYPOT -> {
                final Expr numer2 = new Expr(new Term(arg1Fact, ExpressionTokens.TimesDivCaretTok.TIMES,
                        new Factor(arg1Diff)), new Term(arg2Fact, ExpressionTokens.TimesDivCaretTok.TIMES,
                        arg2DiffFact), false);
                result = new Expr(new Term(new Factor(numer2), ExpressionTokens.TimesDivCaretTok.DIV,
                        new Factor(this)), false).simplify();
            }

            // diff(pow(arg1, arg2)) = pow(arg1, arg2) * [ arg2 * diff(arg1) / arg1 + diff(arg2) * ln(arg1) ]
            case POW -> {
                if (this.argument2.isConstant()) {

                    // Ordinary power rule
                    // diff(pow(arg1, arg2)) = arg2 * pow(arg1, arg2 - 1)
                    final Term t1 = new Term(new Factor(this.argument2));
                    final Term t2 = new Term(ExpressionTokens.PlusMinusTok.MINUS, new Factor(1.0));
                    final Expr arg2Minus1 = new Expr(t1, t2, false).simplify();

                    final Factor factor1 = new Factor(this.argument2);
                    final Factor factor2 = new Factor(new FunctionOf2(EFunctionOf2.POW, this.argument1, arg2Minus1));
                    final Term term = new Term(ExpressionTokens.PlusMinusTok.PLUS, factor1,
                            ExpressionTokens.TimesDivCaretTok.TIMES, factor2);
                    result = new Expr(term, false).simplify();
                } else {
                    final Expr inner = new Expr(new Term(arg2Fact, ExpressionTokens.TimesDivCaretTok.TIMES,
                            arg1DiffFact, ExpressionTokens.TimesDivCaretTok.DIV, arg1Fact), new Term(arg2DiffFact,
                            ExpressionTokens.TimesDivCaretTok.TIMES, new Factor(FunctionOf1.log(this.argument1))),
                            false);
                    result = new Expr(new Term(new Factor(this), ExpressionTokens.TimesDivCaretTok.TIMES,
                            new Factor(inner)), false).simplify();
                }
            }

            default -> result = null;
        }

        return result;
    }

    /**
     * Attempts to simplify the function.
     *
     * @return the simplified function
     */
    public final Factor simplify() {

        final Expr arg1 = this.argument1.simplify();
        final Expr arg2 = this.argument2.simplify();

        Factor result;

        if (this.function == EFunctionOf2.POW) {
            if (arg2.isConstant()) {
                final double arg2Value = arg2.eval(null).doubleValue();

                if (arg2Value == 0.0) {
                    // FIXME: this yields 1.0 for "POW(0,0)".
                    result = new Factor(Double.valueOf(1.0));
                } else if (arg2Value == 1.0) {
                    result = new Factor(arg1);
                } else {
                    result = new Factor(new FunctionOf2(this.function, arg1, arg2));
                }
            } else {
                result = new Factor(new FunctionOf2(this.function, arg1, arg2));
            }
        } else {
            result = new Factor(new FunctionOf2(this.function, arg1, arg2));
        }

//        Log.info("Simplified " + this + " to " + result);

        return result;
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        return this.function.name().toLowerCase(Locale.ROOT) + '(' + this.argument1 + ',' + this.argument2 + ')';
    }
}
