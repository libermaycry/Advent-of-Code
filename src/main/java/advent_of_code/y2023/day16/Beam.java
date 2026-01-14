package advent_of_code.y2023.day16;

class Beam {
    Position position;
    Direction direction;

    public Beam(Position position, Direction direction) {
        this.position = new Position(position.i, position.j);
        this.direction = direction;
    }
}
