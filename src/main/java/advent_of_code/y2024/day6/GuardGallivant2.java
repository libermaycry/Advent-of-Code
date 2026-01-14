package advent_of_code.y2024.day6;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.HashSet;

public class GuardGallivant2 extends AbstractRunnable {

    public static void main(String[] args) {
        new GuardGallivant2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    Pair<Integer, Integer> start;
    int count = 0;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
        start = findStart();
    }

    private Pair<Integer, Integer> findStart() {
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == '^') {
                    return Pair.of(i, j);
                }
            }
        }
        throw new IllegalStateException();
    }

    @Override
    protected Object run() {

        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                if (matrix.get(i,j) == '.') {
                    int I = i;
                    int J = j;
                    addThread(() -> tryPlaceObstacle(I, J));
                }
            }
        }

        waitThreads();
        return count;
    }

    private void tryPlaceObstacle(int i, int j) {
        Matrix copy = matrix.copy();
        copy.set(i,j,'#');
        if (isLooped(copy))
            synchronized (this) {count ++;}
    }

    private boolean isLooped(Matrix copy) {

        Guard guard = new Guard(start.left, start.right);
        HashSet<String> path = new HashSet<>();
        path.add(guard.stringPosition());

        while(!guard.isLeaving(copy)) {

            if (guard.isBlocked(copy)) guard.turn();
            else guard.move();

            if (path.contains(guard.stringPosition()))
                return true;

            path.add(guard.stringPosition());
        }

        return false;
    }


    enum Direction { UP, DOWN, LEFT, RIGHT }

    class Guard {

        int i,j;
        Direction direction;

        Guard(int i, int j) {
            this.i = i;
            this.j = j;
            this.direction = Direction.UP;
        }

        boolean isBlocked(Matrix matrix) {
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

        boolean isLeaving(Matrix matrix) {
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

        String stringPosition() {
            return Strings.of(this.i, this.j, this.direction);
        }

    }

}
