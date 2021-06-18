package yatdel;

/**
 *
 * @author Ben
 */
public class Abstract {

    public char[][] grid;
    public int lengthX;
    public int lengthY;

    public Abstract(char[][] grid) {
        this.grid = grid;
        this.lengthY = grid.length;
        this.lengthX = grid[0].length;

    }

}
