package dev.mathops.math.expression;

import dev.mathops.commons.log.Log;
import dev.mathops.text.lexparse.EOSToken;
import dev.mathops.text.lexparse.IToken;
import dev.mathops.text.lexparse.Lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser that generates an expression object from a string.
 */
public class ExpressionParser {

    /**
     * Private constructor to prevent instantiation.
     */
    private ExpressionParser() {

        // No action
    }

    /**
     * Parses a real expression.
     *
     * @param str the string to parse
     * @return the expression; null if a parsing error occurs
     */
    public static Expr parseExpr(final String str) {

        Expr result = null;

        Log.info("Parsing ", str);

        final Lexer lexer = new Lexer(str, ExpressionLexicalGrammar.EXPRESSION_PATTERNS);
        final List<IToken> tokens = new ArrayList<>(10);

        Log.info("Scanning");

        if (lexer.scan(tokens)) {
            int len = tokens.size();
            if (len > 0 && tokens.get(len - 1) instanceof EOSToken) {
                tokens.remove(len - 1);
                --len;
            }

            Log.info("Scanned - found " + len + " tokens (excluding EOS)");

            final Expr expr = ExpressionSyntacticGrammar.REAL_EXPR.match(tokens, 0);

            if (expr != null) {
                Log.info("Expression grammar matched " + expr.getLength() + " tokens");

                if (expr.getLength() == tokens.size()) {
                    result = expr;
                }
            }
        }

        return result;
    }
}
