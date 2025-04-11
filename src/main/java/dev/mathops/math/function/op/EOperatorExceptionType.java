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

/**
 * A type associated with an {code OperatorException}.
 */
public enum EOperatorExceptionType {

    /** Stack underflow - not enough arguments on stack to execute operation. */
    STACK_UNDERFLOW,

    /** Type check - object on stack was not of expected type. */
    TYPE_CHECK,

    /** Undefined result - for example, when dividing by zero. */
    UNDEFINED_RESULT,

    /** Range check - an argument was outside an allowed range. */
    RANGE_CHECK,

    /** Syntax error - parsing an argument failed. */
    SYNTAX_ERROR,
}
