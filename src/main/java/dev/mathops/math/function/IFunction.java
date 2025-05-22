package dev.mathops.math.function;

import dev.mathops.math.set.number.RealInterval;

/**
 * A real-valued function of a real argument whose domain consists of a simple real interval.
 */
public interface IFunction {

    /**
     * Gets the number of inputs this function accepts.
     *
     * @return the number of inputs
     */
    int getNumInputs();

    /**
     * Gets the number of outputs this function produces.
     *
     * @return the number of outputs
     */
    int getNumOutputs();

    /**
     * Gets the domain of one input variable.
     *
     * @param inputIndex the input index (from 0 to one less than the value provided in the constructor)
     * @return the variable domain ({code null} if not yet set)
     */
    RealInterval getDomain(int inputIndex);

    /**
     * Gets the range of one output variable.
     *
     * @param outputIndex the output index (from 0 to one less than the value provided in the constructor)
     * @return the variable range ({code null} if not yet set)
     */
    RealInterval getRange(int outputIndex);

    /**
     * Evaluates the function.
     *
     * @param arguments the arguments (the length of this array must match the number of inputs)
     * @return the result (an array whose length is the number of outputs)
     * @throws IllegalArgumentException if {code arguments} is null or not the correct length
     */
    double[] evaluate(double... arguments);

    /**
     * Returns the partial derivative of this function with respect to one if its independent variables.
     *
     * @param index the index of the domain variable with respect to which to differentiate
     * @return the partial derivative function
     */
    IFunction differentiate(int index);

    /**
     * Clamps a value to a range.
     *
     * @param value the value to clamp
     * @param min   the minimum clamped value
     * @param max   the maximum clamped value
     * @return the clamped value
     */
    static double clampToRange(final double value, final Number min, final Number max) {

        final double boundedAbove = Math.min(value, max.doubleValue());

        return Math.max(min.doubleValue(), boundedAbove);
    }

    /**
     * Performs linear interpolation.
     *
     * @param x    the x value
     * @param xMin the minimum x value
     * @param xMax the maximum x value
     * @param yMin the minimum y value
     * @param yMax the maximum y value
     * @return the interpolated value, yMin + (x - xMin) [ (yMax - yMin) / (xMax -xMin) ]
     */
    static double interpolate(final double x, final double xMin, final double xMax, final double yMin,
                              final double yMax) {

        final double dy = yMax - yMin;
        final double dx = xMax - xMin;
        final double slope = dy / dx;

        return yMin + slope * (x - xMin);
    }
}
