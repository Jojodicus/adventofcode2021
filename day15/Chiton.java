import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Chiton {
    // sortable positions used for heap
    private static record Position(int x, int y, int weight) implements Comparable<Position> {
        @Override
        public int compareTo(Position p) {
            return weight - p.weight;
        }
    }

    private static int puzzleOne(int[][] field) {
        int[][] risk = new int[field.length][field.length];
        for (int[] ints : risk) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }

        risk[0][0] = 0;

        // breadth first search - dijkstra //

        PriorityQueue<Position> heapQueue = new PriorityQueue<>();
        heapQueue.add(new Position(0, 0, 0));

        while (heapQueue.size() != 0) {
            Position current = heapQueue.poll();

            // reached end
            if (current.x == field.length && current.y == field.length)
                break;

            // next positions to visit
            int[][] next = new int[][]{
                    {current.x + 1, current.y},
                    {current.x, current.y + 1},
                    {current.x - 1, current.y},
                    {current.x, current.y - 1}
            };

            for (int[] newPos : next) {
                int newRow = newPos[0];
                int newCol = newPos[1];

                // outside of grid
                if (newRow < 0 || newCol < 0 || newRow >= field.length || newCol >= field.length)
                    continue;

                int gridNum = field[newRow][newCol];
                int currentRisk = risk[newRow][newCol];

                // not a better path
                if (currentRisk <= risk[current.x][current.y] + gridNum)
                    continue;

                // not first to visit -> clear other visitors
                if (currentRisk != Integer.MAX_VALUE) {
                    Position adj = new Position(newRow, newCol, currentRisk);
                    heapQueue.remove(adj);
                }

                // add as lowest risk level
                risk[newRow][newCol] = risk[current.x][current.y] + gridNum;
                heapQueue.add(new Position(newRow, newCol, risk[newRow][newCol]));
            }
        }

        return risk[risk.length - 1][risk.length - 1];
    }

    private static int puzzleTwo(int[][] field) {
        return puzzleOne(inflate(field));
    }

    private static int[][] parseInput(String input) {
        String[] in = input.split("\r\n");
        int[][] res = new int[in.length][in.length];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[i][j] = in[i].charAt(j) - '0';
            }
        }

        return res;
    }

    private static int[][] inflate(int[][] input) {
        int[][] big = new int[input.length * 5][input.length * 5];

        // make input 5 times bigger
        for (int i = 0; i < big.length; i++) {
            for (int j = 0; j < big.length; j++) {
                big[i][j] = (input[i % input.length][j % input.length]
                        + i / input.length
                        + j / input.length
                        - 1) % 9 + 1;
            }
        }

        return big;
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        int[][] parsed = parseInput(file);

        System.out.println(puzzleOne(parsed));
        System.out.println(puzzleTwo(parsed));
    }
}
