package com.nullprogram.maze;

/* For when something needs to know the solver is done. */
public interface SolverListener {

    /**
     * Called when the maze has been successfully solved.
     */
    void solveDone();

    /**
     * Called when the potential solution has been updated.
     */
    void solveStep();
}
