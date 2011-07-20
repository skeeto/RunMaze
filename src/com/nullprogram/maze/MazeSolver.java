package com.nullprogram.maze;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Solves a maze asynchronously at a leisurely pace.
 */
class MazeSolver implements Runnable {

    public static final Mark ERROR_MARK = Mark.get("error");
    public static final Mark SOLUTION_MARK = Mark.get("solution");

    private final Maze maze;
    private final int sleepTime;

    private volatile boolean enabled;
    private Collection<SolverListener> listeners
    = new CopyOnWriteArrayList<SolverListener>();

    /**
     * Creates a new solver for the given maze.
     * @param puzzle  the maze to be solved
     * @param sleep  amount of time to sleep between steps
     */
    public MazeSolver(final Maze puzzle, final int sleep) {
        maze = puzzle;
        sleepTime = sleep;
        (new Thread(this)).start();
    }

    /**
     * Start the solver thread.
     */
    public void start() {
        enabled = true;
    }

    /**
     * Pause the solution thread.
     */
    public void stop() {
        enabled = false;
    }

    @Override
    public void run() {
        Deque<Cell> stack = new ArrayDeque<Cell>();
        stack.push(maze.start());
        stack.peek().mark(SOLUTION_MARK);

        while (!stack.peek().hasMark(Cell.END)) {
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                return;
            }
            if (!enabled) {
                continue;
            }

            Cell current = stack.peek();
            Collection<Cell> dirs = current.neighbors();

            boolean found = false;
            for (Cell next : dirs) {
                if (next.hasMark(ERROR_MARK) || next.hasMark(SOLUTION_MARK)) {
                    continue;
                }
                stack.push(next);
                next.mark(SOLUTION_MARK);
                found = true;
                break;
            }
            if (!found) {
                stack.pop().mark(ERROR_MARK);
            }
            for (SolverListener listener : listeners) {
                listener.solveStep();
            }
        }
        for (SolverListener listener : listeners) {
            listener.solveDone();
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
