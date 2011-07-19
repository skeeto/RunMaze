package com.nullprogram.maze;

import java.util.Stack;
import java.util.ArrayList;

/* MazeSolve - solves the given maze and updates it on the given
   display. */
class MazeSolve implements Runnable {
    /* Behavior variables */
    private Maze        maze;
    private MazeDisplay thisDisplay;
    private Thread      solveThread;
    private int         sleepTime;

    private volatile boolean enabled;
    private ArrayList<SolveListener> listeners;

    /* Solution variables */
    private Stack<OrderedPair> solveStack = new Stack<OrderedPair>();
    private OrderedPair mazeEnd;

    public MazeSolve(Maze puzzle, MazeDisplay newDisplay, int sleep) {
        maze = puzzle;
        thisDisplay = newDisplay;
        sleepTime = sleep;
        mazeEnd = new OrderedPair(maze.getWidth() - 1,
                                  maze.getHeight() - 1);
        solveStack.push(new OrderedPair(0,0));
        listeners = new ArrayList<SolveListener>();
        start();
    }

    /* Start the solver thread. */
    public void start() {
        if (enabled == false) {
            enabled = true;
            (new Thread(this)).start();
        }
    }

    /* Stop the solver thread. */
    public void stop() {
        enabled = false;
    }

    /* Solves the maze in its own thread */
    public void run() {
        OrderedPair point;

        /* Solve one step of the maze, sleep, check for halt, repeat */
        do {
            point = solveStack.peek();

            /* Mark the current point location */
            maze.markSolution(point);
            thisDisplay.repaint();

            /* Decide which directon to go next */
            OrderedPair upCell    = new OrderedPair(point.x, point.y - 1);
            OrderedPair downCell  = new OrderedPair(point.x, point.y + 1);
            OrderedPair leftCell  = new OrderedPair(point.x - 1, point.y);
            OrderedPair rightCell = new OrderedPair(point.x + 1, point.y);

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
                thisDisplay.repaint();
                solveStack.pop();
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
            for (SolveListener listener : listeners) {
                listener.solveDone();
            }
        }
    }

    /* Add a new listener. */
    public void addListener(SolveListener listener) {
        listeners.add(listener);
    }
}
