package advent_of_code.utils;

public class Pair<L, R> {

    public L left;
    public R right;

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    @Override
    public String toString() {
        return "[%s,%s]".formatted(left,right);
    }
}
