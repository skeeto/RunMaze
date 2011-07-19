package com.nullprogram.maze;

import java.util.Stack;
import java.util.Vector;
import java.util.Random;

/**
 * A single randomly-generated maze. The maze is generated at
 * construction time.
 */
public class Maze {

    private final int width;
    private final int height;
    private final MazeCell[][] data;

    /**
     * Generate a new maze with the given properties.
     * @param mazeWidth   width of the new maze
     * @param mazeHeight  height of the new maze
     */
    public Maze(final int mazeWidth, final int mazeHeight) {
        width = mazeWidth;
        height = mazeHeight;
        data = new MazeCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = new MazeCell();
            }
        }
        generate();
    }

    /**
     * Generate this maze. This should only be called once.
     */
    private void generate() {
        Stack<OrderedPair>  genStack  = new Stack<OrderedPair>();
        Vector<OrderedPair> freeCells = new Vector<OrderedPair>();
        OrderedPair point;
        Random      randomGen = new Random();
        OrderedPair nextCell;
        int         nextCellIndex;

        /* Push on the starting location. */
        genStack.push(new OrderedPair(0, 0));

        while (!genStack.empty()) {
            point = genStack.peek();

            OrderedPair upCell    = new OrderedPair(point.x, point.y - 1);
            OrderedPair downCell  = new OrderedPair(point.x, point.y + 1);
            OrderedPair leftCell  = new OrderedPair(point.x - 1, point.y);
            OrderedPair rightCell = new OrderedPair(point.x + 1, point.y);

            /* Add any unused neighbors to a vector. */
            if (!cellUsed(upCell)) {
                freeCells.add(upCell);
            }
            if (!cellUsed(downCell)) {
                freeCells.add(downCell);
            }
            if (!cellUsed(leftCell)) {
                freeCells.add(leftCell);
            }
            if (!cellUsed(rightCell)) {
                freeCells.add(rightCell);
            }

            /* Choose a neighbor at random if one exists. If none, pop
               the stack. */
            if (freeCells.isEmpty()) {
                genStack.pop();
            } else {
                nextCellIndex = randomGen.nextInt(freeCells.size());
                nextCell = freeCells.elementAt(nextCellIndex);

                /* Mark as used. */
                markCellUsed(nextCell);

                /* Break down the wall. */
                if (nextCell.x < point.x) {
                    breakLeft(point);
                } else if (nextCell.y < point.y) {
                    breakTop(point);
                } else if (nextCell.x > point.x) {
                    breakLeft(nextCell);
                } else if (nextCell.y > point.y) {
                    breakTop(nextCell);
                }

                /* Push new point onto the stack */
                genStack.push(nextCell);
                freeCells.clear();
            }
        }
    }

    /**
     * Determine if the given cell has been visited already. Positions
     * outside the bounds are considered visited.
     * @param point  the point to be tested
     * @return true if cell has been visited
     */
    private boolean cellUsed(final OrderedPair point) {
        return !inBounds(point) || data[point.x][point.y].isUsed();
    }

    /**
     * Mark the given position as visited.
     * @param point position to be marked
     */
    private void markCellUsed(final OrderedPair point) {
        data[point.x][point.y].markUsed();
    }

    /**
     * Break down the left wall at the position.
     * @param point the position to be affected
     */
    private void breakLeft(final OrderedPair point) {
        data[point.x][point.y].breakLeft();
    }

    /**
     * Break down the top wall at the position.
     * @param point the position to be affected
     */
    private void breakTop(final OrderedPair point) {
        data[point.x][point.y].breakTop();
    }

    /**
     * Marks the given position as a solution path.
     * @param point the position to be marked
     */
    public final void markSolution(final OrderedPair point) {
        data[point.x][point.y].markSolution();
    }

    /**
     * Determines id maze position has been marked as a
     * solution. Positions outside the bounds are counted as not marked.
     * @param point the position to be tested
     * @return true of the position has been marked
     */
    public final boolean isSolution(final OrderedPair point) {
        return inBounds(point) && data[point.x][point.y].isSolution();
    }

    /**
     * Marks the given position as part of an error path.
     * @param point the position to be marked
     */
    public final void markError(final OrderedPair point) {
        data[point.x][point.y].markError();
    }

    /**
     * Determines id maze position has been marked as an
     * errer. Positions outside the bounds are counted as not marked.
     * @param point the position to be tested
     * @return true of the position has been marked
     */
    public final boolean isError(final OrderedPair point) {
        return !inBounds(point) || data[point.x][point.y].isError();
    }

    /**
     * Return true if position has any marking.
     * @param point the position to be tested
     * @return true if position has any mark
     */
    public final boolean marked(final OrderedPair point) {
        return !inBounds(point)
            || data[point.x][point.y].isSolution()
            || data[point.x][point.y].isError();
    }

    /**
     * Returns true if position has a top wall.
     * @param point the position to be tested
     * @return true if the position has a top wall
     */
    public final boolean topWall(final OrderedPair point) {
        return !inBounds(point) || data[point.x][point.y].hasTopWall();
    }

    /**
     * Returns true if position has a top wall.
     * @param point the position to be tested
     * @return true if the position has a top wall
     */
    public final boolean leftWall(final OrderedPair point) {
        return !inBounds(point) || data[point.x][point.y].hasLeftWall();
    }

    /**
     * Return true if the given position is within the bounds of the
     * maze.
     * @param point the point to be tested
     * @return true if position is within the bounds of the maze
     */
    private boolean inBounds(final OrderedPair point) {
        return (point.x >= 0) && (point.y >= 0)
            && (point.x < width) && (point.y < height);
    }

    /**
     * Return the width of the maze.
     * @return the width of the maze
     */
    public final int getWidth() {
        return width;
    }

    /**
     * Return the height of the maze.
     * @return the height of the maze
     */
    public final int getHeight() {
        return height;
    }
}
