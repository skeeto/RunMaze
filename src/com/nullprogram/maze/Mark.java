package com.nullprogram.maze;

import java.util.Map;
import java.util.HashMap;

/**
 * Cell metadata for generating and solving mazes. Marks are interned
 * so that they can be compared with the equality operator (==).
 */
public final class Mark {

    private static final Map<String, Mark> TABLE = new HashMap<String, Mark>();

    private final String name;

    /**
     * Create a new mark with the given name.
     * @param markName  name of the new mark
     */
    private Mark(final String markName) {
        name = markName;
    }

    /**
     * Create or fetch the mark with the given name. This function is
     * thread-safe.
     * @param name  name of the mark being requested
     * @return the requested mark
     */
    public static synchronized Mark get(final String name) {
        Mark mark = TABLE.get(name);
        if (mark == null) {
            mark = new Mark(name);
            TABLE.put(name, mark);
        }
        return mark;
    }
}
