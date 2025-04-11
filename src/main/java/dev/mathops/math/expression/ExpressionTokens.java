package dev.mathops.math.expression;

import dev.mathops.text.lexparse.AbstractToken;

/**
 * The tokens of the expression lexical grammar.
 */
public class ExpressionTokens {

    /**
     * A "whitespace-token".
     */
    public static class WhitespaceTok extends AbstractToken {

        /**
         * Constructs a new {@code WhitespaceTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         */
        public WhitespaceTok(final int theStartPosition, final int theLength) {

            super(theStartPosition, theLength);
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[space]";
        }
    }

    /**
     * A "number-token".
     */
    public static class NumberTok extends AbstractToken {

        /** The double value. */
        public final Double value;

        /**
         * Constructs a new {@code NumberTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theDoubleValue   the double value
         */
        public NumberTok(final int theStartPosition, final int theLength, final Double theDoubleValue) {

            super(theStartPosition, theLength);

            this.value = theDoubleValue;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.value + "]";
        }
    }

    /**
     * A "lparen-token".
     */
    public static class LParenTok extends AbstractToken {

        /**
         * Constructs a new {@code LParenTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         */
        public LParenTok(final int theStartPosition, final int theLength) {

            super(theStartPosition, theLength);
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[(]";
        }
    }

    /**
     * A "rparen-token".
     */
    public static class RParenTok extends AbstractToken {

        /**
         * Constructs a new {@code RParenTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         */
        public RParenTok(final int theStartPosition, final int theLength) {

            super(theStartPosition, theLength);
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[)]";
        }
    }

    /**
     * A "comma-token".
     */
    public static class CommaTok extends AbstractToken {

        /**
         * Constructs a new {@code CommaTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         */
        public CommaTok(final int theStartPosition, final int theLength) {

            super(theStartPosition, theLength);
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[,]";
        }
    }

    /**
     * A "caret-token".
     */
    public static class CaretTok extends AbstractToken {

        /**
         * Constructs a new {@code CaretTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         */
        public CaretTok(final int theStartPosition, final int theLength) {

            super(theStartPosition, theLength);
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[^]";
        }
    }

    /**
     * A "plus-minus-token".
     */
    public static class PlusMinusTok extends AbstractToken {

        /** A '+' synthetic token. */
        public static final PlusMinusTok PLUS = new PlusMinusTok('+');

        /** A '-' synthetic token. */
        public static final PlusMinusTok MINUS = new PlusMinusTok('-');

        /** The operator ('+' or '-'). */
        public final char op;

        /**
         * Constructs a new synthetic {@code PlusMinusTok}.
         *
         * @param theOp the operator ('+' or '-')
         */
        private PlusMinusTok(final char theOp) {

            this(0, 0, theOp);
        }

        /**
         * Constructs a new {@code PlusMinusTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theOp            the operator ('+' or '-')
         */
        public PlusMinusTok(final int theStartPosition, final int theLength, final char theOp) {

            super(theStartPosition, theLength);

            if ((int) theOp != '+' && (int) theOp != '-') {
                throw new IllegalArgumentException("Invalid argument");
            }

            this.op = theOp;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.op + "]";
        }
    }

    /**
     * A "times-div-caret-token".
     */
    public static class TimesDivCaretTok extends AbstractToken {

        /** A '*' synthetic token. */
        public static final TimesDivCaretTok TIMES = new TimesDivCaretTok('*');

        /** A '/' synthetic token. */
        public static final TimesDivCaretTok DIV = new TimesDivCaretTok('/');

        /** A '^' synthetic token. */
        public static final TimesDivCaretTok CARET = new TimesDivCaretTok('^');

        /** The operator ('*' or '/' or '^'). */
        public final char op;

        /**
         * Constructs a new synthetic {@code TimesDivCaretTok}.
         *
         * @param theOp the operator ('*' or '/' or '^')
         */
        private TimesDivCaretTok(final char theOp) {

            this(0, 0, theOp);
        }

        /**
         * Constructs a new {@code TimesDivCaretTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theOp            the operator ('*' or '/' or '^')
         */
        public TimesDivCaretTok(final int theStartPosition, final int theLength, final char theOp) {

            super(theStartPosition, theLength);

            if ((int) theOp != '*' && (int) theOp != '/' && (int) theOp != '^') {
                throw new IllegalArgumentException("Invalid argument");
            }

            this.op = theOp;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.op + "]";
        }
    }

    /**
     * A "variable-token".
     */
    public static class VariableTok extends AbstractToken {

        /** The variable name. */
        public final String name;

        /**
         * Constructs a new {@code VariableTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theName          the name
         */
        public VariableTok(final int theStartPosition, final int theLength, final String theName) {

            super(theStartPosition, theLength);

            this.name = theName;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[{" + this.name + "}]";
        }
    }

    /**
     * A "function-r-token".
     */
    public static class FunctionOf1Tok extends AbstractToken {

        /** The function. */
        public final EFunctionOf1 function;

        /**
         * Constructs a new {@code FunctionRTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theFunction      the function
         */
        public FunctionOf1Tok(final int theStartPosition, final int theLength, final EFunctionOf1 theFunction) {

            super(theStartPosition, theLength);

            this.function = theFunction;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.function.name() + "(]";
        }
    }

    /**
     * A "function-of-2-token".
     */
    public static class FunctionOf2Tok extends AbstractToken {

        /** The function. */
        public final EFunctionOf2 function;

        /**
         * Constructs a new {@code FunctionOf2Tok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theFunction      the function
         */
        public FunctionOf2Tok(final int theStartPosition, final int theLength, final EFunctionOf2 theFunction) {

            super(theStartPosition, theLength);

            this.function = theFunction;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.function.name() + "(]";
        }
    }

    /**
     * A "symbol-token".
     */
    public static class SymbolTok extends AbstractToken {

        /** The symbol. */
        public final ESymbol symbol;

        /**
         * Constructs a new {@code SymbolTok}.
         *
         * @param theStartPosition the start position
         * @param theLength        the length
         * @param theSymbol        the symbol
         */
        public SymbolTok(final int theStartPosition, final int theLength, final ESymbol theSymbol) {

            super(theStartPosition, theLength);

            this.symbol = theSymbol;
        }

        /**
         * Generates a string representation of the token.
         *
         * @return the string representation
         */
        @Override
        public final String toString() {

            return "[" + this.symbol.name() + "]";
        }
    }
}
