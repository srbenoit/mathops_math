package dev.mathops.math.expression;

import dev.mathops.math.Add;
import dev.mathops.math.NumberUtils;
import dev.mathops.math.expression.ExpressionTokens.PlusMinusTok;
import dev.mathops.text.lexparse.AbstractProduction;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable expression.
 */
public class Expr extends AbstractProduction {

    /** The terms. */
    private final List<Term> terms;

    /** A flag to indicate this expression is already simplified. */
    private final boolean simplified;

    /**
     * Constructs a new synthetic {@code Expr} consisting of a single term.
     *
     * @param theTerm      the term (may not be {code null})
     * @param isSimplified {code true} if the expression is simplified (calls to {code simplify} will simply return the
     *                     expression itself)
     * @throws IllegalArgumentException if {code theTerm} is {code null}
     */
    public Expr(final Term theTerm, final boolean isSimplified) throws IllegalArgumentException {

        super(0, 0);

        if (theTerm == null) {
            throw new IllegalArgumentException("Term may not be null");
        }

        this.terms = new ArrayList<>(1);
        this.terms.add(theTerm);
        this.simplified = isSimplified;
    }

    /**
     * Constructs a new synthetic {@code Expr} consisting of two terms.
     *
     * @param theTerm1     the first term
     * @param theTerm2     the second term
     * @param isSimplified {code true} if the expression is simplified (calls to {code simplify} will simply return the
     *                     expression itself)
     * @throws IllegalArgumentException if {code theTerm1}, {code theTerm2}, or {code thePm2} is {code null}
     */
    public Expr(final Term theTerm1, final Term theTerm2, final boolean isSimplified) throws IllegalArgumentException {

        super(0, 0);

        if (theTerm1 == null || theTerm2 == null) {
            throw new IllegalArgumentException("Term may not be null");
        }

        this.terms = new ArrayList<>(2);
        this.terms.add(theTerm1);
        this.terms.add(theTerm2);
        this.simplified = isSimplified;
    }

    /**
     * Constructs a new synthetic {@code Expr}.
     *
     * @param theTermList  the terms
     * @param isSimplified {code true} if the expression is simplified (calls to {code simplify} will simply return the
     *                     expression itself)
     * @throws IllegalArgumentException if {code theLeadingTerm}, {code thePmList}, or {code theTermList} is {code null}
     *                                  or the {code thePmList} and {code theTermList} lists differ in length or contain
     *                                  a {code null} entry
     */
    public Expr(final List<Term> theTermList, final boolean isSimplified) throws IllegalArgumentException {

        super(0, 0);

        if (theTermList == null || theTermList.isEmpty()) {
            throw new IllegalArgumentException("Term list may not be null or empty");
        }
        for (final Term term : theTermList) {
            if (term == null) {
                throw new IllegalArgumentException("Term list may not contain nulls");
            }
        }

        this.terms = new ArrayList<>(theTermList);
        this.simplified = isSimplified;
    }

    /**
     * Constructs a new {@code Expr}.
     *
     * @param theStartPosition the position in the source token list where this production's pattern begins
     * @param theLength        the number of tokens in the source string that were matched to produce this production
     * @param theTermList      the terms
     * @param isSimplified     {code true} if the expression is simplified (calls to {code simplify} will simply return
     *                         the expression itself)
     */
    public Expr(final int theStartPosition, final int theLength, final List<Term> theTermList,
                final boolean isSimplified) {

        super(theStartPosition, theLength);

        if (theTermList == null || theTermList.isEmpty()) {
            throw new IllegalArgumentException("Term list may not be null or empty");
        }
        for (final Term term : theTermList) {
            if (term == null) {
                throw new IllegalArgumentException("Term list may not contain nulls");
            }
        }

        this.terms = new ArrayList<>(theTermList);
        this.simplified = isSimplified;
    }

    /**
     * Gets the total number of terms in the expression.
     *
     * @return the number of terms
     */
    public final int getNumTerms() {

        return this.terms.size();
    }

    /**
     * Gets a term.
     *
     * @param index the term index (0 for the leading factor)
     * @return the term
     */
    public final Term getTerm(final int index) {

        return (0 <= index && index < this.terms.size()) ? this.terms.get(index) : null;
    }

    /**
     * Tests whether the expression is constant with respect to a given variable name.
     *
     * @param varName the variable name
     * @return true if the expression is constant with respect to the variable
     */
    public final boolean isConstantWithRespectTo(final String varName) {

        boolean constant = true;

        for (final Term t : this.terms) {
            if (!t.isConstantWithRespectTo(varName)) {
                constant = false;
                break;
            }
        }

        return constant;
    }

    /**
     * Tests whether the expression is constant (has no variable references).
     *
     * @return true if the expression is constant
     */
    public final boolean isConstant() {

        boolean constant = true;

        for (final Term t : this.terms) {
            if (!t.isConstant()) {
                constant = false;
                break;
            }
        }

        return constant;
    }

    /**
     * Evaluates the expression.
     *
     * @param variables variable values
     * @return the result; null if unable to evaluate
     */
    public final Number eval(final VariableValues variables) {

        Number total = null;

        for (final Term term : this.terms) {
            final Number value = term.eval(variables);
            if (value == null) {
                total = null;
                break;
            } else if (total == null) {
                total = value;
            } else {
                total = Add.numberPlusNumber(total, value);
            }
        }

        return total;
    }

