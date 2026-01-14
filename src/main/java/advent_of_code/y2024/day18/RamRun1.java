package advent_of_code.y2024.day18;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Strings;
import advent_of_code.utils.dijkstra.Dijkstra;
import advent_of_code.utils.dijkstra.Node;
import advent_of_code.y2024.AbstractRunnable;

import java.util.List;

public class RamRun1 extends AbstractRunnable {

    public static void main(String[] args) {
        new RamRun1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;

    List<Node> nodes;

    @Override
    protected void init() {
        matrix = new Matrix(new char[71][71]);
        matrix.setAll('.');
        readLines().toList().subList(0, 1024).forEach(line -> {
            String[] split = line.split(",");
            matrix.set(Integer.parseInt(split[1]), Integer.parseInt(split[0]), '#');
        });
        nodes = Dijkstra.getNodesFromMatrix(matrix, '.');
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
        return new Dijkstra(nodes, start, exit).find().orElseThrow().cost;
    }

}
