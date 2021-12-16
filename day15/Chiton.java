import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Chiton {
    private static Vertex start;
    private static Vertex end;
    private static int startValue;
    private static int endValue;

    private static int puzzleOne(Graph data) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(data);
        dijkstraAlgorithm.execute(start);
        return dijkstraAlgorithm.getShortestDistance(end) - startValue + endValue;
    }

    // takes VERY long to finish
    private static int puzzleTwo(String file) {
        String[] aaa = file.split("\r\n");

        Graph g;
        {
            StringBuilder sb = new StringBuilder();
            for (int bigRow = 0; bigRow < 5; bigRow++) {
                for (String cRow : aaa) {
                    for (int bigCol = 0; bigCol < 5; bigCol++) {
                        for (int col = 0; col < aaa.length; col++) {
                            int cur = cRow.charAt(col) - '1';

                            // maximum pain
                            int newC = ((cur + bigRow + bigCol) % 9) + 1;
                            sb.append(newC + '1');
                        }
                    }
                    sb.append("\r\n");
                }
            }
            g = parseInput(sb.toString());
        }

        return puzzleOne(g);
    }

    private static Graph parseInput(String file) {
        // this is the ugliest code I've ever written
        String[] in = file.split("\r\n");
        ArrayList<Vertex> vertices = new ArrayList<>(in.length * in.length);
        Vertex c = new Vertex("V0", "e");
        vertices.add(c);
        start = c;
        startValue = in[0].charAt(0) - '0';
        for (int i = 1; i < in.length * in.length - 1; i++) {
            vertices.add(new Vertex("V%d".formatted(i), "e"));
        }
        c = new Vertex("Vl", "e");
        vertices.add(c);
        end = c;
        endValue = in[in.length - 1].charAt(in.length - 1) - '0';

        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in.length; j++) {
                if (i != 0) edges.add(
                        new Edge(
                                "E%d%d-l".formatted(i, j),
                                vertices.get(i * in.length + j),
                                vertices.get((i - 1) * in.length + j),
                                in[i].charAt(j) - '0')
                );

                if (i != in.length - 1) edges.add(
                        new Edge(
                                "E%d%d-r".formatted(i, j),
                                vertices.get(i * in.length + j),
                                vertices.get((i + 1) * in.length + j),
                                in[i].charAt(j) - '0')
                );

                if (j != 0) edges.add(
                        new Edge(
                                "E%d%d-u".formatted(i, j),
                                vertices.get(i * in.length + j),
                                vertices.get((i) * in.length + j - 1),
                                in[i].charAt(j) - '0')
                );

                if (j != in.length - 1) edges.add(
                        new Edge(
                                "E%d%d-d".formatted(i, j),
                                vertices.get(i * in.length + j),
                                vertices.get((i) * in.length + j + 1),
                                in[i].charAt(j) - '0')
                );
            }
        }

        return new Graph(vertices, edges);
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        Graph input = parseInput(file);

        System.out.println(puzzleOne(input));
        System.out.println(puzzleTwo(file));
    }
}
