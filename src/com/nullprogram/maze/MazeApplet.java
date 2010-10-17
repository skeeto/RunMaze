package com.nullprogram.maze;

import java.awt.Dimension;

import javax.swing.JApplet;

public class MazeApplet extends JApplet implements SolveListener {

    private static final long serialVersionUID = 7742407602430714892L;

    private int cellSize = 15;
    private int speed    = 10;
    private Maze maze;
    private MazeDisplay display;
    private MazeSolve solution;

    public void init() {
        Dimension size = getSize();
        maze = new Maze((int) (size.getWidth() / cellSize),
                        (int) (size.getHeight() / cellSize));
        if (display == null) {
            display = new MazeDisplay(maze, cellSize);
            add(display);
        } else {
            display.setMaze(maze);
        }
        solution = new MazeSolve(maze, display, speed);
        solution.addListener(this);
    }

    public void start() {
        solution.start();
    }

    public void stop() {
        solution.stop();
    }

    public void destroy() {
        stop();
    }

    public void solveDone() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return;
        }
        init();
    }
}
