import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Chiton {
    private static int puzzleOne(int[][] data) {
        return 0;
    }

    private static int puzzleTwo(int[][] data) {
        return 0;
    }

    private static int[][] parseInput(String file) {
        String[] in = file.split("\r\n");
        int[][] res = new int[in.length][in[0].length()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[i][j] = in[i].charAt(j) - '0';
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        int[][] input = parseInput(file);

        System.out.println(puzzleOne(input));
        System.out.println(puzzleTwo(input));
    }
}
