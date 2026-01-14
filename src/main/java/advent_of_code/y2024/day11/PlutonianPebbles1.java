package advent_of_code.y2024.day11;


import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class PlutonianPebbles1 extends AbstractRunnable {

    public static void main(String[] args) {
        new PlutonianPebbles1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Long> stones;

    @Override
    protected void init() {
        String[] split = readLines().toList().getFirst().split(" ");
        stones = Arrays.stream(split).map(Long::valueOf).toList();
    }

    @Override
    protected Object run() {
        for (int i = 0; i < 25; i++) {
            blink();
        }
        return stones.size();
    }

    private void blink() {
        List<Long> newStones = new ArrayList<>();
        stones.forEach(stone -> newStones.addAll(getReplacements(stone)));
        stones = newStones;
    }

    private List<Long> getReplacements(long stone) {

        if (stone == 0) return List.of(1L);

        String stoneString = String.valueOf(stone);

        if (stoneString.length() % 2 == 0) {
            String left = stoneString.substring(0, stoneString.length() / 2);
            String right = stoneString.substring(stoneString.length() / 2);
            return List.of(Long.valueOf(left), Long.valueOf(right));
        }

        return List.of(stone * 2024);
    }

}
