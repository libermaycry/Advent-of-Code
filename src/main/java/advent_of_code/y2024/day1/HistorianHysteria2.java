package advent_of_code.y2024.day1;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class HistorianHysteria2 extends AbstractRunnable {

    public static void main(String[] args) {
        new HistorianHysteria2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    @Override
    protected void init() {
        readLines().forEach(line -> {
            String[] split = line.split("   ");
            left.add(Integer.valueOf(split[0]));
            right.add(Integer.valueOf(split[1]));
        });
    }

    @Override
    public Object run() {
        return left.stream()
                .map(this::getSimilarityScore)
                .mapToInt(x -> x)
                .sum();
    }

    private int getSimilarityScore(int leftN) {
        return (int) (leftN * right.stream()
                        .filter(rightN -> rightN.equals(leftN))
                        .count());
    }

}
