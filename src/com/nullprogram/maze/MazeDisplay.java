package com.nullprogram.maze;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JPanel;

/**
 * Displays a maze to the user as a GUI component.
 */
class MazeDisplay extends JPanel implements SolverListener {
    private static final long serialVersionUID = 1L;

    private static final Stroke WALL_STROKE = new BasicStroke(1);

    /* Color scheme. */
    private static final Color SOLUTION = Color.GREEN;
    private static final Color ERROR = new Color(255, 127, 127);
    private static final Color WALL = Color.BLACK;

    private Maze maze;

    /**
     * Display the given maze at the given size.
     * @param view  the maze to be displayed
     */
    public MazeDisplay(final Maze view) {
        super();
        setMaze(view);
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        double scaleX = getWidth() * 1.0 / maze.getWidth();
        double scaleY = getHeight() * 1.0 / maze.getHeight();
        g.scale(scaleX, scaleY);
        g.setStroke(WALL_STROKE);
        for (Cell cell : maze) {
            if (cell.hasMark(MazeSolver.ERROR_MARK)) {
                g.setColor(ERROR);
                g.fill(cell.getShape());
            } else if (cell.hasMark(MazeSolver.SOLUTION_MARK)) {
                g.setColor(SOLUTION);
                g.fill(cell.getShape());
            }
            g.setColor(WALL);
            g.draw(cell.getWalls());
        }
    }

    /**
     * Assign a new maze to this display.
     * @param view the new maze to be displayed
     */
    public void setMaze(final Maze view) {
        maze = view;
        Dimension size = new Dimension(maze.getWidth(), maze.getHeight());
        setMinimumSize(size);
        setPreferredSize(size);
        repaint();
    }

    @Override
    public final void solveDone() {
        repaint();
    }

    @Override
    public final void solveStep() {
        repaint();
    }
}
