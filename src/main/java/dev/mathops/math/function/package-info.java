/**
 * Pre-defined mathematical functions and interfaces for various function types.
 *
 * <p>
 * A function can be real-valued, complex-valued, real-vector-valued, or complex-vector-valued, and can accept an
 * argument that is a real or complex number or a real opr complex vector.  The "signature" of the function defines the
 * input and output types and number of components in both input and output vectors.
 *
 * <p>
 * Functions may be differentiable, in which case they can compute their derivative functions (or partial derivative
 * functions, in the case of functions over a vector domain).  Such functions implement the "IDifferentiable" interface.
 * A function of a real domain may also be integrable, in which case it can compute a representative antiderivative
 * function that can be used to evaluate definite integrals.  Such functions implement the "IIntegrable" interface.
 */
package dev.mathops.math.function;
