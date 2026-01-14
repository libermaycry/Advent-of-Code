package advent_of_code.utils.dijkstra;

import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;

import java.util.*;
import java.util.stream.Stream;

public class Dijkstra {

    public List<Node> nodes;
    public List<Node> visited;
    public Map<Node, Distance> distances;
    public Node from;
    public Node to;
    public HashMap<String, Node> nodesByName;

    public Dijkstra(List<Node> nodes, Node from, Node to) {
        this.nodes = nodes;
        this.visited = new ArrayList<>();
        this.from = from;
        this.to = to;
        this.distances = new HashMap<>();
        this.distances.put(from, new Distance(0, null));
        this.nodesByName = new HashMap<>();
        nodes.forEach(node -> nodesByName.put(node.name, node));
    }

    public Optional<Path> find() {
        Node current = from;
        while (visited.size() < nodes.size()) {
            for (Link link : current.links) {
                Node toNode = nodesByName.get(link.toName);
                if (visited.contains(toNode)) continue;
                long currentDistance = distances.getOrDefault(toNode, new Distance(Long.MAX_VALUE, current)).value;
                long newDistance = distances.get(current).value + link.cost;
                if (newDistance < currentDistance) {
                    distances.put(toNode, new Distance(newDistance, current));
                }
            }
            visited.add(current);
            current = findNextCurrent();
            if (current == null && visited.size() < nodes.size()) {
                return Optional.empty();
            }
        }
        return Optional.of(buildPath());
    }

    private Path buildPath() {
        List<Node> nodesOfPath = new ArrayList<>();
        long cost = 0;
        Node current = to;
        while (current != null) {
            nodesOfPath.add(current);
            Distance distance = distances.get(current);
            String currentName = current.name;
            current = distance.previous;
            cost += Optional.ofNullable(distance.previous)
                    .map(n -> n.links)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(l -> l.toName.equals(currentName))
                    .map(l -> l.cost)
                    .findAny()
                    .orElse(0L);
        }
        return new Path(cost, nodesOfPath.reversed());
    }

    private Node findNextCurrent() {
        long minDistance = Long.MAX_VALUE;
        Node next = null;
        for (Node node : distances.keySet()) {
            if (visited.contains(node)) continue;
            long d = distances.get(node).value;
            if (d < minDistance) {
                minDistance = d;
                next = node;
            }
        }
        return next;
    }

    public static void main(String[] args) {

        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("A", List.of(
                new Link(2, "C"),
                new Link(4, "B"),
                new Link(7, "E"),
                new Link(6, "D")
        )));
        nodes.add(new Node("B", List.of(
                new Link(4, "A"),
                new Link(1, "D")
        )));
        nodes.add(new Node("C", List.of(
                new Link(2, "A"),
                new Link(3, "E")
        )));
        nodes.add(new Node("D", List.of(
                new Link(6, "A"),
                new Link(1, "B"),
                new Link(1, "E"),
                new Link(3, "F"),
                new Link(11, "G")
        )));
        nodes.add(new Node("E", List.of(
                new Link(3, "C"),
                new Link(7, "A"),
                new Link(1, "D"),
                new Link(6, "F"),
                new Link(3, "H")
        )));
        nodes.add(new Node("F", List.of(
                new Link(6, "E"),
                new Link(11, "H"),
                new Link(3, "G"),
                new Link(3, "D")
        )));
        nodes.add(new Node("G", List.of(
                new Link(2, "H"),
                new Link(3, "F"),
                new Link(11, "D")
        )));
        nodes.add(new Node("H", List.of(
                new Link(3, "E"),
                new Link(11, "F"),
                new Link(2, "G")
        )));

        System.out.println(new Dijkstra(nodes, nodes.getFirst(), nodes.get(6)).find().orElseThrow());
    }

    public static List<Node> getNodesFromMatrix(Matrix matrix, Character... validChars) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                int finalI = i;
                int finalJ = j;
                if (Arrays.stream(validChars).anyMatch(c -> matrix.check(finalI, finalJ, c))) {
                    Node node = new Node(Strings.of(i,j));
                    Stream.of(Pair.of(i - 1, j),
                                Pair.of(i + 1, j),
                                Pair.of(i, j - 1),
                                Pair.of(i, j + 1))
                            .filter(pos -> Arrays.stream(validChars).anyMatch(c -> matrix.check(pos.left, pos.right, c)))
                            .forEach(pos -> node.links.add(new Link(1, Strings.of(pos.left, pos.right))));
                    if (!node.links.isEmpty()) {
                        nodes.add(node);
                    }
                }
            }
        }
        return nodes;
    }




}
