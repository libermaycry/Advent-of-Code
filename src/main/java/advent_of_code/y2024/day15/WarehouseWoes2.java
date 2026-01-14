package advent_of_code.y2024.day15;


import advent_of_code.utils.Matrix;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static advent_of_code.y2024.day15.WarehouseWoes2.Direction.*;

public class WarehouseWoes2 extends AbstractRunnable {

    public static void main(String[] args) {
        new WarehouseWoes2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    static Matrix map;
    static Robot robot;
    static List<Box> boxes;
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

    static class Box {
        Position left, right;
        Box(Position left, Position right) {
            this.left = left;
            this.right = right;
        }

        public int gpsCoordinate() {
            return 100 * this.left.i + this.left.j;
        }

        public List<Box> getVerticalProximityBoxes(Direction direction) {
            List<Box> boxes = new ArrayList<>();
            Box verticalLeft = findBoxByPosition(getNextPosition(this.left, direction));
            Box verticalRight = findBoxByPosition(getNextPosition(this.right, direction));
            if (verticalLeft != null) boxes.add(verticalLeft);
            if (verticalRight != null) boxes.add(verticalRight);
            return boxes;
        }

        @Override
        public boolean equals(Object o) {
            Box other = (Box) o;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        @Override
        public int hashCode() {
            return Objects.hash(left, right);
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
            Box box = findBoxByPosition(nextPosition);
            if (box == null) {
                this.position = nextPosition;
                return;
            }
            List<Box> boxesToShift = List.of(LEFT, RIGHT).contains(direction)
                    ? getBoxesToShiftHorizontal(box, direction)
                    : getBoxesToShiftVertical(box, direction);
            if (boxesToShift.isEmpty())
                return;
            this.position = nextPosition;
            shift(boxesToShift, direction);
        }
    }

    @Override
    protected void init() {
        boxes = new ArrayList<>();
        walls = new ArrayList<>();
        List<List<String>> blocks = readBlocksOfLines();
        Matrix original = new Matrix(readLinesToMatrix(blocks.getFirst()));
        map = new Matrix(new char[original.length()][original.get(0).length*2]);
        for (int i = 0; i < original.length(); i++) {
            for (int j = 0; j < original.get(i).length; j++) {
                char c = original.get(i, j);
                if (c == 'O') {
                    boxes.add(new Box(new Position(i, 2*j), new Position(i, 2*j+1)));
                } else if (c == '@') {
                    robot = new Robot(new Position(i, 2*j));
                } else if (c == '#') {
                    walls.add(new Position(i, 2*j));
                    walls.add(new Position(i, 2*j+1));
                }
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
        return boxes.stream().map(Box::gpsCoordinate).mapToInt(x -> x).sum();
    }

    static Position getNextPosition(Position position, Direction direction) {
        return switch (direction) {
            case UP -> new Position(position.i - 1, position.j);
            case DOWN -> new Position(position.i + 1, position.j);
            case LEFT -> new Position(position.i, position.j - 1);
            case RIGHT -> new Position(position.i, position.j + 1);
        };
    }

    static Box findBoxByPosition(Position position) {
        return boxes.stream()
                .filter(box -> box.left.equals(position) || box.right.equals(position))
                .findFirst()
                .orElse(null);
    }

    private static void shift(List<Box> boxesToShift, Direction direction) {
        List<Box> newBoxes = new ArrayList<>();
        for (Box box : boxes) {
            if (boxesToShift.contains(box)) {
                newBoxes.add(new Box(
                        getNextPosition(box.left, direction),
                        getNextPosition(box.right, direction)));
            } else {
                newBoxes.add(box);
            }
        }
        boxes = newBoxes;
    }

    private static List<Box> getBoxesToShiftHorizontal(Box startingBox, Direction direction) {
        List<Box> boxesToShift = new ArrayList<>();
        boxesToShift.add(startingBox);
        Box nextBox;
        do {
            Box lastBox = boxesToShift.getLast();
            Position next = getNextPosition(direction == LEFT ? lastBox.left : lastBox.right, direction);
            if (walls.contains(next))
                return Collections.emptyList();
            nextBox = findBoxByPosition(next);
            if (nextBox != null)
                boxesToShift.add(nextBox);
        } while(nextBox != null);
        return boxesToShift;
    }

    private static List<Box> getBoxesToShiftVertical(Box startingBox, Direction direction) {
        List<Box> boxesToShift = new ArrayList<>();
        boxesToShift.add(startingBox);
        computeVertical(boxesToShift, boxesToShift, direction);
        return boxesToShift;
    }

    private static void computeVertical(List<Box> boxesToShift, List<Box> lastAdded, Direction direction) {

        if (lastAdded.stream()
                .anyMatch(box -> walls.contains(getNextPosition(box.left, direction))
                        || walls.contains(getNextPosition(box.right, direction)))) {
            boxesToShift.clear();
            return;
        }

        List<Box> verticalProximityBoxes = new ArrayList<>();
        lastAdded.forEach(box -> verticalProximityBoxes.addAll(box.getVerticalProximityBoxes(direction)));

        if (verticalProximityBoxes.isEmpty())
            return;

        boxesToShift.addAll(verticalProximityBoxes);
        computeVertical(boxesToShift, verticalProximityBoxes, direction);
    }

    static void updateMap() {
        map.setAll('.');
        walls.forEach(wall -> map.set(wall.i, wall.j, '#'));
        boxes.forEach(box -> {
            map.set(box.left.i, box.left.j, '[');
            map.set(box.right.i, box.right.j, ']');
        });
        map.set(robot.position.i, robot.position.j, '@');
    }
}

