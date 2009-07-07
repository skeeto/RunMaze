import java.util.Stack;

/* MazeSolve - solves the given maze and updates it on the given
   display. */
class MazeSolve implements Runnable {
    /* Behavior variables */
    private Maze        thisMaze;
    private MazeDisplay thisDisplay;
    private Thread      solveThread;
    private int         sleepTime;

    /* Solution variables */
    private Stack       solveStack = new Stack();
    private OrderedPair mazeEnd;

    public MazeSolve(Maze newMaze, MazeDisplay newDisplay, int sleep) {
        thisMaze   = newMaze;
        thisDisplay = newDisplay;
        mazeEnd = new OrderedPair(thisMaze.mazeWidth - 1,
                                  thisMaze.mazeHeight - 1);

        solveThread = new Thread(this);
        sleepTime = sleep;

        /* Push on the starting location */
        solveStack.push(new OrderedPair(0,0));

        /* Start the solving thread */
        solveThread.start();
    }

    /* Solves the maze in its own thread */
    public void run() {
        OrderedPair point;

        /* Solve one step of the maze, sleep, check for halt, repeat */
        do {
            point = (OrderedPair) solveStack.peek();

            /* Mark the current point location */
            thisMaze.mark(point);
            thisDisplay.mark(point);

            /* Decide which directon to go next */
            OrderedPair upCell    = new OrderedPair(point.x, point.y - 1);
            OrderedPair downCell  = new OrderedPair(point.x, point.y + 1);
            OrderedPair leftCell  = new OrderedPair(point.x - 1, point.y);
            OrderedPair rightCell = new OrderedPair(point.x + 1, point.y);

            /* Push next move onto the stack */
            if (!thisMaze.topWall(point) && !thisMaze.marked(upCell)) {
                solveStack.push(upCell);
            } else if (!thisMaze.leftWall(rightCell)
                       && !thisMaze.marked(rightCell)) {
                solveStack.push(rightCell);
            } else if (!thisMaze.topWall(downCell)
                       && !thisMaze.marked(downCell)) {
                solveStack.push(downCell);
            } else if (!thisMaze.leftWall(point)
                       && !thisMaze.marked(leftCell)) {
                solveStack.push(leftCell);
            } else {
                thisMaze.markError(point);
                thisDisplay.markError(point);
                solveStack.pop();
            }

            /* Wait for some time time */
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                return;
            }

            /* If next point location is the end, we are done. */
        } while (point.x != mazeEnd.x || point.y != mazeEnd.y);
    }
}
