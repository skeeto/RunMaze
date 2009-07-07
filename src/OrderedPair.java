/* OrderedPair - This is basically just a structure to hold an ordered
   pair. This is the object that is pushed onto the stack when solving
   and generating the maze. */
class OrderedPair {
    public int x;
    public int y;

    public OrderedPair(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /* Simply display the pair for testing purposes. */
    public void paint() {
        System.out.println("(" + this.x + "," + this.y + ")");
    }
}
