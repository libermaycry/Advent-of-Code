package advent_of_code.y2025.day4;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Position;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PrintingDepartment2 extends AbstractRunnable {

    public static void main(String[] args) {
        new PrintingDepartment2().start();
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
        List<Position> toRemove = new ArrayList<>();
        int count = 0;
        do {
            toRemove.clear();
            for (int i = 0; i < matrix.length(); i++) {
                for (int j = 0; j < matrix.get(i).length; j++) {
                    if (matrix.check(i,j,'@') && isAccessible(i,j)) {
                        count++;
                        toRemove.add(new Position(i,j));
                    }
                }
            }
            toRemove.forEach(position -> matrix.set(position.i, position.j, '.'));
        } while (!toRemove.isEmpty());
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
