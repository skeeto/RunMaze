package com.nullprogram.maze;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

import java.awt.Shape;

public abstract class Cell {

    public static final Mark END = Mark.get("end");

    private Set<Mark> marks = new HashSet<Mark>(4);

    /**
     * Returns a Shape that describes a cell.
     * @return a Shape describing the border of the cell.
     */
    public abstract Shape getShape();

    /**
     * Return a Shape that describes just the walls of the cell.
     * @return a shape describing the walls of the cell
     */
    public abstract Shape getWalls();

    /**
     * Return a collection of connecting cells.
     * @return a collection of cell connected to this one
     */
    public abstract Collection<Cell> neighbors();

    /**
     * Mark this cell with the given marker.
     * @param marker  the marker to be added to this cell
     */
    public final void mark(final Mark marker) {
        marks.add(marker);
    }

    /**
     * Return true if this cell has been marked with the given marker.
     * @param marker  the marker to be tested
     * @return true if this cell has been marked with this marker
     */
    public final boolean hasMark(final Mark marker) {
        return marks.contains(marker);
    }
}
