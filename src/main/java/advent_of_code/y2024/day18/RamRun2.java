package advent_of_code.y2024.day18;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;
import advent_of_code.utils.dijkstra.Dijkstra;
import advent_of_code.utils.dijkstra.Link;
import advent_of_code.utils.dijkstra.Node;
import advent_of_code.utils.dijkstra.Path;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RamRun2 extends AbstractRunnable {

    public static void main(String[] args) {
        new RamRun2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    List<Node> nodes;
    List<Pair<Integer,Integer>> corrupted;

    @Override
    protected void init() {
        corrupted = new ArrayList<>();
        readLines().forEach(line -> {
            String[] split = line.split(",");
            corrupted.add(Pair.of(Integer.parseInt(split[1]), Integer.parseInt(split[0])));
        });
        matrix = new Matrix(new char[71][71]);
        matrix.setAll('.');
        for (int i = 0; i < 1024; i++) {
            Pair<Integer, Integer> position = corrupted.get(i);
            matrix.set(position.left, position.right, '#');
        }
        initNodes();
    }

    @Override
    protected Object run() {
        Node start = nodes.stream()
                .filter(n -> n.name.equals(Strings.of(0, 0)))
                .findFirst()
                .orElseThrow();
        Node exit = nodes.stream()
                .filter(n -> n.name.equals(Strings.of(matrix.length()-1, matrix.length()-1)))
                .findFirst()
                .orElseThrow();

        Path solution = new Dijkstra(nodes, start, exit).find().orElseThrow();

        for (int i = 1024; i < corrupted.size(); i++) {
            Pair<Integer, Integer> position = corrupted.get(i);
            matrix.set(position.left, position.right, '#');

            if (!solution.nodes.contains(new Node(Strings.of(position.left, position.right))))
                continue;

            boolean blockingHorizontally = matrix.check(position.left, position.right - 1, '#')
                    && matrix.check(position.left, position.right + 1, '#');
            boolean blockingVertically = matrix.check(position.left + 1, position.right, '#')
                    && matrix.check(position.left - 1, position.right, '#');

            if (!blockingHorizontally && !blockingVertically)
                continue;

            initNodes();
            solution = new Dijkstra(nodes, start, exit).find().orElse(null);
            if (solution == null) {
                return position.right + "," + position.left;
            }
        }

        throw new IllegalStateException();
    }

    private void initNodes() {
        nodes = new ArrayList<>();
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.check(i,j,'.')) {
                    Node node = new Node(Strings.of(i,j));
                    Stream.of(Pair.of(i - 1, j),
                                    Pair.of(i + 1, j),
                                    Pair.of(i, j - 1),
                                    Pair.of(i, j + 1))
                            .filter(pos -> matrix.check(pos.left, pos.right, '.'))
                            .forEach(pos -> node.links.add(new Link(1, Strings.of(pos.left, pos.right))));
                    if (!node.links.isEmpty()) {
                        nodes.add(node);
                    }
                }
            }
        }
    }

}
