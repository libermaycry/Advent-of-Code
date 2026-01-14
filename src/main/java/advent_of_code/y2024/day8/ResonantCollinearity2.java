package advent_of_code.y2024.day8;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.HashSet;

public class ResonantCollinearity2 extends AbstractRunnable {

    public static void main(String[] args) {
        new ResonantCollinearity2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    HashSet<String> antinodes;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
        antinodes = new HashSet<>();
    }

    @Override
    protected Object run() {
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) != '.') {
                    addAntinodesFor(i,j);
                }
            }
        }
        return antinodes.size();
    }

    private void addAntinodesFor(int I, int J) {
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == matrix.get(I,J) && i != I && j != J) {
                    antinodes.add(antinodeString(i,j));
                    antinodes.add(antinodeString(I,J));
                    int antI = 2*I - i;
                    int antJ = 2*J - j;
                    while (matrix.isInside(antI, antJ)) {
                        antinodes.add(antinodeString(antI, antJ));
                        antI += I - i;
                        antJ += J - j;
                    }
                }
            }
        }
    }

    private String antinodeString(int i, int j) {
        return Strings.of(i, j);
    }
}
