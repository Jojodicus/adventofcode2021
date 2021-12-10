import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HydrothermalVenture {
    private static int puzzleOne(int[][][] input) {
        // vent grid
        int[][] grid = new int[1000][1000];

        // loop over vent lines
        for (int[][] line : input)
            if (line[0][0] == line[1][0] || line[0][1] == line[1][1]) // no diagonals
                plotLine(grid, line[0][0], line[0][1], line[1][0], line[1][1]);

        // count positions with multiple vents
        int count = 0;
        for (int[] row : grid)
            for (int col : row)
                if (col > 1)
                    count++;

        return count;
    }

    private static int puzzleTwo(int[][][] input) {
        // vent grid
        int[][] grid = new int[1000][1000];

        // loop over vent lines
        for (int[][] line : input)
            plotLine(grid, line[0][0], line[0][1], line[1][0], line[1][1]);

        // count positions with multiple vents
        int count = 0;
        for (int[] row : grid)
            for (int col : row)
                if (col > 1)
                    count++;

        return count;
    }

    private static int[][][] parseInput(String input) {
        // split newlines
        String[] lines = input.split("\r\n");

        int[][][] res = new int[lines.length][2][2];

        for (int i = 0; i < lines.length; i++) {
            // start and end position
            String[] ps = lines[i].split(" -> ");
            // start and end coordinates
            String[] p1 = ps[0].split(",");
            String[] p2 = ps[1].split(",");

            // parse
            res[i][0][0] = Integer.parseInt(p1[0]);
            res[i][0][1] = Integer.parseInt(p1[1]);
            res[i][1][0] = Integer.parseInt(p2[0]);
            res[i][1][1] = Integer.parseInt(p2[1]);
        }

        return res;
    }

    // Bresenham's line algorithm

    private static void plotLine(int[][] grid, int x0, int y0, int x1, int y1) {
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0))
            if (x0 > x1)
                plotLineLow(grid, x1, y1, x0, y0);
            else
                plotLineLow(grid, x0, y0, x1, y1);
        else
            if (y0 > y1)
                plotLineHigh(grid, x1, y1, x0, y0);
            else
                plotLineHigh(grid, x0, y0, x1, y1);
    }

    private static void plotLineLow(int[][] grid, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int yi = 1;
        if (dy < 0) {
            yi = -1;
            dy = -dy;
        }
        int d = 2 * dy - dx;
        int y = y0;
        for (int x = x0; x <= x1; x++) {
            grid[y][x]++;
            if (d > 0) {
                y += yi;
                d += 2 * (dy - dx);
            } else {
                d += 2 * dy;
            }
        }
    }

    private static void plotLineHigh(int[][] grid, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        int d = 2 * dx - dy;
        int x = x0;
        for (int y = y0; y <= y1; y++) {
            grid[y][x]++;
            if (d > 0) {
                x += xi;
                d += 2 * (dx - dy);
            } else {
                d += 2 * dx;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        int[][][] input = parseInput(file);

        System.out.println(puzzleOne(input));
        System.out.println(puzzleTwo(input));
    }
}
