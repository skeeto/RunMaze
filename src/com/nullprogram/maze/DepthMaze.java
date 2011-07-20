package com.nullprogram.maze;

import java.util.List;
import java.util.Deque;
import java.util.Queue;
import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.NoSuchElementException;

import java.awt.Point;

/**
 * A single randomly-generated maze. The maze is generated at
 * construction time.
 */
public class DepthMaze implements Maze {

    private static final int NDIRECTIONS = 4;

    private static final Mark VISITED = Mark.get("visited");

    private final int width;
    private final int height;
    private final int scale;
    private final DepthCell[][] data;

    /**
     * Generate a new maze with the given properties.
     * @param cellWidth  width of the new maze
     * @param cellHeight  height of the new maze
     * @param cellScale  scale of this maze
     */
    public DepthMaze(final int cellWidth, final int cellHeight,
                     final int cellScale) {
        width = cellWidth;
        height = cellHeight;
        scale = cellScale;
        data = new DepthCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = new DepthCell(i, j, scale);
            }
        }
        generate();
    }

    /**
     * Generate this maze. This should only be called once.
     */
    private void generate() {
        Deque<Point> stack = new ArrayDeque<Point>();
        stack.push(new Point(0, 0));
        data[0][0].mark(VISITED);
        data[width - 1][height - 1].mark(Cell.END);
        Random rng = new Random();

        while (!stack.isEmpty()) {
            Queue<Point> dirs = getDirections();
            Point p = stack.peek();
            DepthCell current = get(p);
            boolean found = false;
            while (!dirs.isEmpty()) {
                Point dir = dirs.poll();
                Point pnext = new Point(dir.x + p.x, dir.y + p.y);
                DepthCell next = get(pnext);
                if (next == null || next.hasMark(VISITED)) {
                    continue;
                }
                stack.push(pnext);
                next.mark(VISITED);
                next.addNeighbor(current);
                current.addNeighbor(next);
                found = true;
                break;
            }
            if (!found) {
                stack.pop();
            }
        }
    }

    /**
     * A randomly-sorted set of directions to go.
     * @return a randomly-sorted queue of directions
     */
    private Queue<Point> getDirections() {
        List<Point> dirs = new ArrayList<Point>(NDIRECTIONS);
        dirs.add(new Point(1, 0));
        dirs.add(new Point(-1, 0));
        dirs.add(new Point(0, 1));
        dirs.add(new Point(0, -1));
        Collections.shuffle(dirs);
        return new ArrayDeque<Point>(dirs);
    }

    /**
     * Return the cell at the given position.
     * @param p  position of the cell
     * @return the cell at the given position
     */
    private DepthCell get(final Point p) {
        if (inBounds(p.x, p.y)) {
            return data[p.x][p.y];
        } else {
            return null;
        }
    }

    /**
     * Return true if the given position is within the bounds of the
     * maze.
     * @param px X position to be tested
     * @param py Y position to be tested
     * @return true if position is within the bounds of the maze
     */
    private boolean inBounds(final int px, final int py) {
        return (px >= 0) && (py >= 0) && (px < width) && (py < height);
    }

    @Override
    public final int getWidth() {
        return width * scale;
    }

    @Override
    public final int getHeight() {
        return height * scale;
    }

    @Override
    public final Cell start() {
        return data[0][0];
    }

    @Override
    public final Iterator<Cell> iterator() {
        return new DepthMazeIterator();
    }

    /**
     * Iterates through all the cells in the current maze, starting
     * with 0, 0, working row-wise.
     */
    private class DepthMazeIterator implements Iterator<Cell> {
        private int x = 0;
        private int y = 0;

        @Override
        public boolean hasNext() {
            return y < height;
        }

        @Override
        public Cell next() {
            if (y >= height) {
                throw new NoSuchElementException();
            }
            Cell ret = data[x][y];
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
            return ret;
        }

        @Override
        public void remove() {
            String msg = "Can't remove cells from a maze.";
            throw new UnsupportedOperationException(msg);
        }
    }
}
