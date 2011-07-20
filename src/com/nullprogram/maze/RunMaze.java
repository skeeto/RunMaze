package com.nullprogram.maze;

import javax.swing.JFrame;

/**
 * Set up an animation as a standalone application.
 */
public final class RunMaze {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int SCALE = 15;
    private static final int SPEED = 10;

    private static final int WIDTH_ARG = 0;
    private static final int HEIGHT_ARG = 1;
    private static final int SCALE_ARG = 2;
    private static final int SPEED_ARG = 3;

    /** Hidden constructor. */
    private RunMaze() {
    }

    /**
     * The main function.
     * @param args  command line arguments
     */
    public static void main(final String[] args) {
        /* Fix for poor OpenJDK performance. */
        System.setProperty("sun.java2d.pmoffscreen", "false");

        /* Default maze behaviour */
        int width = WIDTH;
        int height = HEIGHT;
        int scale = SCALE;
        int speed = SPEED;

        /* Parse input arguments */
        if (args.length > 0) {
            try {
                width = Integer.parseInt(args[WIDTH_ARG]);
                if (args.length > HEIGHT_ARG) {
                    height = Integer.parseInt(args[HEIGHT_ARG]);
                }
                if (args.length > SCALE_ARG) {
                    scale = Integer.parseInt(args[SCALE_ARG]);
                }
                if (args.length > SPEED_ARG) {
                    speed = Integer.parseInt(args[SPEED_ARG]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Arguments must be integers");
                System.exit(1);
            }
        }

        Maze maze = new DepthMaze(width, height, scale);
        JFrame frame = new JFrame("Maze");
        MazeDisplay display = new MazeDisplay(maze);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        /* Now solve the maze */
        MazeSolver solver = new MazeSolver(maze, speed);
        solver.addListener(display);
        solver.start();
    }
}
