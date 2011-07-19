package com.nullprogram.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Displays a maze to the user as a GUI component.
 */
class MazeDisplay extends JPanel implements SolverListener {
    private static final long serialVersionUID = 1L;

    /* Color scheme. */
    private static final Color SOLUTION = Color.GREEN;
    private static final Color ERROR = new Color(255, 127, 127);
    private static final Color WALL = Color.BLACK;

    private Maze maze;
    private int scale;

    private int width;
    private int height;

    /**
     * Display the given maze at the given size.
     * @param view  the maze to be displayed
     * @param unitSize  the pixel size of each cell in the maze
     */
    public MazeDisplay(final Maze view, final int unitSize) {
        super();
        scale = unitSize;
        setMaze(view);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, height, width, height);
        g.drawLine(width, 0, width, height);
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                drawCell(new OrderedPair(i, j), g);
            }
        }
    }

    /**
     * Fills the given position with the current color.
     * @param point the position to be filled
     * @param g the Graphics object to be painted on
     */
    private void fillCell(final OrderedPair point, final Graphics g) {
        g.fillRect(point.x * scale, point.y * scale,
                   scale, scale);
    }

    /**
     * Draw a single cell to the display.
     * @param point the position of the cell to be drawn
     * @param g the Graphics to be painted to
     */
    private void drawCell(final OrderedPair point, final Graphics g) {
        if (maze.isSolution(point)) {
            g.setColor(SOLUTION);
            fillCell(point, g);
        }
        if (maze.isError(point)) {
            g.setColor(ERROR);
            fillCell(point, g);
        }

        int x = point.x;
        int y = point.y;
        g.setColor(WALL);
        if (maze.leftWall(point)) {
            g.drawLine(x * scale, y * scale,
                       x * scale, (y + 1) * scale);
        }
        if (maze.topWall(point)) {
            g.drawLine(x * scale, y * scale,
                       (x + 1) * scale, y * scale);
        }
    }

    /**
     * Assign a new maze to this display.
     * @param view the new maze to be displayed
     */
    public void setMaze(final Maze view) {
        maze = view;
        width = maze.getWidth() * scale;
        height = maze.getHeight() * scale;
        Dimension size = new Dimension(width, height);
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
