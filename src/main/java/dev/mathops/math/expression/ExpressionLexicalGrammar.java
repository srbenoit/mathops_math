package dev.mathops.math.expression;

import dev.mathops.text.lexparse.ILexicalPattern;

import java.util.List;

/**
 * The patterns in the expression lexical grammar.
 */
public class ExpressionLexicalGrammar {

    /**
     * Tests whether a character is a digit.
     *
     * @param cp the character
     * @return true if a digit; false if not
     */
    private static boolean isDigit(final int cp) {

        return cp >= '0' && cp <= '9';
    }

    /**
     * Tests whether a character is a letter.
     *
     * @param cp the character
     * @return true if a letter; false if not
     */
    private static boolean isLetter(final int cp) {

        return cp >= 'a' && cp <= 'z' //
                || cp >= 'A' && cp <= 'Z' //
                || cp >= 0x0391 && cp <= 0x03A1 //
                || cp >= 0x03A3 && cp <= 0x03A9 //
                || cp >= 0x03B1 && cp <= 0x03C9;
    }

    /**
     * Tests whether the characters between start and end delimiters constitute an identifier.
     *
     * @param s     the string, as a sequence of Unicode code points
     * @param start the index of the first character to test
     * @param end   the index of the end of the characters to test
     * @return the identifier as a string if contents are a valid identifier; {code null} if not
     */
    public static String isIdentifier(final int[] s, final int start, final int end) {

        String result = null;

        if (end > start) {
            final StringBuilder sb = new StringBuilder(end - start);

            if (isLetter(s[start])) {
                boolean valid = true;
                sb.append((char) s[start]);

                for (int i = start + 1; i < end; ++i) {
                    final int cp = s[i];

                    if (cp != '_' && !isLetter(cp) && !isDigit(cp)) {
                        valid = false;
                        break;
                    }
                    sb.append((char) cp);
                }
                result = valid ? sb.toString() : null;
            }
        }

        return result;
    }

    /** The pattern that matches a "whitespace-token". */
    public static final WhitespacePattern WHITESPACE = new WhitespacePattern();

    /**
     * A pattern that matches a "whitespace-token".
     */
    public static final class WhitespacePattern implements ILexicalPattern {

        /**
         * Constructs a new {@code WhitespacePattern}.
         */
        private WhitespacePattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public final ExpressionTokens.WhitespaceTok match(final int[] codePoints, final int p) {

            ExpressionTokens.WhitespaceTok result = null;

            int pos = p;
            while (pos < codePoints.length) {
                final int cp = codePoints[pos];

                if (cp == ' ' || cp == '\t' || cp == '\r' || cp == '\n') {
                    ++pos;
                } else {
                    break;
                }
            }

            if (pos > p) {
                result = new ExpressionTokens.WhitespaceTok(p, pos - p);
            }

            return result;
        }
    }

    /** The pattern that matches a "number-token". */
    public static final NumberPattern NUMBER = new NumberPattern();

