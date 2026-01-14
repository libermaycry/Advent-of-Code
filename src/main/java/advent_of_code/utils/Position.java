package advent_of_code.utils;

import java.util.Objects;

public class Position {

    public int i,j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        Position other = (Position) o;
        return other.i == this.i && other.j == this.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "[%s,%s]".formatted(i,j);
    }
}