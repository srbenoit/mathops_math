package dev.mathops.math.expression;

import dev.mathops.commons.number.Rational;

import java.util.Locale;

/**
 * A function of a single real argument.
 */
public class FunctionOf1 extends AbstractFunction {

    /** The function. */
    private final EFunctionOf1 function;

    /** The argument. */
    private final Expr argument;

    /**
     * Constructs a new synthetic {@code FunctionOf1}.
     *
     * @param theFunction the function
     * @param theArg      the argument
     */
    public FunctionOf1(final EFunctionOf1 theFunction, final Expr theArg) {

        super(0, 0);

        this.function = theFunction;
        this.argument = theArg;
    }

    /**
     * Constructs a new {@code FunctionOf1}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theFunction      the function
     * @param theArg           the argument
     */
    public FunctionOf1(final int theStartPosition, final int theLength, final EFunctionOf1 theFunction,
                       final Expr theArg) {

        super(theStartPosition, theLength);

        this.function = theFunction;
        this.argument = theArg;
    }

    /**
     * Constructs a new synthetic 'abs' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 abs(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.ABS, theArg);
    }

    /**
     * Constructs a new synthetic 'acos' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 acos(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.ACOS, theArg);
    }

    /**
     * Constructs a new synthetic 'asin' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 asin(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.ASIN, theArg);
    }

    /**
     * Constructs a new synthetic 'atan' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 atan(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.ATAN, theArg);
    }

    /**
     * Constructs a new synthetic 'cbrt' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 cbrt(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.CBRT, theArg);
    }

    /**
     * Constructs a new synthetic 'cos' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 cos(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.COS, theArg);
    }

    /**
     * Constructs a new synthetic 'exp' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 exp(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.EXP, theArg);
    }

    /**
     * Constructs a new synthetic 'expm1' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 expm1(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.EXPM1, theArg);
    }

    /**
     * Constructs a new synthetic 'log' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 log(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.LOG, theArg);
    }

    /**
     * Constructs a new synthetic 'log1p' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 log1p(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.LOG1P, theArg);
    }

    /**
     * Constructs a new synthetic 'log10' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 log10(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.LOG10, theArg);
    }

    /**
     * Constructs a new synthetic 'log2' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 log2(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.LOG2, theArg);
    }

    /**
     * Constructs a new synthetic 'sin' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 sin(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.SIN, theArg);
    }

    /**
     * Constructs a new synthetic 'sqrt' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 sqrt(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.SQRT, theArg);
    }

    /**
     * Constructs a new synthetic 'tan' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 tan(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.TAN, theArg);
    }

    /**
     * Constructs a new synthetic 'toDeg' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 toDeg(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.TO_DEG, theArg);
    }

    /**
     * Constructs a new synthetic 'toRad' {@code FunctionOf1}.
     *
     * @param theArg the argument
     * @return the function
     */
    public static FunctionOf1 toRad(final Expr theArg) {

        return new FunctionOf1(EFunctionOf1.TO_RAD, theArg);
    }

    /**
     * Gets the function.
     *
     * @return the function
     */
    public final EFunctionOf1 getFunction() {

        return this.function;
    }

    /**
     * Gets the argument.
     *
     * @return the argument
     */
    public final Expr getArgument() {

        return this.argument;
    }

    /**
     * Tests whether the function is constant with respect to a given variable name.
     *
     * @param varName the variable name
     * @return true if the function is constant with respect to the variable
     */
    public final boolean isConstantWithRespectTo(final String varName) {

        return this.argument.isConstantWithRespectTo(varName);
    }