    /**
     * A pattern that matches a "number-token".
     */
    public static final class NumberPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code NumberPattern}.
         */
        private NumberPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public final ExpressionTokens.NumberTok match(final int[] codePoints, final int p) {

            ExpressionTokens.NumberTok result = null;

            final int len = codePoints.length;
            if (p < len) {
                int pos = p;

                // Skip leading digits
                while (pos < len && isDigit(codePoints[pos])) {
                    ++pos;
                }
                // Skip '.' if found, and any digits that follow
                if (pos < len && codePoints[pos] == '.') {
                    ++pos;
                    while (pos < len && isDigit(codePoints[pos])) {
                        ++pos;
                    }
                }
                int end = pos;
                // Test for E+/- notation
                if (pos < len && (codePoints[pos] == 'E' || codePoints[pos] == 'e')) {
                    ++pos;
                    if (pos < len && (codePoints[pos] == '+' || codePoints[pos] == '-')) {
                        ++pos;
                    }
                    boolean foundDigit = false;
                    while (pos < len && isDigit(codePoints[pos])) {
                        ++pos;
                        foundDigit = true;
                    }
                    if (!foundDigit) {
                        pos = end;
                    }
                }

                final String str = new String(codePoints, p, pos - p);
                try {
                    result = new ExpressionTokens.NumberTok(p, pos - p, Double.valueOf(str));
                } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                    // Log.warning("Invalid number value: " + str);
                }
            }

            return result;
        }

    }

    /** The pattern that matches a "lparen-token". */
    public static final LParenPattern LPAREN = new LParenPattern();

    /**
     * A pattern that matches a "lparen-token".
     */
    public static final class LParenPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code LParenPattern}.
         */
        private LParenPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public final ExpressionTokens.LParenTok match(final int[] codePoints, final int p) {

            ExpressionTokens.LParenTok result = null;

            if (p < codePoints.length && codePoints[p] == '(') {
                result = new ExpressionTokens.LParenTok(p, 1);
            }

            return result;
        }
    }

    /** The pattern that matches a "rparen-token". */
    public static final RParenPattern RPAREN = new RParenPattern();

    /**
     * A pattern that matches a "rparen-token".
     */
    public static final class RParenPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code RParenPattern}.
         */
        private RParenPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public final ExpressionTokens.RParenTok match(final int[] codePoints, final int p) {

            ExpressionTokens.RParenTok result = null;

            if (p < codePoints.length) {
                final int cp = codePoints[p];

                if (cp == ')') {
                    result = new ExpressionTokens.RParenTok(p, 1);
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "plus-minus-token". */
    public static final PlusMinusPattern PLUS_MINUS = new PlusMinusPattern();

    /**
     * A pattern that matches a "plus-minus-token".
     */
    public static final class PlusMinusPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code PlusMinusPattern}.
         */
        private PlusMinusPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public final ExpressionTokens.PlusMinusTok match(final int[] codePoints, final int p) {

            ExpressionTokens.PlusMinusTok result = null;

            if (p < codePoints.length) {
                final int cp = codePoints[p];

                if (cp == '+') {
                    result = new ExpressionTokens.PlusMinusTok(p, 1, '+');
                } else if (cp == '-') {
                    result = new ExpressionTokens.PlusMinusTok(p, 1, '-');
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "comma-tok". */
    public static final CommaPattern COMMA = new CommaPattern();

    /**
     * A pattern that matches a "comma-tok".
     */
    public static final class CommaPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code CommaPattern}.
         */
        private CommaPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.CommaTok match(final int[] codePoints, final int p) {

            ExpressionTokens.CommaTok result = null;

            if (p < codePoints.length && codePoints[p] == ',') {
                result = new ExpressionTokens.CommaTok(p, 1);
            }

            return result;
        }
    }

    /** The pattern that matches a "times-div-caret-token". */
    public static final TimesDivCaretPattern TIMES_DIV_CARET = new TimesDivCaretPattern();

    /**
     * A pattern that matches a "times-div-caret-token".
     */
    public static final class TimesDivCaretPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code TimesDivCaretPattern}.
         */
        private TimesDivCaretPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.TimesDivCaretTok match(final int[] codePoints, final int p) {

            ExpressionTokens.TimesDivCaretTok result = null;

            if (p < codePoints.length) {
                final int cp = codePoints[p];

                if (cp == '*' || cp == '/' || cp == '^') {
                    result = new ExpressionTokens.TimesDivCaretTok(p, 1, (char) cp);
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "variable-token". */
    public static final VariablePattern VARIABLE = new VariablePattern();

    /**
     * A pattern that matches a "variable-token".
     */
    public static final class VariablePattern implements ILexicalPattern {

        /**
         * Constructs a new {@code VariablePattern}.
         */
        private VariablePattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.VariableTok match(final int[] codePoints, final int p) {

            ExpressionTokens.VariableTok result = null;

            if ((p + 2 < codePoints.length) && (codePoints[p] == '{')) {
                int close = -1;
                for (int i = p + 1; i < codePoints.length; ++i) {
                    if (codePoints[i] == '}') {
                        close = i;
                        break;
                    }
                }

                final String ident = isIdentifier(codePoints, p + 1, close);
                if (ident != null) {
                    result = new ExpressionTokens.VariableTok(p, close - p + 1, ident);
                }
            } else if (p < codePoints.length && isLetter(codePoints[p])) {
                // Variable name without {} wrapper
                int count = 1;
                while (p + count < codePoints.length) {
                    final int cp = codePoints[p + count];
                    if (cp == '_' || isLetter(cp) || isDigit(cp)) {
                        ++count;
                    } else {
                        break;
                    }
                }
                final String ident = isIdentifier(codePoints, p, p + count);
                if (ident != null) {
                    result = new ExpressionTokens.VariableTok(p, count, ident);
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "function-of-1-token". */
    public static final FunctionOf1Pattern FUNCTION_OF_1 = new FunctionOf1Pattern();

    /**
     * A pattern that matches a "function-of-1-token".
     */
    public static final class FunctionOf1Pattern implements ILexicalPattern {

        /**
         * Constructs a new {@code FunctionOf1Pattern}.
         */
        private FunctionOf1Pattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.FunctionOf1Tok match(final int[] codePoints, final int p) {

            ExpressionTokens.FunctionOf1Tok result = null;

            // Find the next '(' character
            int lParen = -1;
            for (int i = p; i < codePoints.length; ++i) {
                if (codePoints[i] == '(') {
                    lParen = i;
                    break;
                }
            }

            if (lParen != -1) {
                final int nameLen = lParen - p;

                if (nameLen == 3) {
                    if (codePoints[p] == 'a' && codePoints[p + 1] == 'b' && codePoints[p + 2] == 's') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.ABS);
                    } else if (codePoints[p] == 'c' && codePoints[p + 1] == 'o' && codePoints[p + 2] == 's') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.COS);
                    } else if (codePoints[p] == 'e' && codePoints[p + 1] == 'x' && codePoints[p + 2] == 'p') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.EXP);
                    } else if (codePoints[p] == 'l' && codePoints[p + 1] == 'o' && codePoints[p + 2] == 'g') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.LOG);
                    } else if (codePoints[p] == 's' && codePoints[p + 1] == 'i' && codePoints[p + 2] == 'n') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.SIN);
                    } else if (codePoints[p] == 't' && codePoints[p + 1] == 'a' && codePoints[p + 2] == 'n') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 4, EFunctionOf1.TAN);
                    }
                } else if (nameLen == 4) {
                    if (codePoints[p] == 'a') {
                        if (codePoints[p + 1] == 'c' && codePoints[p + 2] == 'o' && codePoints[p + 3] == 's') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.ACOS);
                        } else if (codePoints[p + 1] == 's' && codePoints[p + 2] == 'i' && codePoints[p + 3] == 'n') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.ASIN);
                        } else if (codePoints[p + 1] == 't' && codePoints[p + 2] == 'a' && codePoints[p + 3] == 'n') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.ATAN);
                        }
                    } else if (codePoints[p] == 'c' && codePoints[p + 1] == 'b' && codePoints[p + 2] == 'r'
                            && codePoints[p + 3] == 't') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.CBRT);
                    } else if (codePoints[p] == 'l' && codePoints[p + 1] == 'o' && codePoints[p + 2] == 'g'
                            && codePoints[p + 3] == '2') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.LOG2);
                    } else if (codePoints[p] == 's' && codePoints[p + 1] == 'q' && codePoints[p + 2] == 'r'
                            && codePoints[p + 3] == 't') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 5, EFunctionOf1.SQRT);
                    }
                } else if (nameLen == 5) {
                    if (codePoints[p] == 'e' && codePoints[p + 1] == 'x' && codePoints[p + 2] == 'p' && codePoints[p + 3] == 'm'
                            && codePoints[p + 4] == '1') {
                        result = new ExpressionTokens.FunctionOf1Tok(p, 6, EFunctionOf1.EXPM1);
                    } else if (codePoints[p] == 'l') {
                        if (codePoints[p + 1] == 'o' && codePoints[p + 2] == 'g' && codePoints[p + 3] == '1'
                                && codePoints[p + 4] == 'p') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 6, EFunctionOf1.LOG1P);
                        } else if (codePoints[p + 1] == 'o' && codePoints[p + 2] == 'g' && codePoints[p + 3] == '1'
                                && codePoints[p + 4] == '0') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 6, EFunctionOf1.LOG10);
                        }
                    } else if (codePoints[p] == 't' && codePoints[p + 1] == 'o') {
                        if (codePoints[p + 2] == 'D' && codePoints[p + 3] == 'e' && codePoints[p + 4] == 'g') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 6, EFunctionOf1.TO_DEG);
                        } else if (codePoints[p + 2] == 'R' && codePoints[p + 3] == 'a' && codePoints[p + 4] == 'd') {
                            result = new ExpressionTokens.FunctionOf1Tok(p, 6, EFunctionOf1.TO_RAD);
                        }
                    }
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "function-of-2-token". */
    public static final FunctionOf2Pattern FUNCTION_OF_2 = new FunctionOf2Pattern();

    /**
     * A pattern that matches a "function-of-2-token".
     */
    public static final class FunctionOf2Pattern implements ILexicalPattern {

        /**
         * Constructs a new {@code FunctionOf2Pattern}.
         */
        private FunctionOf2Pattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.FunctionOf2Tok match(final int[] codePoints, final int p) {

            ExpressionTokens.FunctionOf2Tok result = null;

            // Find the next '(' character
            int lParen = -1;
            for (int i = p; i < codePoints.length; ++i) {
                if (codePoints[i] == '(') {
                    lParen = i;
                    break;
                }
            }

            if (lParen != -1) {
                final int nameLen = lParen - p;

                if (nameLen == 3) {
                    if (codePoints[p] == 'p' && (codePoints[p + 1] == 'o' && codePoints[p + 2] == 'w')) {
                        result = new ExpressionTokens.FunctionOf2Tok(p, 4, EFunctionOf2.POW);
                    }
                } else if (nameLen == 5) {
                    if (codePoints[p] == 'a' && codePoints[p + 1] == 't'
                            && codePoints[p + 2] == 'a' && codePoints[p + 3] == 'n' && codePoints[p + 4] == '2') {
                        result = new ExpressionTokens.FunctionOf2Tok(p, 6, EFunctionOf2.ATAN2);
                    } else if (codePoints[p] == 'h' && codePoints[p + 1] == 'y'
                            && codePoints[p + 2] == 'p' && codePoints[p + 3] == 'o' && codePoints[p + 4] == 't') {
                        result = new ExpressionTokens.FunctionOf2Tok(p, 6, EFunctionOf2.HYPOT);
                    }
                }
            }

            return result;
        }
    }

    /** The pattern that matches a "symbol-token". */
    public static final SymbolPattern SYMBOL = new SymbolPattern();

    /**
     * A pattern that matches a "symbol-token".
     */
    public static final class SymbolPattern implements ILexicalPattern {

        /**
         * Constructs a new {@code SymbolPattern}.
         */
        private SymbolPattern() {

            // No action
        }

        /**
         * Attempts to match a pattern and produce a token. The token corresponding to the longest sequence of
         * characters that matches an allowed pattern is returned. Null is returned if no allowed pattern matches the
         * string at the specified position.
         *
         * @param codePoints the string, as a sequence of Unicode code points
         * @param p          the start position in the string
         * @return the token; null if none matched (or {@code pos} lies beyond the end of the string)
         */
        @Override
        public ExpressionTokens.SymbolTok match(final int[] codePoints, final int p) {

            ExpressionTokens.SymbolTok result = null;

            if (p < codePoints.length) {
                final int cp = codePoints[p];

                if (cp == 0x03C0) { // GREEK SMALL LETTER PI
                    result = new ExpressionTokens.SymbolTok(p, 1, ESymbol.PI);
                }
            }

            return result;
        }
    }

    /** The list of all expression lexical patterns, to passed to the Lexer. */
    public static final List<ILexicalPattern> EXPRESSION_PATTERNS = List.of(WHITESPACE, NUMBER, LPAREN, RPAREN,
            PLUS_MINUS, COMMA, TIMES_DIV_CARET, VARIABLE, FUNCTION_OF_1, FUNCTION_OF_2, SYMBOL);
}
