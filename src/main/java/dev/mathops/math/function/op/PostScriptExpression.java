/*
 * Copyright (C) 2022 Steve Benoit
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the  License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If  not, see
 * <https://www.gnu.org/licenses/>.
 */

package dev.mathops.math.function.op;

import dev.mathops.text.ByteQueue;
import dev.mathops.text.builder.CharBuilder;
import dev.mathops.text.builder.CharHtmlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A PostScript expression (the subset of the PostScript language used in a "PostScript Calculator Function" within the
 * PDF specification).
 */
public final class PostScriptExpression extends AbstractOperator {

    /** The sequence of operators. */
    private final List<AbstractOperator> operators;

    /**
     * Constructs a new {code PostScriptExpression}.
     */
    public PostScriptExpression() {

        super("expression");

        this.operators = new ArrayList<>(20);
    }

    /**
     * Constructs a new {code PostScriptExpression} by parsing a sequence of bytes.
     *
     * @param queue the queue of bytes to parse
     * @throws IllegalArgumentException if the byte sequence cannot be parsed
     */
    public PostScriptExpression(final ByteQueue queue) throws IllegalArgumentException {

        super("expression");

        int next = queue.consume();
        if (next != (int) '{') {
            throw new IllegalArgumentException("Expression must begin with '{' byte");
        }

        this.operators = new ArrayList<>(20);

        next = queue.consume();
        while (next != (int) '}') {
            if (isWhitespace(next)) {
                next = queue.consume();
            } else {
                queue.reconsume();

                if (next == '+' || next == '-' || (next >= '0' && next <= '9')) {
                    consumeNumber(queue);
                    next = queue.consume();
                } else {
                    consumeOperator(queue);
                    next = queue.consume();
                }
            }
        }
    }

    /**
     * Tests whether a byte is "whitespace".
     *
     * @param next the character to test
     * @return true if whitespace
     */
    private static boolean isWhitespace(final int next) {

        return next == 0 || next == ' ' || next == '\t' || next == '\r' || next == '\n' || next == '\f';
    }

    /**
     * Consumes a number and adds a literal operator to the operator list.
     *
     * @param queue the queue of bytes to parse
     * @throws NumberFormatException if a number could not be parsed
     */
    private void consumeNumber(final ByteQueue queue) throws NumberFormatException {

        // Scan forward until whitespace or end of queue is found
        final int size = queue.size();
        int end = 0;
        while (end < size) {
            int test = queue.peekPlus(end);
            if (isWhitespace(test)) {
                break;
            }
            ++end;
        }

        if (end == 0) {
            throw new NumberFormatException("Empty byte sequence");
        }

        // Copy the sequence of bytes to parse, then convert to string
        final int[] toParse = new int[end];
        queue.peek(0, end, toParse);
        final String strToParse = new String(toParse, 0, end);

        try {
            final long parsedLong = Long.parseLong(strToParse);
            this.operators.add(new LiteralNumberOperator(parsedLong));
        } catch (final NumberFormatException ex1) {
            final double parsedDouble = Double.parseDouble(strToParse);
            this.operators.add(new LiteralNumberOperator(parsedDouble));
        }
    }

