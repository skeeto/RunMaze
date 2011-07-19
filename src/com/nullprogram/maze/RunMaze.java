package com.nullprogram.maze;

import javax.swing.JFrame;

/**
 * Set up an animation as a standalone application.
 */
public final class RunMaze {

    private static final int WIDTH = 60;
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

        /* Create a new maze */
        Maze newMaze = new Maze(width, height);

        /* Create a frame and a maze display */
        JFrame mazeFrame = new JFrame("Maze");
        MazeDisplay mazeDraw = new MazeDisplay(newMaze, scale);
        mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeFrame.add(mazeDraw);
        mazeFrame.pack();
        mazeFrame.setResizable(false);
        mazeFrame.setVisible(true);

        /* Now solve the maze */
        MazeSolver solution = new MazeSolver(newMaze, speed);
        solution.addListener(mazeDraw);
    }
}
