package advent_of_code.y2024.day20;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Position;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class RaceCondition1 extends AbstractRunnable {

    public static void main(String[] args) {
        new RaceCondition1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    List<Position> track;

    int cheats = 0;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {

        track = getTrackPositions();

        for (Position position : track) {
            Position right = new Position(position.i, position.j + 1);
            Position doubleRight = new Position(position.i, position.j + 2);
            if (matrix.check(right.i, right.j, '#') && floorOrExit(doubleRight)) {
                checkSide(position, right, doubleRight);
            }

            Position left = new Position(position.i, position.j - 1);
            Position doubleLeft = new Position(position.i, position.j - 2);
            if (matrix.check(left.i, left.j, '#') && floorOrExit(doubleLeft)) {
                checkSide(position, left, doubleLeft);
            }

            Position up = new Position(position.i - 1, position.j);
            Position doubleUp = new Position(position.i - 2, position.j);
            if (matrix.check(up.i, up.j, '#') && floorOrExit(doubleUp)) {
                checkSide(position, up, doubleUp);
            }

            Position down = new Position(position.i + 1, position.j);
            Position doubleDown = new Position(position.i + 2, position.j);
            if (matrix.check(down.i, down.j, '#') && floorOrExit(doubleDown)) {
                checkSide(position, down, doubleDown);
            }
        }

        return cheats;
    }

    private boolean floorOrExit(Position position) {
        return matrix.check(position.i, position.j, '.') || matrix.check(position.i, position.j, 'E');
    }

    private void checkSide(Position current, Position wall, Position otherSide) {

        List<Position> fromStartToCurrent = track.subList(0, track.indexOf(current));
        List<Position> fromOtherSideToEnd = track.subList(track.indexOf(otherSide), track.size());

        List<Position> newTrack = new ArrayList<>(fromStartToCurrent);
        newTrack.add(wall);
        newTrack.addAll(fromOtherSideToEnd);

        int saved = track.size() - newTrack.size() - 1;
        if (saved >= 100) {
            cheats ++;
        }
    }

    private List<Position> getTrackPositions() {
        Pair<Integer, Integer> current = matrix.getFirstPositionByValue('S');
        Pair<Integer, Integer> exit = matrix.getFirstPositionByValue('E');
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(current.left, current.right));
        while (!current.left.equals(exit.left) || !current.right.equals(exit.right)) {
            current = getNext(current, positions);
            positions.add(new Position(current.left, current.right));
        }
        return positions;
    }

    private Pair<Integer, Integer> getNext(Pair<Integer, Integer> current, List<Position> positions) {

        Pair<Integer, Integer> top = Pair.of(current.left - 1, current.right);
        if (isNextOnTrack(top, positions)) {
            return top;
        }

        Pair<Integer, Integer> down = Pair.of(current.left + 1, current.right);
        if (isNextOnTrack(down, positions)) {
            return down;
        }

        Pair<Integer, Integer> left = Pair.of(current.left, current.right - 1);
        if (isNextOnTrack(left, positions)) {
            return left;
        }

        Pair<Integer, Integer> right = Pair.of(current.left, current.right + 1);
        if (isNextOnTrack(right, positions)) {
            return right;
        }

        throw new IllegalStateException();
    }

    private boolean isNextOnTrack(Pair<Integer, Integer> pos, List<Position> positions) {
        return (matrix.check(pos.left, pos.right, '.') || matrix.check(pos.left, pos.right, 'E'))
                && !positions.contains(new Position(pos.left, pos.right));
    }

}
