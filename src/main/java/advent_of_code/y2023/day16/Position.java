package advent_of_code.y2023.day16;

import java.util.Objects;

class Position {
    int i;
    int j;

    Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        return this.i == p.i && this.j == p.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
