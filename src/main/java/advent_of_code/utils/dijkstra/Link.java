package advent_of_code.utils.dijkstra;

public class Link {

    public long cost;
    public String toName;

    public Link(long cost, String toName) {
        this.cost = cost;
        this.toName = toName;
    }
}