    /**
     * Consumes an operator and adds it to the operator list.
     *
     * @param queue the queue of bytes to parse
     * @throws IllegalArgumentException if an operator could not be parsed
     */
    private void consumeOperator(final ByteQueue queue) throws IllegalArgumentException {

        // Scan forward until whitespace or end of queue is found, skipping nested pairs of '{' and '}'
        final int size = queue.size();
        int end = 0;
        while (end < size) {
            int test = queue.peekPlus(end);
            if (isWhitespace(test)) {
                break;
            }
            ++end;
        }

        if (end == 0) {
            throw new IllegalArgumentException("Empty byte sequence");
        }

        // Copy the sequence of bytes to parse, then convert to string
        final int[] toParse = new int[end];
        queue.peek(0, end, toParse);
        queue.consume(end);

        if (toParse[0] == '{' && toParse[end - 1] == '}') {
            final ByteQueue subexpression = new ByteQueue(end);
            for (int i = 1; i < end - 1; ++i) {
                subexpression.append(toParse[i]);
            }
            final PostScriptExpression expr = new PostScriptExpression(subexpression);
            this.operators.add(expr);
        } else if (end == 2) {
            // 'eq', 'ge', 'gt', 'if', 'le', 'ln', 'lt', 'ne', 'or'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            if (c0 == 'e' && c1 == 'q') {
                this.operators.add(new EqOperator());
            } else if (c0 == 'g' && c1 == 'e') {
                this.operators.add(new GeOperator());
            } else if (c0 == 'g' && c1 == 't') {
                this.operators.add(new GtOperator());
            } else if (c0 == 'i' && c1 == 'f') {
                this.operators.add(new IfOperator());
            } else if (c0 == 'l' && c1 == 'e') {
                this.operators.add(new LeOperator());
            } else if (c0 == 'l' && c1 == 'n') {
                this.operators.add(new LnOperator());
            } else if (c0 == 'l' && c1 == 't') {
                this.operators.add(new LtOperator());
            } else if (c0 == 'n' && c1 == 'e') {
                this.operators.add(new NeOperator());
            } else if (c0 == 'o' && c1 == 'r') {
                this.operators.add(new OrOperator());
            } else {
                final String strToParse = new String(toParse, 0, 2);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 3) {
            // 'abs', 'add', 'and', 'cos', 'cvi', 'cvr', 'div', 'dup', 'exp', 'log', 'mod', 'mul', 'neg', 'not', 'pop',
            // 'sin', 'sub', 'xor'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            if (c0 == 'a' && c1 == 'b' && c2 == 's') {
                this.operators.add(new AbsOperator());
            } else if (c0 == 'a' && c1 == 'd' && c2 == 'd') {
                this.operators.add(new AddOperator());
            } else if (c0 == 'a' && c1 == 'n' && c2 == 'd') {
                this.operators.add(new AndOperator());
            } else if (c0 == 'c' && c1 == 'o' && c2 == 's') {
                this.operators.add(new CosOperator());
            } else if (c0 == 'c' && c1 == 'v' && c2 == 'i') {
                this.operators.add(new CviOperator());
            } else if (c0 == 'c' && c1 == 'v' && c2 == 'r') {
                this.operators.add(new CvrOperator());
            } else if (c0 == 'd' && c1 == 'i' && c2 == 'v') {
                this.operators.add(new DivOperator());
            } else if (c0 == 'd' && c1 == 'u' && c2 == 'p') {
                this.operators.add(new DupOperator());
            } else if (c0 == 'e' && c1 == 'x' && c2 == 'p') {
                this.operators.add(new ExpOperator());
            } else if (c0 == 'l' && c1 == 'o' && c2 == 'g') {
                this.operators.add(new LogOperator());
            } else if (c0 == 'm' && c1 == 'o' && c2 == 'd') {
                this.operators.add(new ModOperator());
            } else if (c0 == 'm' && c1 == 'u' && c2 == 'l') {
                this.operators.add(new MulOperator());
            } else if (c0 == 'n' && c1 == 'e' && c2 == 'g') {
                this.operators.add(new NegOperator());
            } else if (c0 == 'n' && c1 == 'o' && c2 == 't') {
                this.operators.add(new NotOperator());
            } else if (c0 == 'p' && c1 == 'o' && c2 == 'p') {
                this.operators.add(new PopOperator());
            } else if (c0 == 's' && c1 == 'i' && c2 == 'n') {
                this.operators.add(new SinOperator());
            } else if (c0 == 's' && c1 == 'u' && c2 == 'b') {
                this.operators.add(new SubOperator());
            } else if (c0 == 'x' && c1 == 'o' && c2 == 'r') {
                this.operators.add(new XorOperator());
            } else {
                final String strToParse = new String(toParse, 0, 3);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 4) {
            // 'atan', 'copy', 'exch', 'idiv', 'roll', 'sqrt', 'true'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            final int c3 = toParse[3];
            if (c0 == 'a' && c1 == 't' && c2 == 'a' && c3 == 'n') {
                this.operators.add(new AtanOperator());
            } else if (c0 == 'c' && c1 == 'o' && c2 == 'p' && c3 == 'y') {
                this.operators.add(new CopyOperator());
            } else if (c0 == 'e' && c1 == 'x' && c2 == 'c' && c3 == 'h') {
                this.operators.add(new ExchOperator());
            } else if (c0 == 'i' && c1 == 'd' && c2 == 'i' && c3 == 'v') {
                this.operators.add(new IdivOperator());
            } else if (c0 == 'r' && c1 == 'o' && c2 == 'l' && c3 == 'l') {
                this.operators.add(new RollOperator());
            } else if (c0 == 's' && c1 == 'q' && c2 == 'r' && c3 == 't') {
                this.operators.add(new SqrtOperator());
            } else if (c0 == 't' && c1 == 'r' && c2 == 'u' && c3 == 'e') {
                this.operators.add(new TrueOperator());
            } else {
                final String strToParse = new String(toParse, 0, 4);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 5) {
            // 'false', 'floor', 'index', 'round',
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            final int c3 = toParse[3];
            final int c4 = toParse[4];
            if (c0 == 'f' && c1 == 'a' && c2 == 'l' && c3 == 's' && c4 == 'e') {
                this.operators.add(new FalseOperator());
            } else if (c0 == 'f' && c1 == 'l' && c2 == 'o' && c3 == 'o' && c4 == 'r') {
                this.operators.add(new FloorOperator());
            } else if (c0 == 'i' && c1 == 'n' && c2 == 'd' && c3 == 'e' && c4 == 'x') {
                this.operators.add(new IndexOperator());
            } else if (c0 == 'r' && c1 == 'o' && c2 == 'u' && c3 == 'n' && c4 == 'd') {
                this.operators.add(new RoundOperator());
            } else {
                final String strToParse = new String(toParse, 0, 5);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 6) {
            // 'ifelse'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            final int c3 = toParse[3];
            final int c4 = toParse[4];
            final int c5 = toParse[5];
            if (c0 == 'i' && c1 == 'f' && c2 == 'e' && c3 == 'l' && c4 == 's' && c5 == 'e') {
                this.operators.add(new IfElseOperator());
            } else {
                final String strToParse = new String(toParse, 0, 6);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 7) {
            // 'ceiling'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            final int c3 = toParse[3];
            final int c4 = toParse[4];
            final int c5 = toParse[5];
            final int c6 = toParse[6];
            if (c0 == 'c' && c1 == 'e' && c2 == 'i' && c3 == 'l' && c4 == 'i' && c5 == 'n' && c6 == 'g') {
                this.operators.add(new CeilingOperator());
            } else {
                final String strToParse = new String(toParse, 0, 7);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else if (end == 8) {
            // 'bitshift', 'truncate'
            final int c0 = toParse[0];
            final int c1 = toParse[1];
            final int c2 = toParse[2];
            final int c3 = toParse[3];
            final int c4 = toParse[4];
            final int c5 = toParse[5];
            final int c6 = toParse[6];
            final int c7 = toParse[7];
            if (c0 == 'b' && c1 == 'i' && c2 == 't' && c3 == 's' && c4 == 'h' && c5 == 'i' && c6 == 'f' && c7 == 't') {
                this.operators.add(new BitshiftOperator());
            } else if (c0 == 't' && c1 == 'r' && c2 == 'u' && c3 == 'n' && c4 == 'c' && c5 == 'a' && c6 == 't'
                    && c7 == 'e') {
                this.operators.add(new TruncateOperator());
            } else {
                final String strToParse = new String(toParse, 0, 7);
                throw new IllegalArgumentException("Invalid operator: " + strToParse);
            }
        } else {
            final String strToParse = new String(toParse, 0, end);
            throw new IllegalArgumentException("Invalid operator: " + strToParse);
        }
    }

    /**
     * Gets the list of operators in this expression.
     *
     * @return the list of operators
     */
    public List<AbstractOperator> getOperators() {

        return this.operators;
    }

    /**
     * Executes the operator against a stack.
     *
     * @param stack the stack
     * @throws OperatorException if there was an error executing the operator
     */
    public void execute(final OperatorStack stack) throws OperatorException {

        for (final AbstractOperator op : this.operators) {
            op.execute(stack);
        }
    }

    /**
     * Generates a string representation of the operator.
     *
     * @return the representation
     */
    @Override
    public final String toString() {

        final int count = this.operators.size();

        final CharHtmlBuilder htm = new CharHtmlBuilder(30);
        htm.addChar('{');
        for (int i = 0; i < count; ++i) {
            if (i > 0) {
                htm.addChar(CharBuilder.SPC);
            }
            final AbstractOperator op = this.operators.get(i);
            final String opStr = op.toString();
            htm.addString(opStr);
        }
        htm.addChar('}');

        return htm.toString();
    }
}
