package com.nullprogram.maze;

/* For when something needs to know the solver is done. */
public interface SolveListener {

    /* Called when the solver is done. */
    void solveDone();
}
