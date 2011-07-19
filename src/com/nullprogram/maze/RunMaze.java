package com.nullprogram.maze;

import javax.swing.JFrame;

/* RunMaze - Contains the main function that drives the program. */
public class RunMaze {
    public static void main(String args[]) {
        /* Fix for poor OpenJDK performance. */
        System.setProperty("sun.java2d.pmoffscreen", "false");

        /* Default maze behaviour */
        int width    = 60;
        int height   = 40;
        int unitSize = 15;
        int speed    = 10;

        /* Parse input arguments */
        if (args.length > 0) {
            try {
                width = Integer.parseInt(args[0]);
                if (args.length > 1) {
                    height = Integer.parseInt(args[1]);
                }
                if (args.length > 2) {
                    unitSize = Integer.parseInt(args[2]);
                }
                if (args.length > 3) {
                    speed = Integer.parseInt(args[3]);
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
        MazeDisplay mazeDraw = new MazeDisplay(newMaze, unitSize);
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
