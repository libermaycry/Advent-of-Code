package advent_of_code.utils.dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    public String name;
    public List<Link> links;

    public Node(String name) {
        this(name, new ArrayList<>());
    }

    public Node(String name, List<Link> links) {
        this.name = name;
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        Node other = (Node) o;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
