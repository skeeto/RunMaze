package com.nullprogram.maze;

import java.awt.*;

/* Contains the actual maze drawing */
class MazeDisplay extends Canvas {
    private static final long serialVersionUID = 1L;

    private Maze thisMaze;
    private int unitSize;

    public MazeDisplay(Maze newMaze, int unitSize) {
        super();
        this.setSize(newMaze.mazeWidth*unitSize, newMaze.mazeHeight*unitSize);
        thisMaze = newMaze;
        this.unitSize = unitSize;
    }

    /* Draws the maze */
    public void paint(Graphics g) {
        int width  = thisMaze.mazeWidth*unitSize;
        int height = thisMaze.mazeHeight*unitSize;
        g.drawLine(0,height,width,height);
        g.drawLine(width,0,width,height);
        for (int i = 0; i < thisMaze.mazeWidth; i++) {
            for (int j = 0; j < thisMaze.mazeHeight; j++) {
                drawCell(new OrderedPair(i, j), g);
            }
        }
    }

    /* Simply calls for a single cell to e redrawn */
    public void mark(OrderedPair point) {
        Graphics g = this.getGraphics();
        drawCell(point, g);
    }

    /* Simply calls for a single cell to e redrawn */
    public void markError(OrderedPair point) {
        Graphics g = this.getGraphics();
        drawCell(point, g);
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
