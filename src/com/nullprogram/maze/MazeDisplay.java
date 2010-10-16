package com.nullprogram.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

/* Contains the actual maze drawing */
class MazeDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    private Maze thisMaze;
    private int unitSize;

    private int width;
    private int height;

    public MazeDisplay(Maze newMaze, int unitSize) {
        super();
        this.unitSize = unitSize;
        thisMaze = newMaze;
        width  = thisMaze.mazeWidth*unitSize;
        height = thisMaze.mazeHeight*unitSize;
        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setPreferredSize(size);
    }

    /* Draws the maze */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,height,width,height);
        g.drawLine(width,0,width,height);
        for (int i = 0; i < thisMaze.mazeWidth; i++) {
            for (int j = 0; j < thisMaze.mazeHeight; j++) {
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
        int i = point.x;
        int j = point.y;

        if (thisMaze.mazeData[i][j].solveMark) {
            g.setColor(new Color(0, 255, 0)); // Green (solution)
            fillCell(point, g);
        }

        if (thisMaze.mazeData[i][j].solveError) {
            g.setColor(new Color(255, 127, 127)); // Light red (error)
            fillCell(point, g);
        }

        g.setColor(new Color(0, 0, 0));	// Black walls

        if (thisMaze.mazeData[i][j].left) {
            g.drawLine(i*unitSize,
                       j*unitSize,
                       i*unitSize,
                       (j+1)*unitSize);
        }
        if (thisMaze.mazeData[i][j].top) {
            g.drawLine(i*unitSize,
                       j*unitSize,
                       (i+1)*unitSize,
                       j*unitSize);
        }
    }
}
