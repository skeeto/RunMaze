package com.nullprogram.maze;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collection;

import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class DepthCell extends Cell {

    private final Shape shape;
    private final int x;
    private final int y;
    private final int size;
    private final Collection<Cell> neighbors = new ArrayList<Cell>(4);

    private boolean left = true;
    private boolean top = true;

    public DepthCell(int posX, int posY, int scale) {
        x = posX;
        y = posY;
        size = scale;
        shape = new Rectangle(x * scale, y * scale, size, size);
    }

    @Override
    public final Shape getShape() {
        return shape;
    }

    @Override
    public final Shape getWalls() {
        Path2D.Double walls = new Path2D.Double();
        if (left) {
            Shape leftWall = new Line2D.Double(x * size, y * size,
                                               x * size, y * size + size);
            walls.append(leftWall, false);
        }
        if (top) {
            Shape topWall = new Line2D.Double(x * size, y * size,
                                              x * size + size, y * size);
            walls.append(topWall, false);
        }
        return walls;
    }

    @Override
    public final Collection<Cell> neighbors() {
        return new ArrayList<Cell>(neighbors);
    }

    void addNeighbor(DepthCell cell) {
        if (cell.x < x) {
            left = false;
        } else if (cell.y < y) {
            top = false;
        }
        neighbors.add(cell);
    }
}
