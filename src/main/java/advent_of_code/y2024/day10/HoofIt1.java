package advent_of_code.y2024.day10;


import advent_of_code.utils.Characters;
import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoofIt1 extends AbstractRunnable {

    public static void main(String[] args) {
        new HoofIt1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    Set<String> topPositions = new HashSet<>();
    List<Trailhead> trailheads = new ArrayList<>();

    record Trailhead(int i, int j, int score) {}

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        findTrailheads();
        return trailheads.stream().map(Trailhead::score).mapToInt(x -> x).sum();
    }

    private void findTrailheads() {
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == '0') {
                    int score = score(i, j, i, j);
                    if (score > 0) {
                        trailheads.add(new Trailhead(i,j,score));
                    }
                }
            }
        }
    }

    private int score(int i, int j, int startI, int startJ) {

        if (matrix.get(i,j) == '9') {
            String stringPos = Strings.of(i, j, startI, startJ);
            if (!topPositions.contains(stringPos)) {
                topPositions.add(stringPos);
                return 1;
            }
            return 0;
        }

        List<Pair<Integer, Integer>> neighbors = getNeighbors(i, j);

        return neighbors.stream()
                .map(n -> score(n.left, n.right, startI, startJ))
                .mapToInt(x -> x)
                .sum();
    }

    private List<Pair<Integer, Integer>> getNeighbors(int i, int j) {
        char nextHeight = Characters.fromInt(Character.getNumericValue(matrix.get(i,j)) + 1);
        List<Pair<Integer, Integer>> neighbors = new ArrayList<>();

        if (matrix.check(i-1, j, nextHeight)) {
            neighbors.add(Pair.of(i-1, j));
        }

        if (matrix.check(i+1, j, nextHeight)) {
            neighbors.add(Pair.of(i+1, j));
        }

        if (matrix.check(i, j+1, nextHeight)) {
            neighbors.add(Pair.of(i, j+1));
        }

        if (matrix.check(i, j-1, nextHeight)) {
            neighbors.add(Pair.of(i, j-1));
        }

        return neighbors;
    }
}
