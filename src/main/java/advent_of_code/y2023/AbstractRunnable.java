package advent_of_code.y2023;

import advent_of_code.utils.Files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractRunnable implements Runnable {

    protected abstract String getPath();

    protected Stream<String> readLines() {
        return Files.readLines(getPath());
    }

    protected void start() {
        long start = System.currentTimeMillis();
        run();
        System.out.println("\nduration millis: " + (System.currentTimeMillis() - start));
    }

    protected Map<Integer, String> readLinesToMap() {
        Map<Integer, String> map = new HashMap<>();
        List<String> lines = readLines().toList();
        IntStream.range(0, lines.size()).forEach(index -> map.put(index+1, lines.get(index)));
        return map;
    }

    protected char[][] readLinesToMatrix() {
        List<String> lines = readLines().toList();
        char[][] matrix = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }
        return matrix;
    }

    protected void printMatrix(char[][] matrix) {
        for (char[] line : matrix) {
            for (char c : line) {
                System.out.print(" " + c);
            }
            System.out.println();
        }
    }
}
