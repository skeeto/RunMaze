import java.util.Stack;
import java.util.Vector;
import java.util.Random;

/* Maze - Generates and stores a maze. */
class Maze {
    public int mazeWidth;
    public int mazeHeight;
    public MazeCell[][] mazeData = null;

    public Maze(int width, int height) {
        this.mazeWidth = width;
        this.mazeHeight = height;

        /* Initialize the array */
        mazeData = new MazeCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mazeData[i][j] = new MazeCell();
            }
        }

        /* Generate the maze */
        this.generate();
    }

    /* Generates the maze. */
    private void generate() {
        Stack       genStack  = new Stack();
        OrderedPair point;
        Vector      freeCells = new Vector();
        Random      randomGen = new Random();
        OrderedPair nextCell;
        int         nextCellIndex;

        /* Push on the starting location. */
        genStack.push(new OrderedPair(0, 0));

        while (!genStack.empty()) {
            point = (OrderedPair) genStack.peek();

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
                nextCell = (OrderedPair) freeCells.elementAt(nextCellIndex);

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

    /* Checks to make sure the query is in the maze. Returns the cell
       "used" state if it is. */
    private boolean cellUsed(OrderedPair point) {
        if ((point.x < 0) || (point.y < 0)
                || (point.x >= this.mazeWidth) || (point.y >= this.mazeHeight)) {
            return true;
        } else {
            return mazeData[point.x][point.y].used;
        }
    }

    /* Marks a cell's "used" state as true */
    private void markCellUsed(OrderedPair point) {
        mazeData[point.x][point.y].used = true;
    }

    /* Removes the left cell wall*/
    private void breakLeft(OrderedPair point) {
        mazeData[point.x][point.y].left = false;
    }

    /* Remove the top cell wall */
    private void breakTop(OrderedPair point) {
        mazeData[point.x][point.y].top = false;
    }

    /* Marks the cell as a solution path */
    public void mark(OrderedPair point) {
        mazeData[point.x][point.y].solveMark = true;
    }

    /* Returns true if the cell is marked as any type fo path */
    public boolean marked(OrderedPair point) {
        if ((point.x < 0) || (point.y < 0)
                || (point.x >= this.mazeWidth) || (point.y >= this.mazeHeight)) {
            return true;
        } else {
            return mazeData[point.x][point.y].solveMark
                   || mazeData[point.x][point.y].solveError;
        }
    }

    /* Marks the cell as part of an error path */
    public void markError(OrderedPair point) {
        mazeData[point.x][point.y].solveError = true;
    }

    /* Returns true if cell has a top wall */
    public boolean topWall(OrderedPair point) {
        if ((point.x < 0) || (point.y < 0)
                || (point.x >= this.mazeWidth) || (point.y >= this.mazeHeight)) {
            return true;
        } else {
            return mazeData[point.x][point.y].top;
        }
    }

    /* Returns true if cell has a bottom wall */
    public boolean leftWall(OrderedPair point) {
        if ((point.x < 0) || (point.y < 0)
                || (point.x >= this.mazeWidth) || (point.y >= this.mazeHeight)) {
            return true;
        } else {
            return mazeData[point.x][point.y].left;
        }
    }
}
