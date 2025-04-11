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

package dev.mathops.math.geom;

/** The type of a tuple. */
public enum ETupleType {

    /** A point (points are changed by translations). */
    POINT,

    /** A vector (vectors are not changed by translations). */
    VECTOR,

    /** A normal vector (normal vectors transform differently than points or vectors). */
    NORMAL_VECTOR;
}
