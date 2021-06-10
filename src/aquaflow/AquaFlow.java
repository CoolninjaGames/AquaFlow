package aquaflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class AquaFlow {

    static ArrayList<Flow> flows = new ArrayList<>();

    /**
     * @param args a list of paths to flows
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File loads = new File(args[0]);
        ArrayList<File> load = new ArrayList<>();
        BufferedReader b = new BufferedReader(new FileReader(loads));
        String s = b.readLine();
        while (s != null) {
            load.add(new File(s));
            s = b.readLine();
        }
        for (int i = 0; i < load.size(); i++) {
            BufferedReader br = new BufferedReader(new FileReader(load.get(i)));
            ArrayList<ArrayList<Character>> t = new ArrayList<ArrayList<Character>>();
            String line = br.readLine();
            int index = 0;
            while (line != null) {
                t.add(new ArrayList<Character>());
                for (int x = 0; x < line.length(); x++) {
                    t.get(index).add(line.charAt(x));
                }
                index++;
                line = br.readLine();
            }
            char[][] temp = new char[t.size()][t.get(0).size()];
            for (int y = 0; y < t.size(); y++) {
                for (int x = 0; x < t.get(y).size(); x++) {
                    setGrid(temp, x, y, t.get(y).get(x));
                }
            }
            flows.add(new Flow(temp));
            br.close();
        }
        Flow first = flows.get(0);
        int firstStartX = getStart(first)[0];
        int firstStartY = getStart(first)[1];
        Pointer start = new Pointer(firstStartX, firstStartY, 0);
        start.start();
    }

    public static char readGrid(Flow flow, int x, int y) {
        return readGrid(flow.grid, x, y);
    }

    public static char readGrid(char[][] grid, int x, int y) {
        return grid[y][x];
    }

    public static void setGrid(Flow flow, int x, int y, char c) {
        setGrid(flow.grid, x, y, c);
    }

    public static void setGrid(char[][] grid, int x, int y, char c) {
        grid[y][x] = c;
    }

    public synchronized static Flow getFlow(int index) {
        return flows.get(index);
    }

    public static int[] getStart(Flow grid) {
        int[] pos = new int[2];
        for (int y = 0; y < grid.lengthY; y++) {
            for (int x = 0; x < grid.lengthX; x++) {
                if (readGrid(grid, x, y) == 'S') {
                    pos[0] = x;
                    pos[1] = y;
                }
            }
        }
        return pos;
    }

}
