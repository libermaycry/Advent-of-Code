package advent_of_code.y2025.day4;


import advent_of_code.utils.Matrix;
import advent_of_code.y2025.AbstractRunnable;

import java.util.stream.Stream;

public class PrintingDepartment1 extends AbstractRunnable {

    public static void main(String[] args) {
        new PrintingDepartment1().start();
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
                if (matrix.check(i,j,'@') && isAccessible(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isAccessible(int i, int j) {
        long count = Stream.of(
                        isPaper(i - 1, j - 1),
                        isPaper(i - 1, j),
                        isPaper(i - 1, j + 1),
                        isPaper(i + 1, j - 1),
                        isPaper(i + 1, j),
                        isPaper(i + 1, j + 1),
                        isPaper(i, j - 1),
                        isPaper(i, j + 1)
                ).filter(x -> x)
                .count();
        return count < 4;
    }

    private boolean isPaper(int i, int j) {
        return matrix.check(i, j, '@');
    }

}
