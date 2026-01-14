package advent_of_code.y2024.day20;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Position;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RaceCondition2 extends AbstractRunnable {

    public static void main(String[] args) {
        new RaceCondition2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    List<Position> track;

    int CHEAT_MAX = 20;
    int cheats = 0;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
    }

    @Override
    protected Object run() {
        track = getTrackPositions();
        for (Position current : track) {
            for (Position cheat : getCheatablePositions(current)) {
                int fromStartToCurrent = track.subList(0, track.indexOf(current)).size();
                int fromCurrentToCheat = Math.abs(current.i - cheat.i) + Math.abs(current.j - cheat.j);
                int fromCheatToEnd = track.subList(track.indexOf(cheat), track.size()).size();
                int saved = track.size() - fromStartToCurrent - fromCurrentToCheat - fromCheatToEnd;
                if (saved >= 100)
                    cheats ++;
            }
        }
        return cheats;
    }

    private List<Position> getCheatablePositions(Position position) {
        HashSet<Position> positions = new HashSet<>();
        for (int j = position.j - CHEAT_MAX; j <= position.j; j++) {
            int vertical = CHEAT_MAX - Math.abs(position.j - j);
            for (int i = position.i - vertical; i <= position.i + vertical; i++) {
                positions.add(new Position(i,j));
                positions.add(new Position(i, position.j + position.j - j));
            }
        }
        positions.removeIf(p -> !matrix.check(p.i, p.j, '.') && !matrix.check(p.i, p.j, 'E'));
        positions.removeIf(p -> p.equals(position));
        return new ArrayList<>(positions);
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
