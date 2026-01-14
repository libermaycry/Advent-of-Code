package advent_of_code.y2025.day5;


import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class Cafeteria1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Cafeteria1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Range (long start, long end) {}

    List<Range> ranges = new ArrayList<>();
    List<Long> ingredients = new ArrayList<>();

    @Override
    protected void init() {
        List<List<String>> blocks = readBlocksOfLines();
        blocks.getFirst().forEach(line -> {
            String[] split = line.split("-");
            ranges.add(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
        });
        blocks.getLast().forEach(line -> ingredients.add(Long.parseLong(line)));
    }

    @Override
    protected Object run() {
        return ingredients
                .stream()
                .filter(this::isFresh)
                .count();
    }

    private boolean isFresh(long ingredient) {
        for (Range range : ranges) {
            if (range.start <= ingredient && ingredient <= range.end) {
                return true;
            }
        }
        return false;
    }

}
