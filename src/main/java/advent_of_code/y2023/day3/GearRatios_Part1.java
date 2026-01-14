package advent_of_code.y2023.day3;


import advent_of_code.y2023.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class GearRatios_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new GearRatios_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day3/engine_schema.txt";
    }

    String[][] schema;

    record Position(int i, int j) {}

    @Override
    public void run() {

        List<String> lines = readLines().toList();

        schema = new String[lines.size()][lines.get(0).length()];

        int row = 0;

        for (String line : lines) {
            for (int j = 0; j < line.toCharArray().length; j++) {
                schema[row][j] = String.valueOf(line.toCharArray()[j]);
            }
            row++;
        }

        List<Position> positions = new ArrayList<>();

        int sum = 0;

        for (int i=0; i < schema.length; i++) {

            for (int j=0; j < schema[i].length; j++) {

                String value = schema[i][j];

                if (isDigit(value.charAt(0))) {
                    positions.add(new Position(i,j));
                } else {
                    if (!positions.isEmpty() && isPartNumber(positions)) {
                        sum += getNumber(positions);
                    }
                    positions.clear();
                }
            }

            if (!positions.isEmpty() && isPartNumber(positions)) {
                sum += getNumber(positions);
            }

            positions.clear();
        }

        System.out.println(sum);

    }

    private int getNumber(List<Position> positions) {
        String num = "";
        for(Position p : positions) {
            num += schema[p.i][p.j];
        }
        return Integer.parseInt(num);
    }

    private boolean isPartNumber(List<Position> positions) {
        return positions.stream().anyMatch(this::thereIsAdjacentSymbol);
    }

    private boolean thereIsAdjacentSymbol(Position position) {
        for(int i = position.i - 1; i <= position.i + 1; i++) {
            if (i < 0 || i > schema.length - 1) continue;
            for(int j = position.j - 1; j <= position.j + 1; j++) {
                if (j < 0 || j > schema[i].length - 1) continue;
                if (isSymbol(schema[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSymbol(String s) {
        return !s.equals(".") && !isDigit(s.charAt(0));
    }


}

