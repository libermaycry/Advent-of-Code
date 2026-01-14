package advent_of_code.y2024.day25;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class CodeChronicle extends AbstractRunnable {

    public static void main(String[] args) {
        new CodeChronicle().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Pin(List<Integer> heights) {}

    List<Pin> locks = new ArrayList<>();
    List<Pin> keys = new ArrayList<>();

    @Override
    protected void init() {
        readBlocksOfLines().forEach(block -> {
            char[][] matrix = readLinesToMatrix(block);
            if (matrix[0][0] == '.') keys.add(convertToKey(matrix));
            else locks.add(convertToLock(matrix));
        });
    }

    private Pin convertToLock(char[][] matrix) {
        List<Integer> heights = new ArrayList<>();
        for (int j = 0; j < matrix[0].length; j++) {
            int height = 0;
            for (int i = 1; i < matrix.length; i++) {
                if (matrix[i][j] == '.')
                    break;
                height ++;
            }
            heights.add(height);
        }
        return new Pin(heights);
    }

    private Pin convertToKey(char[][] matrix) {
        List<Integer> heights = new ArrayList<>();
        for (int j = 0; j < matrix[0].length; j++) {
            int height = 0;
            for (int i = matrix.length - 2; i >= 1; i--) {
                if (matrix[i][j] == '.') {
                    break;
                }
                height ++;
            }
            heights.add(height);
        }
        return new Pin(heights);
    }

    @Override
    protected Object run() {
        int result = 0;

        for (Pin key : keys) {
            for (Pin lock : locks) {
                if (fit(key, lock)) {
                    result ++;
                }
            }
        }
        return result;
    }

    private boolean fit(Pin key, Pin lock) {
        for (int i = 0; i < key.heights.size(); i++) {
            int kh = key.heights.get(i);
            int lh = lock.heights.get(i);
            if (kh + lh > 5) return false;
        }
        return true;
    }
}
