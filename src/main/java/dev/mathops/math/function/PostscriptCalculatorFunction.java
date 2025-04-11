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

package dev.mathops.math.function;

import dev.mathops.math.function.op.OperatorException;
import dev.mathops.math.function.op.OperatorStack;
import dev.mathops.math.function.op.PostScriptExpression;
import dev.mathops.math.set.number.RealInterval;
import dev.mathops.text.ByteQueue;
import dev.mathops.commons.log.Log;

/**
 * A PostScript calculator (Type 4) function, which can take any number of inputs and generate any number of outputs.
 */
public class PostscriptCalculatorFunction extends AbstractFunction implements IPdfFunction {

    /** The opcodes. */
    private final byte[] opcodes;

    /** The parsed expression. */
    private final PostScriptExpression expression;

    /**
     * Constructs a new {code PostscriptCalculatorFunction}.
     *
     * @param theNumInputs  the number of inputs (M) this function accepts
     * @param theNumOutputs the number of outputs (N) this function generates
     * @param theDomain     an array of 2M numbers giving the domain of each input value
     * @param theRange      an array of 2N numbers giving the range of each output value
     * @param theOpcodes    the opcode data (per the PDF specification)
     * @throws IllegalArgumentException of the opcodes array is null or empty or is not a valid expression
     */
    public PostscriptCalculatorFunction(final int theNumInputs, final int theNumOutputs, final RealInterval[] theDomain,
                                        final RealInterval[] theRange, final byte[] theOpcodes)
            throws IllegalArgumentException {

        super(theNumInputs, theNumOutputs, theDomain, theRange);

        if (theOpcodes == null || theOpcodes.length == 0) {
            throw new IllegalArgumentException("Opcodes array may not be null or empty");
        }

        this.opcodes = theOpcodes.clone();
        this.expression = new PostScriptExpression(new ByteQueue(theOpcodes));
    }

    /**
     * Gets the sequence of commands.
     *
     * @return the commands
     */
    public final byte[] getOpcodes() {

        return this.opcodes.clone();
    }

    /**
     * Evaluates the function.
     *
     * @param arguments the arguments (the length of this array must match the number of inputs)
     * @return the result (an array whose length is the number of outputs)
     * @throws IllegalArgumentException if {code arguments} is null or not the correct length
     */
    @Override
    public final double[] evaluate(final double... arguments) {

        if (arguments == null || arguments.length != getNumInputs()) {
            throw new IllegalArgumentException("Number of arguments does not match number of inputs");
        }

        // Input variable values are initial stack
        final OperatorStack stack = new OperatorStack();
        for (final double d : arguments) {
            stack.pushDouble(d);
        }

        try {
            this.expression.execute(stack);
        } catch (final OperatorException ex) {
            throw new IllegalArgumentException("Unable to evaluate expression", ex);
        }

        // At the end, what remains on the stack is output values
        final int numOutputs = getNumOutputs();
        final double[] result = new double[numOutputs];

        final int stackSize = stack.size();
        if (stack.size() == numOutputs) {
            for (int i = 0; i < numOutputs; ++i) {
                final Object popped = stack.poll();

                if (popped instanceof Double doublePopped) {
                    result[i] = doublePopped.doubleValue();
                } else {
                    Log.warning("TYPE ERROR: Incorrect result type on stack.");
                }
            }
        } else if (stackSize < numOutputs) {
            Log.warning("STACK UNDERFLOW ERROR: Too few results on stack.");
        } else {
            Log.warning("STACK OVERFLOW ERROR: Too many results on stack.");
        }

        return result;
    }

    /**
     * Differentiates the function.
     *
     * @param index the index of the domain variable with respect to which to differentiate
     * @return the derivative function
     */
    @Override
    public IFunction differentiate(int index) {

        // TODO: seems hard!

        return null;
    }

    /**
     * Generates a string representation of the expression.
     *
     * @return the string representation
     */
    @Override
    public String toString() {

        // TODO:

        return "PostScriptCalculatorFunction{}";
    }
}
