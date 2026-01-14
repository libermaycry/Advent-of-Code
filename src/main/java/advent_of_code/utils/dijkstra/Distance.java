package advent_of_code.utils.dijkstra;

public class Distance {

    public long value;
    public Node previous;

    public Distance(long value, Node previous) {
        this.value = value;
        this.previous = previous;
    }
}
