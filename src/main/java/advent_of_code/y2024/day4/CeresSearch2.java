package advent_of_code.y2024.day4;


import advent_of_code.utils.Matrix;
import advent_of_code.y2024.AbstractRunnable;

public class CeresSearch2 extends AbstractRunnable {

    public static void main(String[] args) {
        new CeresSearch2().start();
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
                if (matrix.get(i,j) == 'A' && isXmas(i, j))
                    count ++;
            }
        }
        return count;
    }

    private boolean isXmas(int i, int j) {

        boolean condition1 = matrix.check(i - 1, j - 1, 'M')
                && matrix.check(i - 1, j + 1, 'M')
                && matrix.check(i + 1, j + 1, 'S')
                && matrix.check(i + 1, j - 1, 'S');

        boolean condition2 = matrix.check(i - 1, j - 1, 'S')
                && matrix.check(i - 1, j + 1, 'M')
                && matrix.check(i + 1, j + 1, 'M')
                && matrix.check(i + 1, j - 1, 'S');

        boolean condition3 = matrix.check(i - 1, j - 1, 'S')
                && matrix.check(i - 1, j + 1, 'S')
                && matrix.check(i + 1, j + 1, 'M')
                && matrix.check(i + 1, j - 1, 'M');

        boolean condition4 = matrix.check(i - 1, j - 1, 'M')
                && matrix.check(i - 1, j + 1, 'S')
                && matrix.check(i + 1, j + 1, 'S')
                && matrix.check(i + 1, j - 1, 'M');

        return condition1 || condition2 || condition3 || condition4;
    }



}
