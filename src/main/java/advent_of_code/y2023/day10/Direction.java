package advent_of_code.y2023.day10;

enum Direction {UP, DOWN, LEFT, RIGHT;
    static Direction getOpposite(Direction direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}