package com.nullprogram.maze;

/**
 * Interface for subscribing to solver events.
 */
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
