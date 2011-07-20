package com.nullprogram.maze;

/**
 * A generic maze of unknown shape and properties. Cells are connected
 * together, describing the path of the maze. This cell network should
 * be created by the maze constructor, and the initial cell returned
 * with start().
 *
 * Iteration is provided for display purposes, to iterate over every
 * cell in the maze -- ordering being regardless of the cell's
 * connectedness.
 */
public interface Maze extends Iterable<Cell> {

    /**
     * The measured width of the maze. This is not the width of the
     * maze in terms of cells, but the displayed width. For example, A
     * rectangular maze that is 5 cells wide, where each cell is 5
     * units width, the maze would be 25 units wide.
     * @return the measured width of the maze
     */
    int getWidth();

    /**
     * The measured height of the maze. See getWidth().
     * @return the measured height of the maze
     */
    int getHeight();

    /**
     * Return the starting cell of this maze. Cells link to other
     * cells, and "playing" the maze is a matter of following those
     * The Iterator returned as part of the Iterable interface is just
     * for iterating through all the cells in any order exactly
     * once.
     * @return the starting cell of the maze
     */
    Cell start();
}
