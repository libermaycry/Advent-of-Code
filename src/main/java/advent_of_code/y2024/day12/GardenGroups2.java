package advent_of_code.y2024.day12;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GardenGroups2 extends AbstractRunnable {

    public static void main(String[] args) {
        new GardenGroups2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Region(String type,
            List<Pair<Integer, Integer>> positions) {}

    List<Region> regions;
    Matrix matrix;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
        findRegions();
    }

    @Override
    protected Object run() {
        return regions.stream().map(this::getPrice).mapToInt(x -> x).sum();
    }

    private int getPrice(Region region) {
        return getSides(region) * region.positions.size();
    }

    private int getSides(Region region) {

        int sides = 0;
        Set<String> externalPositions = new HashSet<>();

        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                int finalI = i;
                int finalJ = j;

                Pair<Integer, Integer> position = region.positions.stream()
                        .filter(p -> p.left.equals(finalI) && p.right.equals(finalJ))
                        .findAny()
                        .orElse(null);

                if (position != null) {

                    int I, J;
                    String orientation;

                    I = position.left;
                    J = position.right + 1;
                    orientation = "R";
                    if (differentType(region, I, J)) {
                        if (!externalPositions.contains(Strings.of(I-1,J,orientation))
                                && !externalPositions.contains(Strings.of(I+1,J,orientation))) {
                            sides ++;
                        }
                        externalPositions.add(Strings.of(I,J,orientation));
                    }

                    I = position.left;
                    J = position.right - 1;
                    orientation = "L";
                    if (differentType(region, I, J)) {
                        if (!externalPositions.contains(Strings.of(I-1,J,orientation))
                                && !externalPositions.contains(Strings.of(I+1,J,orientation))) {
                            sides ++;
                        }
                        externalPositions.add(Strings.of(I,J,orientation));
                    }

                    I = position.left - 1;
                    J = position.right;
                    orientation = "U";
                    if (differentType(region, I, J)) {
                        if (!externalPositions.contains(Strings.of(I,J-1,orientation))
                                && !externalPositions.contains(Strings.of(I,J+1,orientation))) {
                            sides ++;
                        }
                        externalPositions.add(Strings.of(I,J,orientation));
                    }

                    I = position.left + 1;
                    J = position.right;
                    orientation = "D";
                    if (differentType(region, I, J)) {
                        if (!externalPositions.contains(Strings.of(I,J-1,orientation))
                                && !externalPositions.contains(Strings.of(I,J+1,orientation))) {
                            sides ++;
                        }
                        externalPositions.add(Strings.of(I,J,orientation));
                    }
                }
            }
        }

        return sides;
    }

    private boolean differentType(Region region, int I, int J) {
        return matrix.isOutside(I,J) || !String.valueOf(matrix.get(I,J)).equals(region.type);
    }

    private void findRegions() {
        regions = new ArrayList<>();
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                int finalI = i;
                int finalJ = j;
                if (regions.stream()
                        .noneMatch(region -> region.positions.stream()
                                .anyMatch(position -> position.left.equals(finalI) && position.right.equals(finalJ)))) {
                    findRegionFor(i, j);
                }
            }
        }
    }

    private void findRegionFor(int i, int j) {
        Region region = new Region(String.valueOf(matrix.get(i,j)), new ArrayList<>());
        region.positions.add(Pair.of(i,j));
        visit(i, j, region);
        regions.add(region);
    }

    private void visit(int i, int j, Region region) {

        List<Pair<Integer, Integer>> neighbors = getNeighborsSameType(i, j);
        neighbors.removeIf(neighbor ->
                region.positions.stream().anyMatch(position ->
                        position.left.equals(neighbor.left) && position.right.equals(neighbor.right)));

        neighbors.forEach(neighbor -> region.positions.add(Pair.of(neighbor.left, neighbor.right)));
        neighbors.forEach(neighbor -> visit(neighbor.left, neighbor.right, region));
    }

    private List<Pair<Integer, Integer>> getNeighborsSameType(int i, int j) {
        List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
        if (matrix.check(i-1, j, matrix.get(i,j))) {
            neighbors.add(Pair.of(i-1, j));
        }
        if (matrix.check(i+1, j, matrix.get(i,j))) {
            neighbors.add(Pair.of(i+1, j));
        }
        if (matrix.check(i, j+1, matrix.get(i,j))) {
            neighbors.add(Pair.of(i, j+1));
        }
        if (matrix.check(i, j-1, matrix.get(i,j))) {
            neighbors.add(Pair.of(i, j-1));
        }
        return neighbors;
    }

}
