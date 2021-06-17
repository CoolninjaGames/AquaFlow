package jabc;

import static jabc.JABC.readGrid;
import java.util.Stack;

/**
 *
 * @author Ben
 */
public class Console {

    public static synchronized void printPos(String threadname, Abstract ab, Stack stack, int xPos, int yPos) {
        System.out.println(threadname);
        for (int y = 0; y < ab.grid.length; y++) {
            for (int x = 0; x < ab.grid[y].length; x++) {
                if (x == xPos && y == yPos) {
                    System.out.print('}');
                } else {
                    System.out.print(readGrid(ab.grid, x, y));
                }
            }
            System.out.print('\n');
        }
        if (!stack.empty()) {
            System.out.println(stack.peek());
        }
    }

    public static void doNothing() {

    }

}
