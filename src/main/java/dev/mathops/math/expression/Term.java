package dev.mathops.math.expression;

import dev.mathops.math.Divide;
import dev.mathops.math.Multiply;
import dev.mathops.math.NumberUtils;
import dev.mathops.text.lexparse.AbstractProduction;

import java.util.ArrayList;
import java.util.List;

/**
 * A term in an expression.
 */
public class Term extends AbstractProduction {

    /** The sign associated with the term (may be {code null} for the leading term in an expression). */
    private final ExpressionTokens.PlusMinusTok sign;

    /** The leading factor (never null). */
    private Factor leadingFactor;

    /** The TimesDivToken preceding each subsequent factor. */
    private final List<ExpressionTokens.TimesDivCaretTok> opList;

    /** The subsequent factors. */
    private final List<Factor> factorList;

    /**
     * Constructs a new synthetic {@code Term} consisting of a single numeric factor.
     *
     * @param theNumber the number value
     */
    Term(final double theNumber) {

        super(0, 0);

        if (theNumber < 0) {
            this.sign = ExpressionTokens.PlusMinusTok.MINUS;
            this.leadingFactor = new Factor(Double.valueOf(-theNumber));
        } else {
            this.sign = ExpressionTokens.PlusMinusTok.PLUS;
            this.leadingFactor = new Factor(Double.valueOf(theNumber));
        }
        this.opList = new ArrayList<>(0);
        this.factorList = new ArrayList<>(0);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of a single numeric factor.
     *
     * @param theNumber the number value
     */
    public Term(final Number theNumber) {

        super(0, 0);

        this.sign = ExpressionTokens.PlusMinusTok.PLUS;
        this.leadingFactor = new Factor(theNumber);
        this.opList = new ArrayList<>(0);
        this.factorList = new ArrayList<>(0);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of a single factor.
     *
     * @param theSign          the sign associated with the term ({code null} treated as '+')
     * @param theLeadingFactor the leading factor (never null)
     */
    public Term(final ExpressionTokens.PlusMinusTok theSign, final Factor theLeadingFactor) {

        super(0, 0);

        if (theLeadingFactor == null) {
            throw new IllegalArgumentException("Leading factor may not be null");
        }

        this.sign = theSign == null ? ExpressionTokens.PlusMinusTok.PLUS : theSign;
        this.leadingFactor = theLeadingFactor;
        this.opList = new ArrayList<>(0);
        this.factorList = new ArrayList<>(0);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of a single factor and no sign.
     *
     * @param theLeadingFactor the leading factor (never null)
     */
    public Term(final Factor theLeadingFactor) {

        this(null, theLeadingFactor);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of two factors.
     *
     * @param theSign    the sign associated with the term ({code null} treated as '+')
     * @param theFactor1 the first factor
     * @param theTd      the times/div operator
     * @param theFactor2 the second factor
     */
    public Term(final ExpressionTokens.PlusMinusTok theSign, final Factor theFactor1,
                final ExpressionTokens.TimesDivCaretTok theTd, final Factor theFactor2) {

        super(0, 0);

        if (theFactor1 == null || theFactor2 == null) {
            throw new IllegalArgumentException("Factor may not be null");
        }
        if (theTd == null) {
            throw new IllegalArgumentException("Times/div may not be null");
        }

        this.sign = theSign == null ? ExpressionTokens.PlusMinusTok.PLUS : theSign;
        this.leadingFactor = theFactor1;

        this.opList = new ArrayList<>(1);
        this.opList.add(theTd);

        this.factorList = new ArrayList<>(1);
        this.factorList.add(theFactor2);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of two factors and no sign.
     *
     * @param theFactor1 the first factor
     * @param theTd      the times/div operator
     * @param theFactor2 the second factor
     */
    public Term(final Factor theFactor1, final ExpressionTokens.TimesDivCaretTok theTd, final Factor theFactor2) {

        this(null, theFactor1, theTd, theFactor2);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of three factors.
     *
     * @param theSign    the sign associated with the term ({code null} treated as '+')
     * @param theFactor1 the first factor
     * @param theTd1     the first times/div operator
     * @param theFactor2 the second factor
     * @param theTd2     the second times/div operator
     * @param theFactor3 the third factor
     */
    public Term(final ExpressionTokens.PlusMinusTok theSign, final Factor theFactor1,
                final ExpressionTokens.TimesDivCaretTok theTd1, final Factor theFactor2,
                final ExpressionTokens.TimesDivCaretTok theTd2, final Factor theFactor3) {

        super(0, 0);

        if (theFactor1 == null || theFactor2 == null || theFactor3 == null) {
            throw new IllegalArgumentException("Factor may not be null");
        }
        if (theTd1 == null || theTd2 == null) {
            throw new IllegalArgumentException("Times/div may not be null");
        }

        this.sign = theSign == null ? ExpressionTokens.PlusMinusTok.PLUS : theSign;
        this.leadingFactor = theFactor1;

        this.opList = new ArrayList<>(2);
        this.opList.add(theTd1);
        this.opList.add(theTd2);

        this.factorList = new ArrayList<>(2);
        this.factorList.add(theFactor2);
        this.factorList.add(theFactor3);
    }

    /**
     * Constructs a new synthetic {@code Term} consisting of three factors and no sign.
     *
     * @param theFactor1 the first factor
     * @param theTd1     the first times/div operator
     * @param theFactor2 the second factor
     * @param theTd2     the second times/div operator
     * @param theFactor3 the third factor
     */
    public Term(final Factor theFactor1, final ExpressionTokens.TimesDivCaretTok theTd1, final Factor theFactor2,
                final ExpressionTokens.TimesDivCaretTok theTd2, final Factor theFactor3) {

        this(null, theFactor1, theTd2, theFactor2, theTd2, theFactor3);
    }

    /**
     * Constructs a new synthetic {@code Term}.
     *
     * @param theSign          the sign associated with the term ({code null} treated as '+')
     * @param theLeadingFactor the leading factor (never null)
     * @param theTdList        the TimesDivToken preceding each subsequent factor
     * @param theFactorList    the subsequent factors
     */
    public Term(final ExpressionTokens.PlusMinusTok theSign, final Factor theLeadingFactor,
                final List<ExpressionTokens.TimesDivCaretTok> theTdList, final List<Factor> theFactorList) {

        super(0, 0);

        if (theLeadingFactor == null) {
            throw new IllegalArgumentException("Leading factor may not be null");
        }
        if (theFactorList == null) {
            throw new IllegalArgumentException("Factor list may not be null");
        }
        if (theTdList == null) {
            throw new IllegalArgumentException("Times/div list may not be null");
        }
        if (theTdList.size() != theFactorList.size()) {
            throw new IllegalArgumentException("Factor and times/div lists must be same size");
        }
        for (final ExpressionTokens.TimesDivCaretTok td : theTdList) {
            if (td == null) {
                throw new IllegalArgumentException("Times/div lists may not contain nulls");
            }
        }
        for (final Factor factor : theFactorList) {
            if (factor == null) {
                throw new IllegalArgumentException("Factor list may not contain nulls");
            }
        }

        this.sign = theSign == null ? ExpressionTokens.PlusMinusTok.PLUS : theSign;
        this.leadingFactor = theLeadingFactor;
        this.opList = new ArrayList<>(theTdList);
        this.factorList = new ArrayList<>(theFactorList);
    }

    /**
     * Constructs a new synthetic {@code Term} with no sign.
     *
     * @param theLeadingFactor the leading factor (never null)
     * @param theTdList        the TimesDivToken preceding each subsequent factor
     * @param theFactorList    the subsequent factors
     */
    public Term(final Factor theLeadingFactor, final List<ExpressionTokens.TimesDivCaretTok> theTdList,
                final List<Factor> theFactorList) {

        this(null, theLeadingFactor, theTdList, theFactorList);
    }

    /**
     * Constructs a new {@code Term}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theSign          the sign associated with the term ({code null} treated as '+')
     * @param theLeadingFactor the leading factor (never null)
     * @param theTdList        the TimesDivToken preceding each subsequent factor
     * @param theFactorList    the subsequent factors
     */
    public Term(final int theStartPosition, final int theLength, final ExpressionTokens.PlusMinusTok theSign,
                final Factor theLeadingFactor, final List<ExpressionTokens.TimesDivCaretTok> theTdList,
                final List<Factor> theFactorList) {

        super(theStartPosition, theLength);

        if (theLeadingFactor == null) {
            throw new IllegalArgumentException("Leading factor may not be null");
        }
        if (theFactorList == null) {
            throw new IllegalArgumentException("Factor list may not be null");
        }
        if (theTdList == null) {
            throw new IllegalArgumentException("Times/div list may not be null");
        }
        if (theTdList.size() != theFactorList.size()) {
            throw new IllegalArgumentException("Factor and times/div lists must be same size");
        }
        for (final ExpressionTokens.TimesDivCaretTok timesDivCaretTok : theTdList) {
            if (timesDivCaretTok == null) {
                throw new IllegalArgumentException("Times/div lists may not contain nulls");
            }
        }
        for (final Factor factor : theFactorList) {
            if (factor == null) {
                throw new IllegalArgumentException("Factor list may not contain nulls");
            }
        }

        this.sign = theSign == null ? ExpressionTokens.PlusMinusTok.PLUS : theSign;
        this.leadingFactor = theLeadingFactor;
        this.opList = new ArrayList<>(theTdList);
        this.factorList = new ArrayList<>(theFactorList);
    }

    /**
     * Constructs a new {@code Term}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theLeadingFactor the leading factor (never null)
     * @param theTdList        the TimesDivToken preceding each subsequent factor
     * @param theFactorList    the subsequent factors
     */
    public Term(final int theStartPosition, final int theLength, final Factor theLeadingFactor,
                final List<ExpressionTokens.TimesDivCaretTok> theTdList, final List<Factor> theFactorList) {

        this(theStartPosition, theLength, null, theLeadingFactor, theTdList, theFactorList);
    }

    /**
     * Gets the sign.
     *
     * @return the sign
     */
    public final ExpressionTokens.PlusMinusTok getSign() {

        return this.sign;
    }

    /**
     * Gets the total number of factors in the term.
     *
     * @return the number of factors
     */
    public final int getNumFactors() {

        return 1 + this.factorList.size();
    }

    /**
     * Gets a factor.
     *
     * @param index the factor index (0 for the leading factor)
     * @return the factor
     */
    public final Factor getFactor(final int index) {

        Factor result = null;

        if (index == 0) {
            result = this.leadingFactor;
        } else if (index > 0 && index <= this.factorList.size()) {
            result = this.factorList.get(index - 1);
        }

        return result;
    }

    /**
     * Gets an operation.
     *
     * @param index the index of the factor the operation follows
     * @return the operation
     */
    public final ExpressionTokens.TimesDivCaretTok getOp(final int index) {

        ExpressionTokens.TimesDivCaretTok result = null;

        if (index >= 0 && index < this.opList.size()) {
            result = this.opList.get(index);
        }

        return result;
    }

    /**
     * Tests whether the expression is constant with respect to a given variable name.
     *
     * @param varName the variable name
     * @return true if the expression is constant with respect to the variable
     */
    public final boolean isConstantWithRespectTo(final String varName) {

        boolean constant = this.leadingFactor.isConstantWithRespectTo(varName);

        for (final Factor t : this.factorList) {
            if (constant) {
                constant = t.isConstantWithRespectTo(varName);
            }
        }

        return constant;
    }

    /**
     * Tests whether the term is constant (has no variable references).
     *
     * @return true if the term is constant
     */
    public final boolean isConstant() {

        boolean constant = this.leadingFactor.isConstant();

        for (final Factor t : this.factorList) {
            if (constant) {
                constant = t.isConstant();
            }
        }

        return constant;
    }

    /**
     * Tests whether the term consists of a single factor that is a number.
     *
     * @return true if a number
     */
    public final boolean isNumber() {

        return this.factorList.isEmpty() && this.leadingFactor.getNumber() != null;
    }

    /**
     * Gets the number value of this term if it is simply a number.
     *
     * @return the number value; {code null} if this term is not a simple number
     */
    public final Number getNumberValue() {

        Number value = null;

        if (this.factorList.isEmpty()) {
            value = this.leadingFactor.getNumber();
        }

        return value;
    }

    /**
     * Evaluates the term.
     *
     * @param variables variable values
     * @return the result; null if unable to evaluate
     */
    public final Number eval(final VariableValues variables) {

        convertExponentsToPowFunctions();

        Number value = this.leadingFactor.eval(variables);

        if (value != null) {
            final int opListSize = this.opList.size();
            final int factorListSize = this.factorList.size();
            final int size = Math.min(opListSize, factorListSize);

            for (int i = 0; i < size; ++i) {
                final Number temp = this.factorList.get(i).eval(variables);
                if (temp == null) {
                    value = null;
                    break;
                }

                final int op = (int) this.opList.get(i).op;
                if (op == '/') {
                    value = Divide.numberDivNumber(value, temp);
                } else if (op == '*') {
                    value = Multiply.numberTimesNumber(value, temp);
                } else {
                    value = Double.valueOf(Math.pow(value.doubleValue(), temp.doubleValue()));
                }
            }

            if ((int) this.sign.op == '-') {
                value = NumberUtils.negate(value);
            }
        }

        return value;
    }

    /**
     * Generates the derivative of this term with respect to a given variable.
     *
     * @param varName the name of the variable with respect to which to differentiate
     * @return the derivative
     */
    public final Term differentiate(final String varName) {

        convertExponentsToPowFunctions();

//        Log.info("    Differentiating term ", this);

        final List<Factor> numer = new ArrayList<>(4);
        final List<Factor> denom = new ArrayList<>(4);

        numer.add(this.leadingFactor);
        for (int i = 0; i < this.opList.size(); ++i) {
            final Factor factor = this.factorList.get(i);
            if ((int) this.opList.get(i).op == '/') {
                denom.add(factor);
            } else {
                numer.add(factor);
            }
        }

        final Term result;

        if (denom.isEmpty()) {
            final Expr prod = doProductRule(numer, varName).simplify();
            result = new Term(new Factor(prod));
        } else {
            result = doQuotientRule(numer, denom, varName).simplify();
        }

        //        Log.info("    Derivative of term ", this, " is ", result);

        return result;
    }

    /**
     * Attempts to simplify the term.
     *
     * @return the simplified term
     */
    public final Term simplify() {

        convertExponentsToPowFunctions();

        //        Log.info("Simplifying ", this);

        final int numFactors = this.factorList.size();
        final List<Factor> newFactorList = new ArrayList<>(numFactors + 1);
        final List<ExpressionTokens.TimesDivCaretTok> newOpList = new ArrayList<>(numFactors + 1);

        // Simplify all factors and add (including leading factors) to a list along with ops, with
        // an implied '*' operation on the first factor

        newOpList.add(ExpressionTokens.TimesDivCaretTok.TIMES);
        final Factor leadingSimple = this.leadingFactor.simplify();
        newFactorList.add(leadingSimple);

        for (int i = 0; i < numFactors; ++i) {
            final ExpressionTokens.TimesDivCaretTok op = this.opList.get(i);
            final Factor simplified = this.factorList.get(i).simplify();

            // If a simplified factor consists of an expression with a single term, we can replace that expression
            // by its term's factors (but if that factor is preceded by a '/' operator, we need to adjust each
            // operator in the inner term).
            final Expr inner = simplified.getParenthesized();
            if (inner == null || inner.getNumTerms() > 1) {
                newOpList.add(op);
                newFactorList.add(simplified);
            } else {
                final Term innerTerm = inner.getTerm(0);
                final boolean isDiv = ExpressionTokens.TimesDivCaretTok.DIV.equals(op);

                newOpList.add(isDiv ? ExpressionTokens.TimesDivCaretTok.DIV : ExpressionTokens.TimesDivCaretTok.TIMES);
                final boolean negate = (int) innerTerm.sign.op == '-';
                if (negate) {
                    newFactorList.add(innerTerm.leadingFactor);
                    newOpList.add(ExpressionTokens.TimesDivCaretTok.TIMES);
                    newFactorList.add(new Factor(Integer.valueOf(-1)));
                } else {
                    newFactorList.add(innerTerm.leadingFactor);
                }

                for (int j = 1; j < innerTerm.getNumFactors(); ++j) {
                    final ExpressionTokens.TimesDivCaretTok innerOp = innerTerm.getOp(j - 1);

                    if (isDiv) {
                        if (ExpressionTokens.TimesDivCaretTok.TIMES.equals(innerOp)) {
                            newOpList.add(ExpressionTokens.TimesDivCaretTok.DIV);
                        } else {
                            newOpList.add(ExpressionTokens.TimesDivCaretTok.TIMES);
                        }
                    } else {
                        newOpList.add(innerOp);
                    }

                    final Factor factor = innerTerm.getFactor(j);
                    newFactorList.add(factor);
                }
            }
        }

        //        for (int i = 0; i < newFactorList.size(); ++i) {
        //            Log.fine("* ", newOpList.get(i), " ", newFactorList.get(i));
        //        }

        // Scan the list of simplified factors and check for factors that have simplified to a number, then merge all
        // into a single number term (unless the result is zero, then omit).

        Number allNumbers = Integer.valueOf(1);
        int numNumberFactors = 0;
        int numOtherFactors = 0;
        boolean divByZero = false;

        int count = newFactorList.size();
        int i = 0;
        while (i < count) {
            final Factor factor = newFactorList.get(i);
            if (factor.getNumber() == null) {
                ++numOtherFactors;
                ++i;
            } else {
                ++numNumberFactors;
                newFactorList.remove(i);
                final ExpressionTokens.TimesDivCaretTok op = newOpList.remove(i);
                final Number val = factor.getNumber();
                if ((int) op.op == '*') {
                    allNumbers = Multiply.numberTimesNumber(allNumbers, val);
                } else if (0 == val.doubleValue()) {
                    divByZero = true;
                    break;
                } else {
                    allNumbers = Divide.numberDivNumber(allNumbers, val);
                }
                --count;
            }
        }

        //        Log.info("Found " + numNumberFactors + " number factors, " + numOtherFactors + " others");

        final Term result;

        if (divByZero) {
            result = new Term(Double.NaN);
        } else if (numNumberFactors > 0) {
            if (numOtherFactors == 0 || NumberUtils.isZero(allNumbers)) {
                result = new Term(this.sign, new Factor(allNumbers));
            } else {
                final Factor newLeading = new Factor(allNumbers);

                if (newFactorList.isEmpty()) {
                    result = new Term(this.sign, newLeading);
                } else {
                    result = new Term(this.sign, newLeading, newOpList, newFactorList);
                }
            }
        } else {
            result = this;
        }

        //        Log.info("Term ", this, " simplified to ", result);

        return result;
    }

    /**
     * Returns a term that is equal to this term negated.
     *
     * @return the negated term
     */
    public final Term negate() {

        final Factor newLeading = new Factor(Double.valueOf(-1.0));
        final int numFactors = this.factorList.size();
        final List<Factor> newFactorList = new ArrayList<>(numFactors + 1);
        final List<ExpressionTokens.TimesDivCaretTok> newOpList = new ArrayList<>(numFactors + 1);

        newFactorList.add(this.leadingFactor);
        newFactorList.addAll(this.factorList);

        newOpList.add(ExpressionTokens.TimesDivCaretTok.TIMES);
        newOpList.addAll(this.opList);

        return new Term(newLeading, newOpList, newFactorList);
    }

    /**
     * Scans the list of operations and converts any exponentiation operations found into "pow" functions.
     */
    private void convertExponentsToPowFunctions() {

        // TODO: member flag to indicate there are no exponents so we can skip this quickly

        boolean hasExponents = false;
        for (final ExpressionTokens.TimesDivCaretTok td : this.opList) {
            if ((int) td.op == '^') {
                hasExponents = true;
                break;
            }
        }

        if (hasExponents) {
            int numOps = this.opList.size();

            // Factor1 ^ Factor2 --> new Factor(Pow(factor1, factor2))
            for (int i = 0; i < numOps; ++i) {
                if ((int) this.opList.get(i).op == '^') {
                    if (i == 0) {
                        final Factor pow = new Factor(FunctionOf2.pow( //
                                new Expr(new Term(this.leadingFactor), false), //
                                new Expr(new Term(this.factorList.get(0)), false)));
                        this.opList.remove(0);
                        this.factorList.remove(0);
                        this.leadingFactor = pow;
                    } else {
                        final Factor pow = new Factor(FunctionOf2.pow( //
                                new Expr(new Term(this.factorList.get(i - 1)), false), //
                                new Expr(new Term(this.factorList.get(i)), false)));

                        this.opList.remove(i);
                        this.factorList.remove(i);
                        this.factorList.set(i, pow);
                    }
                    --numOps;
                }
            }
        }
    }

    /**
     * Performs a product rule derivative given a list of factors.
     *
     * @param factors the list of factors
     * @param varName the name of the variable with respect to which to differentiate
     * @return the result
     */
    protected static Expr doProductRule(final List<? extends Factor> factors, final String varName) {

        final int count = factors.size();
        final Expr result;

        if (count == 1) {
            result = new Expr(new Term(factors.get(0).differentiate(varName)), false);
        } else {
            // Divide factors into those constant with respect to variable, and those that vary

            final List<Factor> constFactors = new ArrayList<>(count);
            final List<Factor> varFactors = new ArrayList<>(count);

            for (final Factor f : factors) {
                if (f.isConstantWithRespectTo(varName)) {
                    constFactors.add(f);
                } else {
                    varFactors.add(f);
                }
            }

            final int numVarFactors = varFactors.size();
            if (0 == numVarFactors) {
                // Nothing varies - derivative is zero
                result = new Expr(new Term(0.0), true);
            } else {
                // Perform a product-rule derivative of all the variable factors

                final List<Term> resultTerms = new ArrayList<>(count);
                for (int i = 0; i < numVarFactors; ++i) {
                    final List<Factor> termFactors = new ArrayList<>(numVarFactors);

                    for (int j = 0; j < numVarFactors; ++j) {
                        if (j == i) {
                            termFactors.add(varFactors.get(i).differentiate(varName));
                        } else {
                            termFactors.add(varFactors.get(i));
                        }
                    }

                    final Term product = makeProductOf(termFactors);
                    resultTerms.add(product);
                }

                final Expr sum = new Expr(resultTerms, false);

                // Then multiply that result by all constant factors (if any)

                if (constFactors.isEmpty()) {
                    result = sum;
                } else {
                    // Result should be the product of the resulting sum and all const factors
                    constFactors.add(new Factor(sum));
                    final Term product = makeProductOf(constFactors);
                    result = new Expr(product, false);
                }
            }
        }

        return result;
    }

    /**
     * Given a list of factors, creates a term that consists of the product of all factors.
     *
     * @param factors the factors
     * @return the term
     */
    protected static Term makeProductOf(final List<Factor> factors) {

        final Factor leading = factors.remove(0);

        final int numFactors = factors.size();
        final List<ExpressionTokens.TimesDivCaretTok> termTdList = new ArrayList<>(numFactors);
        for (int k = 0; k < numFactors; ++k) {
            termTdList.add(ExpressionTokens.TimesDivCaretTok.TIMES);
        }

        final Term result = new Term(leading, termTdList, factors);

        factors.add(0, leading);

        return result;
    }

    /**
     * Performs a quotient rule derivative given a list of numerator factors and a list of denominator factors.
     *
     * @param numer   the list of factors in the numerator
     * @param denom   the list of factors in the denominator
     * @param varName the name of the variable with respect to which to differentiate
     * @return the result
     */
    protected static Term doQuotientRule(final List<? extends Factor> numer, final List<? extends Factor> denom,
                                         final String varName) {

        final Expr derivOfNumer = doProductRule(numer, varName);
        final Expr derivOfDenom = doProductRule(denom, varName);

        // Denominator times derivative of numerator
        final List<Factor> toMul1 = new ArrayList<>(2);
        toMul1.addAll(denom);
        toMul1.add(new Factor(derivOfNumer));
        final Term term1 = makeProductOf(toMul1);

        // Numerator times derivative of denominator
        final List<Factor> toMul2 = new ArrayList<>(2);
        toMul2.addAll(numer);
        toMul2.add(new Factor(derivOfDenom));
        final Term term2 = makeProductOf(toMul2);

        // (Denom times derivative of numer) - (Numer times derivatives of denom)
        final List<Term> topTerms = new ArrayList<>(2);
        topTerms.add(term1);
        final Term negated = term2.negate();
        topTerms.add(negated);
        final Factor topFact = new Factor(new Expr(topTerms, false));

        // Denominator squared
        final int size = denom.size();
        final List<Factor> botFactors = new ArrayList<>(size * 2);
        botFactors.addAll(denom);
        botFactors.addAll(denom);
        final Term bot = makeProductOf(botFactors);
        final Factor botFact = new Factor(new Expr(bot, false));

        // Need a term that is "top / bot"
        final List<ExpressionTokens.TimesDivCaretTok> quotientTds = new ArrayList<>(1);
        quotientTds.add(ExpressionTokens.TimesDivCaretTok.DIV);
        final List<Factor> quotientFactors = new ArrayList<>(1);
        quotientFactors.add(botFact);

        return new Term(topFact, quotientTds, quotientFactors).simplify();
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final StringBuilder sb = new StringBuilder(50);

        if (this.sign != null) {
            sb.append(this.sign);
        }

        sb.append(this.leadingFactor);

        for (int i = 0; i < this.opList.size(); ++i) {
            sb.append(this.opList.get(i).op);
            sb.append(this.factorList.get(i));
        }

        return sb.toString();
    }
}