    /**
     * Tests whether the function is constant (has no variable references).
     *
     * @return true if the function is constant
     */
    public final boolean isConstant() {

        return this.argument.isConstant();
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

        final Number evalOut = this.argument.eval(variables);

        if (evalOut == null) {
            value = null;
        } else {
            final double argVal = evalOut.doubleValue();

            value = Double.valueOf(switch (this.function) {
                case ABS -> Math.abs(argVal);
                case ACOS -> Math.acos(argVal);
                case ASIN -> Math.asin(argVal);
                case ATAN -> Math.atan(argVal);
                case CBRT -> Math.cbrt(argVal);
                case COS -> Math.cos(argVal);
                case EXP -> Math.exp(argVal);
                case EXPM1 -> Math.expm1(argVal);
                case LOG -> Math.log(argVal);
                case LOG1P -> Math.log1p(argVal);
                case LOG10 -> Math.log10(argVal);
                case LOG2 -> Math.log(argVal) / LN2;
                case SIN -> Math.sin(argVal);
                case SQRT -> Math.sqrt(argVal);
                case TAN -> Math.tan(argVal);
                case TO_DEG -> Math.toDegrees(argVal);
                case TO_RAD -> Math.toRadians(argVal);
            });
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

        final Expr argDiff = this.argument.differentiate(varName);
        final Factor argFact = new Factor(this.argument);
        final Factor argDiffFact = new Factor(argDiff);

        final Expr result;

        switch (this.function) {
            // diff(abs(arg)) = arg * diff(arg) / abs(arg)
            case ABS -> result = new Expr(new Term(argFact, ExpressionTokens.TimesDivCaretTok.TIMES, argDiffFact,
                    ExpressionTokens.TimesDivCaretTok.DIV, new Factor(this)), false).simplify();

            // diff(acos(arg)) = -diff(arg) / sqrt(1 - arg^2)
            case ACOS -> result = new Expr(new Term(ExpressionTokens.PlusMinusTok.MINUS, argDiffFact,
                    ExpressionTokens.TimesDivCaretTok.DIV, new Factor(FunctionOf1.sqrt(new Expr(new Term(1),
                    new Term(ExpressionTokens.PlusMinusTok.MINUS, argFact,
                            ExpressionTokens.TimesDivCaretTok.TIMES, argFact), false)))), false).simplify();

            // diff(asin(arg)) = diff(arg) / sqrt(1 - arg^2)
            case ASIN -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV,
                    new Factor(FunctionOf1.sqrt(new Expr(new Term(1),
                            new Term(ExpressionTokens.PlusMinusTok.MINUS, argFact,
                                    ExpressionTokens.TimesDivCaretTok.TIMES, argFact), false)))), false).simplify();

            // diff(atan(arg)) = diff(arg) / (arg^2 + 1)
            case ATAN -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV,
                    new Factor(new Expr(new Term(argFact, ExpressionTokens.TimesDivCaretTok.TIMES, argFact),
                            new Term(1), false))), false).simplify();

            // diff(cbrt(arg)) = diff(arg) / 3 / pow(arg, 2/3)
            case CBRT -> result = new Expr( //
                    new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, new Factor(3),
                            ExpressionTokens.TimesDivCaretTok.DIV, //
                            new Factor(new FunctionOf2(EFunctionOf2.POW, this.argument,
                                    new Expr(new Term(new Rational(2, 3)), false)))), false).simplify();

            // diff(cos(arg)) = -sin(arg) * diff(arg)
            case COS -> result = new Expr(new Term(ExpressionTokens.PlusMinusTok.MINUS,
                    new Factor(FunctionOf1.sin(this.argument)), ExpressionTokens.TimesDivCaretTok.TIMES,
                    argDiffFact), false).simplify();

            // diff(exp(arg)) = exp(arg) * diff(arg)
            // diff(expm1(arg)) = exp(arg) * diff(arg)
            case EXP, EXPM1 -> result = new Expr(new Term(new Factor(this), ExpressionTokens.TimesDivCaretTok.TIMES,
                    argDiffFact), false).simplify();

            // diff(log(arg)) = diff(arg) / arg
            case LOG -> result =
                    new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, argFact), false).simplify();

            // diff(log1p(arg)) = diff(arg) / (1 + arg)
            case LOG1P -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV,
                    new Factor(new Expr(new Term(1), new Term(argFact), false))), false).simplify();

            // diff(log10(arg)) = diff(arg) / arg / log(10)
            case LOG10 -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, argFact,
                    ExpressionTokens.TimesDivCaretTok.DIV, new Factor(Math.log(10))), false).simplify();

            // diff(log2(arg)) = diff(arg) / arg / log(2)
            case LOG2 -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, argFact,
                    ExpressionTokens.TimesDivCaretTok.DIV, new Factor(Math.log(2))), false).simplify();

            // diff(sin(arg)) = cos(arg) * diff(arg)
            case SIN -> result = new Expr(new Term(new Factor(FunctionOf1.cos(this.argument)),
                    ExpressionTokens.TimesDivCaretTok.TIMES, argDiffFact), false).simplify();

            // diff(sqrt(arg)) = diff(arg) / 2 / sqrt(arg)
            case SQRT -> result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, new Factor(2),
                    ExpressionTokens.TimesDivCaretTok.DIV, new Factor(FunctionOf1.sqrt(this.argument))),
                    false).simplify();

            // diff(tan(arg)) = diff(arg) / cos(arg) / cos(arg)
            case TAN -> {
                final Factor cos = new Factor(FunctionOf1.cos(this.argument));
                result = new Expr(new Term(argDiffFact, ExpressionTokens.TimesDivCaretTok.DIV, cos,
                        ExpressionTokens.TimesDivCaretTok.DIV, cos), false).simplify();
            }

            // diff(toDeg(arg)) = toDeg(diff(arg))
            case TO_DEG -> result = new Expr(new Term(new Factor(FunctionOf1.toDeg(argDiff))), false).simplify();

            // diff(toRad(arg)) = toRad(diff(arg))
            case TO_RAD -> result = new Expr(new Term(new Factor(FunctionOf1.toRad(argDiff))), false).simplify();

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

        return new Factor(new FunctionOf1(this.function, this.argument.simplify()));
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        return this.function.name().toLowerCase(Locale.ROOT) + '(' + this.argument + ')';
    }
}
