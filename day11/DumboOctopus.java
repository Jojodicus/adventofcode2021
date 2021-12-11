import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class DumboOctopus {
    private static int puzzleOne(int[][] input) {
        // copy input
        int[][] grid = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            grid[i] = Arrays.copyOf(input[i], input[i].length);
        }

        int flashes = 0;

        // step 100 times
        for (int i = 0; i < 100; i++) {
            flashes += step(grid);
        }

        return flashes;
    }

    private static int puzzleTwo(int[][] input) {
        // copy input
        int[][] grid = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            grid[i] = Arrays.copyOf(input[i], input[i].length);
        }

        // step until all octopus flash together
        int step = 1;
        while (step(grid) != grid.length * grid.length) {
            step++;
        }
        return step;
    }

    private static int[][] parseInput(String file) {
        String[] lines = file.split("\r\n");
        int[][] res = new int[lines.length][lines.length];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = lines[i].charAt(j) - '0';
            }
        }

        return res;
    }

    private static int step(int[][] grid) {
        boolean[] flashed = new boolean[grid.length * grid.length];

        // increment all octopus
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                incrementAndFlash(grid, flashed, i, j);
            }
        }

        // recover
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] > 9)
                    grid[i][j] = 0;
            }
        }

        int count = 0;

        // count flashes
        for (boolean f : flashed) {
            if (f) count++;
        }

        return count;
    }

    private static void incrementAndFlash(int[][] grid, boolean[] flashed, int row, int col) {
        // increment
        grid[row][col]++;

        // flash
        if (grid[row][col] > 9 && !flashed[row * grid.length + col]) {
            flashed[row * grid.length + col] = true;

            // propagate
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    propagate(grid, flashed, row + i, col + j);
                }
            }
        }
    }

    private static void propagate(int[][] grid, boolean[] flashed, int row, int col) {
        if (row < 0 || row >= grid.length) return;
        if (col < 0 || col >= grid.length) return;
        incrementAndFlash(grid, flashed, row, col);
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        int[][] input = parseInput(file);

        System.out.println(puzzleOne(input));
        System.out.println(puzzleTwo(input));
    }
}
