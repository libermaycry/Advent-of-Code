package advent_of_code.y2024.day6;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class GuardGallivant1 extends AbstractRunnable {

    public static void main(String[] args) {
        new GuardGallivant1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    Guard guard;
    List<Pair<Integer, Integer>> visited;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == '^') {
                    guard = new Guard(i, j, Direction.UP);
                }
            }
        }
        visited = new ArrayList<>();
        visited.add(Pair.of(guard.i, guard.j));
    }

    @Override
    protected Object run() {

        while(!guard.isLeaving()) {

            if (guard.isBlocked())
                guard.turn();

            guard.move();

            if (visited.stream().noneMatch(pos -> pos.left == guard.i && pos.right == guard.j)) {
                visited.add(Pair.of(guard.i, guard.j));
            }
        }

        return visited.size();
    }


    enum Direction { UP, DOWN, LEFT, RIGHT }

    class Guard {

        int i,j;
        Direction direction;

        Guard(int i, int j, Direction direction) {
            this.i = i;
            this.j = j;
            this.direction = direction;
        }

        boolean isBlocked() {
            return switch(direction) {
                case UP -> matrix.check(i - 1, j, '#');
                case DOWN -> matrix.check(i + 1, j, '#');
                case RIGHT -> matrix.check(i, j + 1, '#');
                case LEFT -> matrix.check(i, j - 1, '#');
            };
        }

        void turn() {
            switch (direction) {
                case UP -> direction = Direction.RIGHT;
                case RIGHT -> direction = Direction.DOWN;
                case DOWN -> direction = Direction.LEFT;
                case LEFT -> direction = Direction.UP;
            }
        }

        boolean isLeaving() {
            return switch(direction) {
                case UP -> matrix.isOutside(i - 1, j);
                case DOWN -> matrix.isOutside(i + 1, j);
                case RIGHT -> matrix.isOutside(i, j + 1);
                case LEFT -> matrix.isOutside(i, j - 1);
            };
        }

        void move() {
            switch (direction) {
                case UP -> i--;
                case RIGHT -> j++;
                case DOWN -> i++;
                case LEFT -> j--;
            }
        }

    }

}
