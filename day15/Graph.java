import java.util.List;

public record Graph(List<Vertex> vertexes, List<Edge> edges) {
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}