package advent_of_code.y2023.day11;


import advent_of_code.y2023.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class CosmicExpansion extends AbstractRunnable {

    public static void main(String[] args) {
        new CosmicExpansion().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day11/image.txt";
    }

    record Galaxy(int i, int j) {
        @Override
        public boolean equals(Object obj) {
            Galaxy other = (Galaxy) obj;
            return this.i == other.i && this.j == other.j;
        }
    }

    List<List<Character>> image;

    List<Galaxy> galaxies;
    List<Integer> indexesRowsToExpand;
    List<Integer> indexesColsToExpand;

    @Override
    public void run() {

        parseFile();

        findExpansion();

        initGalaxies();

        long expansionParameter = 1_000_000L;
        long result = 0;

        for(Galaxy galaxy : galaxies) {
            for(Galaxy other : galaxies) {
                if (other.equals(galaxy)) continue;

                long rowsToExpandThrough = indexesRowsToExpand.stream()
                        .filter(val -> isInRange(val, galaxy.i, other.i))
                        .count();

                long colsToExpandThrough = indexesColsToExpand.stream()
                        .filter(val -> isInRange(val, galaxy.j, other.j))
                        .count();

                result += Math.abs(galaxy.i - other.i) + (expansionParameter-1) * rowsToExpandThrough
                        + Math.abs(galaxy.j - other.j) + (expansionParameter-1) * colsToExpandThrough;
            }
        }

        System.out.println(result/2);
    }

    private boolean isInRange(int value, int n1, int n2) {
        int first = Math.min(n1, n2);
        int second = Math.max(n1, n2);
        return value >= first && value <= second;
    }

    private void initGalaxies() {
        galaxies = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            List<Character> characters = image.get(i);
            for (int j = 0; j < characters.size(); j++) {
                if(characters.get(j) == '#') {
                    galaxies.add(new Galaxy(i,j));
                }
            }
        }
    }

    void findExpansion() {

        indexesRowsToExpand = new ArrayList<>();
        indexesColsToExpand = new ArrayList<>();

        for (int i = 0; i < image.size(); i++) {
            List<Character> row = image.get(i);
            if (row.stream().allMatch(c -> c == '.')) {
                indexesRowsToExpand.add(i);
            }
        }

        for (int j = 0; j < image.get(0).size(); j++) {
            List<Character> col = new ArrayList<>();
            for (List<Character> row : image) {
                col.add(row.get(j));
            }
            if (col.stream().allMatch(c -> c == '.')) {
                indexesColsToExpand.add(j);
            }
        }
    }

    private void parseFile() {

        List<String> lines = readLines().toList();

        image = new ArrayList<>();

        for (String line : lines) {
            List<Character> list = new ArrayList<>();
            for (char c : line.toCharArray()) {
                list.add(c);
            }
            image.add(list);
        }
    }
}

