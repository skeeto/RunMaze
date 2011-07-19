package com.nullprogram.maze;

/**
 * Refers to a specific position in the maze.
 */
class Position {
    private final int x;
    private final int y;

    /**
     * Create a new Position for the given coordinates.
     * @param posX  X coordinate of this position
     * @param posY  Y coordinate of this position
     */
    public Position(final int posX, final int posY) {
        x = posX;
        y = posY;
    }

    @Override
    public final String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public final boolean equals(final Object o) {
        if (o instanceof Position) {
            Position that = (Position) o;
            if ((this.x == that.x) && (this.y == that.y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final int hashCode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(x);
        buffer.append(y);
        return buffer.toString().hashCode();
    }

    /**
     * Return the X coordinate of this position.
     * @return the X coordinate
     */
    public final int getX() {
        return x;
    }

    /**
     * Return the Y coordinate of this position.
     * @return the Y coordinate
     */
    public final int getY() {
        return y;
    }
}
