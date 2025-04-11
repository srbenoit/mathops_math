package dev.mathops.math.expression;

import dev.mathops.math.expression.ExpressionTokens.CommaTok;
import dev.mathops.math.expression.ExpressionTokens.FunctionOf1Tok;
import dev.mathops.math.expression.ExpressionTokens.FunctionOf2Tok;
import dev.mathops.math.expression.ExpressionTokens.LParenTok;
import dev.mathops.math.expression.ExpressionTokens.NumberTok;
import dev.mathops.math.expression.ExpressionTokens.PlusMinusTok;
import dev.mathops.math.expression.ExpressionTokens.RParenTok;
import dev.mathops.math.expression.ExpressionTokens.SymbolTok;
import dev.mathops.math.expression.ExpressionTokens.TimesDivCaretTok;
import dev.mathops.math.expression.ExpressionTokens.VariableTok;
import dev.mathops.math.expression.ExpressionTokens.WhitespaceTok;
import dev.mathops.text.lexparse.ISyntacticPattern;
import dev.mathops.text.lexparse.IToken;

import java.util.ArrayList;
import java.util.List;

/**
 * The patterns in the expression syntactic grammar.
 */
public class ExpressionSyntacticGrammar {

    /** The pattern that matches an "REAL_EXPR" production. */
    public static final RealExprPattern REAL_EXPR = new RealExprPattern();

    /** The pattern that matches an "REAL_TERM" production. */
    public static final RealTermPattern REAL_TERM = new RealTermPattern();

    /** The pattern that matches an "REAL_FACTOR" production. */
    public static final RealFactorPattern REAL_FACTOR = new RealFactorPattern();

    /**
     * A pattern that matches a "REAL_EXPR" production.
     */
    public static class RealExprPattern implements ISyntacticPattern {

        /**
         * Constructs a new {code RealExprPattern}.
         */
        private RealExprPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern of tokens and produce an object. The object corresponding to the longest sequence
         * of tokens that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * token list at the specified position.
         *
         * <pre>
         * REAL_EXPR := S? plus-minus-tok? REAL_TERM (plus-minus-tok REAL_TERM)*
         * </pre>
         *
         * @param tokens the list of tokens (which may contain an EOS token at the end)
         * @param start  the start position in the token list
         * @return the object; null if none matched
         */
        @Override
        public final Expr match(final List<IToken> tokens, final int start) {

            Expr result = null;

            final int len = tokens.size();

            int pos = skipWhitespace(tokens, start);

            if (pos < len) {
                final Term leadingTerm = REAL_TERM.match(tokens, pos);

                if (leadingTerm != null) {

                    pos += leadingTerm.getLength();

                    final List<Term> termList = new ArrayList<>();
                    termList.add(leadingTerm);

                    int end = pos;
                    // All but the first term MUST be preceded by a +/- token.
                    while (pos < len && tokens.get(pos) instanceof PlusMinusTok) {
                        final Term term = REAL_TERM.match(tokens, pos);

                        if (term != null) {
                            termList.add(term);
                            pos += term.getLength();
                            end = pos;
                        }
                    }

                    result = new Expr(start, end - start, termList, false);
                }
            }

            return result;
        }
    }

    /**
     * A pattern that matches a "REAL_TERM" production.
     */
    public static class RealTermPattern implements ISyntacticPattern {

        /**
         * Constructs a new {@code RealTermPattern}.
         */
        private RealTermPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern of tokens and produce an object. The object corresponding to the longest sequence
         * of tokens that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * token list at the specified position.
         *
         * <pre>
         * REAL_TERM := S? REAL_FACTOR (S? times-div-caret-tok S? REAL_FACTOR)* S?
         * </pre>
         *
         * @param tokens the list of tokens
         * @param start  the start position in the token list
         * @return the object; null if none matched
         */
        @Override
        public final Term match(final List<IToken> tokens, final int start) {

            Term result = null;

            final int len = tokens.size();

            PlusMinusTok pm = null;

            int pos = skipWhitespace(tokens, start);

            if (pos < len && tokens.get(pos) instanceof final PlusMinusTok pmTok) {
                pm = pmTok;
                pos = skipWhitespace(tokens, pos + 1);
            }

            if (pos < len) {
                final Factor leadingFactor = REAL_FACTOR.match(tokens, pos);

                if (leadingFactor != null) {
                    pos += leadingFactor.getLength();

                    final List<TimesDivCaretTok> tdmList = new ArrayList<>();
                    final List<Factor> factorList = new ArrayList<>();

                    pos = skipWhitespace(tokens, pos);
                    int end = pos;

                    while (pos < len && tokens.get(pos) instanceof final TimesDivCaretTok tdm) {
                        pos = skipWhitespace(tokens, pos + 1);
                        final Factor factor = REAL_FACTOR.match(tokens, pos);

                        if (factor != null) {
                            tdmList.add(tdm);
                            factorList.add(factor);
                            pos += factor.getLength();

                            pos = skipWhitespace(tokens, pos);
                            end = pos;
                        }
                    }

                    result = new Term(start, end - start, pm, leadingFactor, tdmList, factorList);
                }
            }

            return result;
        }
    }

