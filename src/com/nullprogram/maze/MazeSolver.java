package com.nullprogram.maze;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Solves a maze asynchronously at a leisurely pace.
 */
class MazeSolver implements Runnable {

    private final Maze maze;
    private final int sleepTime;

    private volatile boolean enabled;
    private Collection<SolverListener> listeners
        = new CopyOnWriteArrayList<SolverListener>();

    /* Solution variables */
    private Deque<Position> solveStack = new ArrayDeque<Position>();
    private Position mazeEnd;

    /**
     * Creates a new solver for the given maze.
     * @param puzzle  the maze to be solved
     * @param sleep  amount of time to sleep between steps
     */
    public MazeSolver(final Maze puzzle, final int sleep) {
        maze = puzzle;
        sleepTime = sleep;
        mazeEnd = new Position(maze.getWidth() - 1,
                                  maze.getHeight() - 1);
        solveStack.push(new Position(0, 0));
        start();
    }

    /**
     * Start the solver thread.
     */
    public void start() {
        if (!enabled) {
            enabled = true;
            (new Thread(this)).start();
        }
    }

    /**
     * Pause the solution thread.
     */
    public void stop() {
        enabled = false;
    }

    @Override
    public void run() {
        Position point;

        /* Solve one step of the maze, sleep, check for halt, repeat */
        do {
            point = solveStack.peek();

            /* Mark the current point location */
            maze.markSolution(point);

            /* Decide which directon to go next */
            int x = point.getX();
            int y = point.getY();
            Position upCell = new Position(x, y - 1);
            Position downCell = new Position(x, y + 1);
            Position leftCell = new Position(x - 1, y);
            Position rightCell = new Position(x + 1, y);

            /* Push next move onto the stack */
            if (!maze.topWall(point) && !maze.marked(upCell)) {
                solveStack.push(upCell);
            } else if (!maze.leftWall(rightCell)
                       && !maze.marked(rightCell)) {
                solveStack.push(rightCell);
            } else if (!maze.topWall(downCell)
                       && !maze.marked(downCell)) {
                solveStack.push(downCell);
            } else if (!maze.leftWall(point)
                       && !maze.marked(leftCell)) {
                solveStack.push(leftCell);
            } else {
                maze.markError(point);
                solveStack.pop();
            }

            for (SolverListener listener : listeners) {
                listener.solveStep();
            }

            /* Wait for some time time */
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                return;
            }

            /* If next point location is the end, we are done. */
        } while (enabled && !point.equals(mazeEnd));

        if (point.equals(mazeEnd)) {
            for (SolverListener listener : listeners) {
                listener.solveDone();
            }
        }
    }

    /**
     * Subscribe a new SolverListener to this solver.
     * @param listener  the new subscriber
     */
    public final void addListener(final SolverListener listener) {
        listeners.add(listener);
    }
}
