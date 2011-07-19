package com.nullprogram.maze;

/**
 * A single cell in a maze. Each cell has a top wall, a left wall, two
 * solution markers, and a marker indicating if the cell is part of
 * the final maze yet.
 */
class MazeCell {
    private boolean top = true;
    private boolean left = true;
    private boolean used = false;

    /** Indicates cell is part of a solution path. */
    private boolean solution = false;

    /** Indicates cell was part of an error solution. */
    private boolean error = false;

    /**
     * Returns true if cell is part of an error path.
     * @return true if cell is part of an error path
     */
    public final boolean isError() {
        return error;
    }

    /**
     * Returns true if cell is part of a solution path.
     * @return true if cell is part of a solution path
     */
    public final boolean isSolution() {
        return solution;
    }

    /**
     * Returns true if cell has a top wall.
     * @return true if cell has a top wall
     */
    public final boolean hasTopWall() {
        return top;
    }

    /**
     * Returns true if cell has a left wall.
     * @return true if cell has a left wall
     */
    public final boolean hasLeftWall() {
        return left;
    }

    /**
     * Returns true if cell has been used in the current maze.
     * @return true if cell has been used in the current maze.
     */
    public final boolean isUsed() {
        return used;
    }

    /**
     * Mark this cell as a solution cell.
     */
    public final void markSolution() {
        solution = true;
    }

    /**
     * Mark this cell as an error cell.
     */
    public final void markError() {
        solution = false;
        error = true;
    }

    /**
     * Remove this cell's left wall.
     */
    public final void breakLeft() {
        left = false;
    }

    /**
     * Remove this cell's right wall.
     */
    public final void breakTop() {
         top = false;
    }

    /**
     * Mark this cell as used.
     */
    public final void markUsed() {
        used = true;
    }
}