    /**
     * A pattern that matches a "REAL_FACTOR" production.
     */
    public static class RealFactorPattern implements ISyntacticPattern {

        /**
         * Constructs a new {@code RealFactorPattern}.
         */
        private RealFactorPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern of tokens and produce an object. The object corresponding to the longest sequence
         * of tokens that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * token list at the specified position.
         *
         * <pre>
         * REAL_FACTOR := ( lparen-token REAL_EXPR rparen-token )
         *              | ( function-r-token REAL_EXPR rparen-token )
         *              | ( function-rr-token REAL_EXPR comma-token REAL_EXPR rparen-token )
         *              | variable-token
         *              | symbol-token
         * </pre>
         *
         * @param tokens the list of tokens
         * @param start  the start position in the token list
         * @return the object; null if none matched
         */
        @Override
        public final Factor match(final List<IToken> tokens, final int start) {

            Factor result = null;

            final int len = tokens.size();

            if (start < len) {
                final IToken tok = tokens.get(start);

                if (tok instanceof LParenTok) {
                    final Expr inner = REAL_EXPR.match(tokens, start + 1);
                    if (inner != null) {
                        int pos = start + 1 + inner.getLength();
                        if (pos < len && tokens.get(pos) instanceof RParenTok) {
                            result = new Factor(start, pos + 1 - start, inner);
                        }
                    }
                } else if (tok instanceof FunctionOf1Tok) {
                    final Expr inner = REAL_EXPR.match(tokens, start + 1);
                    if (inner != null) {
                        final int pos = start + 1 + inner.getLength();
                        if (pos < len && tokens.get(pos) instanceof RParenTok) {
                            result = new Factor(start, pos + 1 - start, new FunctionOf1(start, pos + 1 - start,
                                    ((FunctionOf1Tok) tok).function, inner));
                        }
                    }
                } else if (tok instanceof FunctionOf2Tok) {
                    final Expr inner1 = REAL_EXPR.match(tokens, start + 1);
                    if (inner1 != null) {
                        int pos = start + 1 + inner1.getLength();
                        if (pos < len && tokens.get(pos) instanceof CommaTok) {
                            final Expr inner2 = REAL_EXPR.match(tokens, pos + 1);
                            if (inner2 != null) {
                                pos = pos + 1 + inner2.getLength();
                                if (pos < len && tokens.get(pos) instanceof RParenTok) {
                                    result = new Factor(start, pos + 1 - start, new FunctionOf2(start,
                                            pos + 1 - start, ((FunctionOf2Tok) tok).function, inner1, inner2));
                                }
                            }
                        }
                    }
                } else if (tok instanceof VariableTok) {
                    result = new Factor(start, 1, ((VariableTok) tok).name);
                } else if (tok instanceof SymbolTok) {
                    result = new Factor(start, 1, ((SymbolTok) tok).symbol);
                } else if (tok instanceof NumberTok) {
                    result = new Factor(start, 1, ((NumberTok) tok).value);
                }
            }

            return result;
        }
    }

    /**
     * Finds the first position in a token list on or after a start position that is not whitespace.
     *
     * @param tokens the token list
     * @param start  the start position
     * @return the position of the first non-whitespace token found; or the length of the list if none was found
     */
    private static int skipWhitespace(final List<IToken> tokens, final int start) {

        int pos = start;
        final int len = tokens.size();

        while (pos < len && tokens.get(pos) instanceof WhitespaceTok) {
            ++pos;
        }

        return pos;
    }
}
