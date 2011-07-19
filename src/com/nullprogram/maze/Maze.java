package com.nullprogram.maze;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.ArrayList;
import java.util.ArrayDeque;

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
        Deque<Position> genStack  = new ArrayDeque<Position>();
        List<Position> freeCells = new ArrayList<Position>();
        Position point;
        Random      randomGen = new Random();
        Position nextCell;
        int         nextCellIndex;

        /* Push on the starting location. */
        genStack.push(new Position(0, 0));

        while (!genStack.isEmpty()) {
            point = genStack.peek();

            int x = point.getX();
            int y = point.getY();
            Position upCell = new Position(x, y - 1);
            Position downCell = new Position(x, y + 1);
            Position leftCell = new Position(x - 1, y);
            Position rightCell = new Position(x + 1, y);

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
                nextCell = freeCells.get(nextCellIndex);

                /* Mark as used. */
                markCellUsed(nextCell);

                /* Break down the wall. */
                if (nextCell.getX() < x) {
                    breakLeft(point);
                } else if (nextCell.getY() < y) {
                    breakTop(point);
                } else if (nextCell.getX() > x) {
                    breakLeft(nextCell);
                } else if (nextCell.getY() > y) {
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
    private boolean cellUsed(final Position point) {
        return !inBounds(point) || data[point.getX()][point.getY()].isUsed();
    }

    /**
     * Mark the given position as visited.
     * @param point position to be marked
     */
    private void markCellUsed(final Position point) {
        data[point.getX()][point.getY()].markUsed();
    }

    /**
     * Break down the left wall at the position.
     * @param point the position to be affected
     */
    private void breakLeft(final Position point) {
        data[point.getX()][point.getY()].breakLeft();
    }

    /**
     * Break down the top wall at the position.
     * @param point the position to be affected
     */
    private void breakTop(final Position point) {
        data[point.getX()][point.getY()].breakTop();
    }

    /**
     * Marks the given position as a solution path.
     * @param point the position to be marked
     */
    public final void markSolution(final Position point) {
        data[point.getX()][point.getY()].markSolution();
    }

    /**
     * Determines id maze position has been marked as a
     * solution. Positions outside the bounds are counted as not marked.
     * @param point the position to be tested
     * @return true of the position has been marked
     */
    public final boolean isSolution(final Position point) {
        return inBounds(point) && data[point.getX()][point.getY()].isSolution();
    }

    /**
     * Marks the given position as part of an error path.
     * @param point the position to be marked
     */
    public final void markError(final Position point) {
        data[point.getX()][point.getY()].markError();
    }

    /**
     * Determines id maze position has been marked as an
     * errer. Positions outside the bounds are counted as not marked.
     * @param point the position to be tested
     * @return true of the position has been marked
     */
    public final boolean isError(final Position point) {
        return !inBounds(point) || data[point.getX()][point.getY()].isError();
    }

    /**
     * Return true if position has any marking.
     * @param point the position to be tested
     * @return true if position has any mark
     */
    public final boolean marked(final Position point) {
        return !inBounds(point)
            || data[point.getX()][point.getY()].isSolution()
            || data[point.getX()][point.getY()].isError();
    }

    /**
     * Returns true if position has a top wall.
     * @param point the position to be tested
     * @return true if the position has a top wall
     */
    public final boolean topWall(final Position point) {
        return !inBounds(point)
            || data[point.getX()][point.getY()].hasTopWall();
    }

    /**
     * Returns true if position has a top wall.
     * @param point the position to be tested
     * @return true if the position has a top wall
     */
    public final boolean leftWall(final Position point) {
        return !inBounds(point)
            || data[point.getX()][point.getY()].hasLeftWall();
    }

    /**
     * Return true if the given position is within the bounds of the
     * maze.
     * @param point the point to be tested
     * @return true if position is within the bounds of the maze
     */
    private boolean inBounds(final Position point) {
        return (point.getX() >= 0) && (point.getY() >= 0)
            && (point.getX() < width) && (point.getY() < height);
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
