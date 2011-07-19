package com.nullprogram.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

/* Contains the actual maze drawing */
class MazeDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    private Maze maze;
    private int unitSize;

    private int width;
    private int height;

    public MazeDisplay(Maze view, int unitSize) {
        super();
        this.unitSize = unitSize;
        setMaze(view);
    }

    /* Draws the maze */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,height,width,height);
        g.drawLine(width,0,width,height);
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                drawCell(new OrderedPair(i, j), g);
            }
        }
    }

    /* Fills a cell with the current color */
    private void fillCell(OrderedPair point, Graphics g) {
        g.fillRect(point.x * unitSize,
                   point.y * unitSize,
                   unitSize,
                   unitSize);
    }

    /* Draws the cell according to its state information */
    private void drawCell(OrderedPair point, Graphics g) {
        if (maze.isSolution(point)) {
            g.setColor(new Color(0, 255, 0));
            fillCell(point, g);
        }
        if (maze.isError(point)) {
            g.setColor(new Color(255, 127, 127));
            fillCell(point, g);
        }

        int x = point.x;
        int y = point.y;
        g.setColor(new Color(0, 0, 0));
        if (maze.leftWall(point)) {
            g.drawLine(x*unitSize,
                       y*unitSize,
                       x*unitSize,
                       (y+1)*unitSize);
        }
        if (maze.topWall(point)) {
            g.drawLine(x*unitSize,
                       y*unitSize,
                       (x+1)*unitSize,
                       y*unitSize);
        }
    }

    /* Assign this display a new maze. */
    public void setMaze(Maze view) {
        maze = view;
        width = maze.getWidth() * unitSize;
        height = maze.getHeight() * unitSize;
        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setPreferredSize(size);
    }
}
