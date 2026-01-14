package advent_of_code.y2025.day7;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Position;
import advent_of_code.y2025.AbstractRunnable;

import java.util.HashMap;
import java.util.Map;

public class Laboratories2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Laboratories2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    Map<Position, Long> cache = new HashMap<>();

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        Pair<Integer, Integer> pair = matrix.getFirstPositionByValue('S');
        return new Beam(new Position(pair.left, pair.right)).move();
    }


    class Beam {

        Position position;

        Beam(Position position) {
            this.position = position;
        }


        long move() {
            Position next = new Position(position.i + 1, position.j);

            if (matrix.isOutside(next.i, next.j)) {
                return 1;
            }

            if (isFloor(next)) {
                position = next;
                return move();
            }

            Position left = new Position(next.i, next.j - 1);
            long leftResult = cache.containsKey(left) ? cache.get(left) : new Beam(left).move();
            cache.put(left, leftResult);

            Position right = new Position(next.i, next.j + 1);
            long rightResult = cache.containsKey(right) ? cache.get(right) : new Beam(right).move();
            cache.put(right, rightResult);

            return leftResult + rightResult;
        }

        private boolean isFloor(Position next) {
            return matrix.check(next.i, next.j, '.');
        }

    }

}
