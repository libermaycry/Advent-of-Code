package advent_of_code.y2023.day13;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class PointOfIncidence_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new PointOfIncidence_Part1().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day13/patterns.txt";
    }

    List<char[][]> patterns;

    record Focal (int index1, int index2, int len) {}

    @Override
    public void run() {

        parseFile();

        int result = 0;

        for (char[][] pattern : patterns) {

            Focal focalVertical = getVerticalFocal(pattern);

            Focal focalHorizontal = getHorizontalFocal(pattern);

            if (focalVertical == null) {
                result += 100 * (focalHorizontal.index1 + 1);
            } else {
                result += focalVertical.index1 + 1;
            }
        }

        System.out.println(result);
    }

    private Focal getVerticalFocal(char[][] pattern) {

        for (int j = 0; j < pattern[0].length - 1; j++) {
            List<Character> col = getCol(j, pattern);
            List<Character> col2 = getCol(j+1, pattern);
            if (col.equals(col2)) {
                int left = j - 1;
                int right = j + 2;
                while(true) {
                    if(left < 0 || right > pattern[0].length - 1) {
                        return new Focal(j, j+1, -1);
                    }
                    if (!getCol(left, pattern).equals(getCol(right, pattern))) {
                        break;
                    }
                    left--;
                    right++;
                }
            }
        }

        return null;
    }

    private Focal getHorizontalFocal(char[][] pattern) {

        for (int i = 0; i < pattern.length - 1; i++) {
            List<Character> row = getRow(i, pattern);
            List<Character> row2 = getRow(i+1, pattern);
            if (row.equals(row2)) {
                int up = i - 1;
                int down = i + 2;
                while(true) {
                    if(up < 0 || down > pattern.length - 1) {
                        return new Focal(i, i+1, -1);
                    }
                    if (!getRow(up, pattern).equals(getRow(down, pattern))) {
                        break;
                    }
                    up--;
                    down++;
                }
            }
        }

        return null;
    }

    private List<Character> getRow(int i, char[][] pattern) {
        List<Character> chars = new ArrayList<>();
        for (int j = 0; j < pattern[i].length; j++) {
            chars.add(pattern[i][j]);
        }
        return chars;
    }

    private List<Character> getCol(int j, char[][] pattern) {
        List<Character> chars = new ArrayList<>();
        for (char[] value : pattern) {
            chars.add(value[j]);
        }
        return chars;
    }

    void parseFile() {

        List<String> lines = readLines().toList();

        List<Integer> blankLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            if (Strings.isBlank(lines.get(i))) {
                blankLines.add(i);
            }
        }

        patterns = new ArrayList<>();

        for (int i = 0; i < blankLines.size(); i++) {
            if (i == 0) {
                patterns.add(toPattern(lines.subList(0, blankLines.get(i))));
            } else {
                patterns.add(toPattern(lines.subList(blankLines.get(i-1) + 1, blankLines.get(i))));
            }
        }

        patterns.add(toPattern(lines.subList(blankLines.get(blankLines.size()-1) + 1, lines.size())));
    }

    private char[][] toPattern(List<String> lines) {
        char[][] matrix = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }
        return matrix;
    }


}

