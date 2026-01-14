package advent_of_code.y2024.day15;


import advent_of_code.utils.Matrix;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static advent_of_code.y2024.day15.WarehouseWoes1.Direction.*;

public class WarehouseWoes1 extends AbstractRunnable {

    public static void main(String[] args) {
        new WarehouseWoes1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    static Matrix map;
    static Robot robot;
    static List<Position> boxes;
    static List<Position> walls;

    enum Direction {UP, DOWN, LEFT, RIGHT}
    List<Direction> instructions;

    static class Position {
        int i, j;
        Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
        @Override
        public boolean equals(Object o) {
            Position other = (Position) o;
            return this.i == other.i && this.j == other.j;
        }
        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    static class Robot {

        Position position;
        Robot(Position position) {
            this.position = position;
        }

        void move(Direction direction) {
            Position nextPosition = getNextPosition(this.position, direction);
            if (walls.contains(nextPosition))
                return;
            if (!boxes.contains(nextPosition)) {
                this.position = nextPosition;
                return;
            }
            List<Position> boxesToShift = getBoxesToShift(nextPosition, direction);
            if (boxesToShift.isEmpty())
                return;
            this.position = nextPosition;
            shift(boxesToShift, direction);
        }

        private void shift(List<Position> boxesToShift, Direction direction) {
            List<Position> newBoxes = new ArrayList<>();
            for (Position box : boxes) {
                if (boxesToShift.contains(box)) {
                    newBoxes.add(getNextPosition(box, direction));
                } else {
                    newBoxes.add(box);
                }
            }
            boxes = newBoxes;
        }

        private List<Position> getBoxesToShift(Position position, Direction direction) {
            List<Position> boxesToShift = new ArrayList<>();
            boxesToShift.add(position);
            boolean boxesAligned;
            do {
                Position next = getNextPosition(boxesToShift.getLast(), direction);
                if (walls.contains(next))
                    return Collections.emptyList();

                boxesAligned = boxes.stream().anyMatch(box -> box.equals(next));

                if (boxesAligned)
                    boxesToShift.add(next);

            } while(boxesAligned);
            return boxesToShift;
        }
    }

    @Override
    protected void init() {
        boxes = new ArrayList<>();
        walls = new ArrayList<>();
        List<List<String>> blocks = readBlocksOfLines();
        map = new Matrix(readLinesToMatrix(blocks.getFirst()));
        for (int i = 0; i < map.length(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                char c = map.get(i, j);
                if (c == '#') walls.add(new Position(i, j));
                if (c == 'O') boxes.add(new Position(i, j));
                if (c == '@') robot = new Robot(new Position(i, j));
            }
        }
        instructions = new ArrayList<>();
        for (String line : blocks.getLast()) {
            for (char c : line.toCharArray()) {
                if (c == '<') instructions.add(LEFT);
                if (c == '>') instructions.add(RIGHT);
                if (c == '^') instructions.add(UP);
                if (c == 'v') instructions.add(DOWN);
            }
        }
    }

    @Override
    protected Object run() {
        instructions.forEach(direction -> robot.move(direction));
        return boxes.stream().map(this::gpsCoordinate).mapToInt(x -> x).sum();
    }

    static Position getNextPosition(Position position, Direction direction) {
        return switch (direction) {
            case UP -> new Position(position.i - 1, position.j);
            case DOWN -> new Position(position.i + 1, position.j);
            case LEFT -> new Position(position.i, position.j - 1);
            case RIGHT -> new Position(position.i, position.j + 1);
        };
    }

    int gpsCoordinate(Position position) {
        return 100 * position.i + position.j;
    }

    static void updateMap() {
        map.setAll('.');
        for (int i = 0; i < map.length(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                Position position = new Position(i, j);
                if (walls.contains(position)) {
                    map.set(i,j,'#');
                } else if (boxes.contains(position)) {
                    map.set(i,j,'O');
                } else if (robot.position.equals(position)) {
                    map.set(i,j,'@');
                }
            }
        }
    }
}

