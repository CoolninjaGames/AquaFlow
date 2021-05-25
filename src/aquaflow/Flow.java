package aquaflow;

/**
 *
 * @author Ben
 */
public class Flow {

    public char[][] grid;
    public int lengthX;
    public int lengthY;

    public Flow(char[][] grid) {
        this.grid = grid;
        this.lengthY = grid.length;
        this.lengthX = grid[0].length;

    }

}
