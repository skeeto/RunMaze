import java.awt.*;
import java.awt.event.*;

/* RunMaze - Contains the main function that drives the program. */
public class RunMaze {
    public static void main(String args[]) {

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
        Frame mazeFrame = new Frame("Maze");
        MazeDisplay mazeDraw = new MazeDisplay(newMaze, unitSize);
        mazeFrame.add(mazeDraw);

        /* Adjust the size of the frame */
        mazeFrame.setSize(width*unitSize+12, height*unitSize+30);

        /* Finally make everything visible */
        mazeFrame.setVisible(true);

        mazeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        /* Now solve the maze */
        MazeSolve solution = new MazeSolve(newMaze, mazeDraw, speed);
    }
}
