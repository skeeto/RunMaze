package com.nullprogram.maze;

import java.awt.Dimension;

import javax.swing.JApplet;

/**
 * Runs the maze animation as an applet.
 */
public class MazeApplet extends JApplet implements SolverListener {

    private static final long serialVersionUID = 7742407602430714892L;

    /* Defaults */
    private static final int CELL_SIZE = 15;
    private static final int SPEED = 10;
    private static final int RESTART_DELAY = 3000;

    private int cellSize = CELL_SIZE;
    private int speed = SPEED;
    private Maze maze;
    private MazeDisplay display;
    private MazeSolver solution;

    @Override
    public final void init() {
        String paramSize = getParameter("cellsize");
        if (paramSize != null) {
            cellSize = Integer.parseInt(paramSize);
        }
        Dimension size = getSize();
        maze = new DepthMaze((int) (size.getWidth() / cellSize),
                             (int) (size.getHeight() / cellSize),
                             cellSize);
        if (display == null) {
            display = new MazeDisplay(maze);
            add(display);
        } else {
            display.setMaze(maze);
        }
        solution = new MazeSolver(maze, speed);
        solution.addListener(this);
        solution.addListener(display);
    }

    @Override
    public final void start() {
        solution.start();
    }

    @Override
    public final void stop() {
        solution.stop();
    }

    @Override
    public final void destroy() {
        stop();
    }

    @Override
    public final void solveDone() {
        try {
            Thread.sleep(RESTART_DELAY);
        } catch (InterruptedException e) {
            return;
        }
        init();
        start();
    }

    @Override
    public final void solveStep() {
    }
}