    /**
     * Generates the derivative of this expression with respect to a given variable.  This operation simply
     * differentiates each term and creates a new expression from the results.
     *
     * @param varName the name of the variable with respect to which to differentiate
     * @return the derivative
     */
    public final Expr differentiate(final String varName) {

        final int numTerms = this.terms.size();
        final List<Term> dTerms = new ArrayList<>(numTerms);
        this.terms.forEach((term) -> dTerms.add(term.differentiate(varName)));

        return new Expr(dTerms, false);
    }

    /**
     * Attempts to simplify the expression.  If this expression is already marked as simplified, it is simply returned.
     * Otherwise, each term in this expression is simplified, and then a series of expression-level simplifications are
     * applied, and the result is returned.
     * <p>
     * Simplifications performed:
     * <ol>
     * <li>All terms that have simplified to a single factor consisting of a parenthesized expression are replaced
     * by that expression's terms, with its sign distributed through each.</li>
     * <li>All terms that have simplified to a number are merged into a single number term.</li>
     * </ol>
     * <p>
     * Future simplifications that could be implemented:
     * <ul>
     * <li>Scan for terms with common factors, factoring then out into a new term with the common factors multiplying
     * a parenthesized sub-expression (which might simplify to a single factor, possibly zero).</li>
     * </ul>
     *
     * @return the simplified expression
     */
    public final Expr simplify() {

        final Expr result;

        if (this.simplified) {
            result = this;
        } else {
            // Simplify all terms
            final int numTerms = this.terms.size();
            final List<Term> newSimplified = new ArrayList<>(numTerms);
            this.terms.forEach((t) -> newSimplified.add(t.simplify()));

            // Apply expression-level simplifications and create resulting expression
            result = new Expr(mergeNumberTerms(//
                    expandParenthesizedSubexpressions(newSimplified)), //
                    true);
        }

        return result;
    }

    /**
     * Creates a new list of terms from a provided list of (simplified) terms.
     *
     * <p>
     * Each term in the source list is either (1) added directly to the constructed list if it has multiple factors, or
     * it has a single factor that is not a parenthesized expression, or (2) replaced in the constructed list by the
     * terms in its parenthesized factors, with the terms' leading sign distributed to each of those factors.
     *
     * <p>
     * Note that since the input terms are already simplified, it should not be necessary to recursively process the
     * expanded terms to see if they need further expansion.
     *
     * @param terms the list of simplified terms
     * @return the constructed list of terms
     */
    private List<Term> expandParenthesizedSubexpressions(final List<? extends Term> terms) {

        final int numTerms = terms.size();
        final List<Term> constructed = new ArrayList<>(numTerms);

        for (final Term t : terms) {
            if (t.getNumFactors() == 1) {
                final Factor f = t.getFactor(0);
                final Expr inner = f.getParenthesized();
                if (inner == null) {
                    constructed.add(t);
                } else {
                    final PlusMinusTok sign = t.getSign();
                    final boolean negate = sign != null && sign.op == '-';
                    final int numInnerTerms = inner.getNumTerms();
                    for (int i = 0; i < numInnerTerms; ++i) {
                        constructed.add(negate ? inner.getTerm(i).negate() : inner.getTerm(i));
                    }
                }
            } else {
                constructed.add(t);
            }
        }

        return constructed;
    }

    /**
     * Scan for terms that have evaluated to a number.  If there are more than one, merge all such terms into a single
     * number term.
     *
     * @param theTerms the list of terms
     * @return the resulting list of terms (the provided list if there are 0 or 1 number terms present, or a new list if
     *         there were more than 1 present)
     */
    private List<Term> mergeNumberTerms(final List<Term> theTerms) {

        Number allNumbers = Long.valueOf(0L);
        int numNumberTerms = 0;
        for (final Term t : theTerms) {
            if (t.isNumber()) {
                Number value = t.getNumberValue();

                if ((int) t.getSign().op == '-') {
                    value = NumberUtils.negate(value);
                }

                allNumbers = Add.numberPlusNumber(allNumbers, value);
                ++numNumberTerms;
            }
        }

        final List<Term> result;

        if (numNumberTerms <= 1) {
            result = theTerms;
        } else {
            result = new ArrayList<>(theTerms);
            result.removeIf(Term::isNumber);
            if (result.isEmpty() || !NumberUtils.isZero(allNumbers)) {
                result.add(0, new Term(allNumbers));
            }
        }

        return result;
    }

    /**
     * Returns an expression that is equal to this expression negated.  This operation simply negates each term and
     * creates an expression from the results.  If the original expression was considered "simplified", the new
     * expression will be considered as such.
     *
     * @return the negated expression
     */
    public final Expr negate() {

        final int numTerms = this.terms.size();
        final List<Term> nTerms = new ArrayList<>(numTerms);
        this.terms.forEach((term) -> nTerms.add(term.negate()));

        return new Expr(nTerms, this.simplified);
    }

    /**
     * Generates a string representation of the expression, which is simply the concatenation of the string
     * representation of each term, except that if the result has a leading '+' operation, that operation is removed.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {

        final StringBuilder sb = new StringBuilder(50);
        this.terms.forEach(sb::append);

        String result = sb.toString();
        if ((int) result.charAt(0) == '+') {
            result = result.substring(1);
        }

        return result;
    }
}
