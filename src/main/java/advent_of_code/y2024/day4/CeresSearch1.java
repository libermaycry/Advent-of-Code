package advent_of_code.y2024.day4;


import advent_of_code.utils.Matrix;
import advent_of_code.y2024.AbstractRunnable;

public class CeresSearch1 extends AbstractRunnable {

    public static void main(String[] args) {
        new CeresSearch1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        int count = 0;
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == 'X') {
                    for (Matrix.Direction direction : Matrix.Direction.values()) {
                        if (isXmas(i, j, direction)) count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isXmas(int i, int j, Matrix.Direction direction) {
        return switch (direction) {
            case N -> matrix.check(i - 1, j, 'M')
                   && matrix.check(i - 2, j, 'A')
                   && matrix.check(i - 3, j, 'S');
            case NE -> matrix.check(i - 1, j + 1, 'M')
                    && matrix.check(i - 2, j + 2, 'A')
                    && matrix.check(i - 3, j + 3, 'S');
            case E -> matrix.check(i, j + 1, 'M')
                   && matrix.check(i, j + 2, 'A')
                   && matrix.check(i, j + 3, 'S');
            case SE -> matrix.check(i + 1, j + 1, 'M')
                    && matrix.check(i + 2, j + 2, 'A')
                    && matrix.check(i + 3, j + 3, 'S');
            case S -> matrix.check(i + 1, j, 'M')
                   && matrix.check(i + 2, j, 'A')
                   && matrix.check(i + 3, j, 'S');
            case SW -> matrix.check(i + 1, j - 1, 'M')
                    && matrix.check(i + 2, j - 2, 'A')
                    && matrix.check(i + 3, j - 3, 'S');
            case W -> matrix.check(i, j - 1, 'M')
                   && matrix.check(i, j - 2, 'A')
                   && matrix.check(i, j - 3, 'S');
            case NW -> matrix.check(i - 1, j - 1, 'M')
                    && matrix.check(i - 2, j - 2, 'A')
                    && matrix.check(i - 3, j - 3, 'S');
        };
    }



}
