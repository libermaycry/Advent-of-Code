package advent_of_code.y2025.day7;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Position;
import advent_of_code.y2025.AbstractRunnable;

public class Laboratories1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Laboratories1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    long result = 0;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        Pair<Integer, Integer> pair = matrix.getFirstPositionByValue('S');
        new Beam(new Position(pair.left, pair.right)).move();
        return result;
    }


    class Beam {

        Position position;

        Beam(Position position) {
            this.position = position;
            markVisited(position);
        }

        void move() {
            Position next = new Position(position.i + 1, position.j);
            if (matrix.isOutside(next.i, next.j) || isVisited(next))
                return;
            if (isFloor(next)) {
                markVisited(next);
                position = next;
                move();
                return;
            }
            result ++;
            new Beam(new Position(next.i, next.j - 1)).move();
            new Beam(new Position(next.i, next.j + 1)).move();
        }

        private boolean isFloor(Position next) {
            return matrix.check(next.i, next.j, '.');
        }


        private boolean isVisited(Position next) {
            return matrix.check(next.i, next.j, '|');
        }

        private void markVisited(Position pos) {
            matrix.set(pos.i, pos.j, '|');
        }

    }

}
