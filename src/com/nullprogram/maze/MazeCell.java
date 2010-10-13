package com.nullprogram.maze;

/* MazeCell - a single maze cell. Each cell has a top wall and a left
   wall and a marker indicating if the cell is part of the final maze
   yet (or not). */
class MazeCell {
    public boolean top;  // Inidicates if cell has a top wall
    public boolean left; // Inidicates if cell has a left wall
    public boolean used; // Cell id number.

    /* Used in solving the maze */
    public boolean solveMark;  // Cell is part of solution
    public boolean solveError; // Cell is part of error

    public MazeCell() {
        this.top  = true;
        this.left = true;
        this.used = false;

        /* Used by the solver */
        this.solveMark  = false;
        this.solveError = false;
    }
}
