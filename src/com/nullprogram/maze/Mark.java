package com.nullprogram.maze;

import java.util.Map;
import java.util.HashMap;

public class Mark {

    private static final Map<String, Mark> table = new HashMap<String, Mark>();

    private final String name;

    private Mark(String markName) {
        name = markName;
    }

    public static final synchronized Mark get(String name) {
        Mark mark = table.get(name);
        if (mark == null) {
            mark = new Mark(name);
            table.put(name, mark);
        }
        return mark;
    }
}
