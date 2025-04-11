/*
 * Copyright (C) 2022 Steve Benoit
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the  License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more
 * details.
 *
 *  You should have received a copy of the GNU General Public License along with this program. If  not, see
 * <https://www.gnu.org/licenses/>.
 */

package dev.mathops.math.geom;

import dev.mathops.commons.res.ResBundle;

import java.util.Locale;

/**
 * Localized resources.
 */
final class Res extends ResBundle {

    // Used by Tuple2D, Tuple3D

    /** A resource key. */
    static final String BAD_COORDINATES;

    /** A resource key. */
    static final String NULL_TUPLE_TYPE;

    // Used by Tuple2D, Tuple3D, Tuple2L, Tuple3L

    /** A resource key. */
    static final String BAD_TUPLE_STR;

    // Used by Rect2D, Rect2L

    /** A resource key. */
    static final String BAD_RECT_STR;


    static {
        int index = 1;
        BAD_COORDINATES = key(index);
        ++index;
        NULL_TUPLE_TYPE = key(index);
        ++index;
        BAD_TUPLE_STR = key(index);
        ++index;
        BAD_RECT_STR = key(index);
    }

    //

    /** The resources - an array of key-values pairs. */
    private static final String[][] EN_US = { //
            {BAD_COORDINATES, "Coordinates must be finite"},
            {NULL_TUPLE_TYPE, "Tuple type may not be null"},
            {BAD_TUPLE_STR, "Invalid tuple string: {0}"},
            {BAD_RECT_STR, "Invalid rectangle string: {0}"},

            //
    };

    /** The singleton instance. */
    private static final Res instance = new Res();

    /**
     * Private constructor to prevent direct instantiation.
     */
    private Res() {

        super(Locale.US, EN_US);
    }

    /**
     * Gets the message with a specified key using the current locale.
     *
     * @param key the message key
     * @return the best-matching message, an empty string if none is registered (never {@code null})
     */
    static String get(final String key) {

        return instance.getMsg(key);
    }

    /**
     * Retrieves the message with a specified key, then uses a {@code MessageFormat} to format that message pattern with
     * a collection of arguments.
     *
     * @param key       the message key
     * @param arguments the arguments, as for {@code MessageFormat}
     * @return the formatted string (never {@code null})
     */
    static String fmt(final String key, final Object... arguments) {

        return instance.formatMsg(key, arguments);
    }
}
