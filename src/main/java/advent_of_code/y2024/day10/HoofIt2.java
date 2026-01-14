package advent_of_code.y2024.day10;


import advent_of_code.utils.Characters;
import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class HoofIt2 extends AbstractRunnable {

    public static void main(String[] args) {
        new HoofIt2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    List<Trailhead> trailheads = new ArrayList<>();

    record Trailhead(int i, int j, int rating) {}

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        findTrailheads();
        return trailheads.stream().map(Trailhead::rating).mapToInt(x -> x).sum();
    }

    private void findTrailheads() {
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == '0') {
                    int rating = rating(i, j);
                    if (rating > 0) {
                        trailheads.add(new Trailhead(i,j,rating));
                    }
                }
            }
        }
    }

    private int rating(int i, int j) {

        if (matrix.get(i,j) == '9') {
            return 1;
        }

        List<Pair<Integer, Integer>> neighbors = getNeighbors(i, j);

        return neighbors.stream()
                .map(n -> rating(n.left, n.right))
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
