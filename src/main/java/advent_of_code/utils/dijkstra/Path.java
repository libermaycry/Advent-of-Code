package advent_of_code.utils.dijkstra;

import java.util.List;

public class Path {

    public long cost;
    public List<Node> nodes;

    public Path(long cost, List<Node> nodes) {
        this.cost = cost;
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        nodes.forEach(node -> sb.append(node.toString()).append(" -> "));
        String result = sb.substring(0, sb.toString().length() - 4);
        return result + ", cost " + this.cost;
    }
}
