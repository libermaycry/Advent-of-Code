package advent_of_code.y2024.day1;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class HistorianHysteria1 extends AbstractRunnable {

    public static void main(String[] args) {
        new HistorianHysteria1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    @Override
    protected void init() {
        readLines().forEach(line -> {
            String[] split = line.split(" {3}");
            left.add(Integer.valueOf(split[0]));
            right.add(Integer.valueOf(split[1]));
        });
        Collections.sort(left);
        Collections.sort(right);
    }

    @Override
    public Object run() {
        return IntStream
                .range(0, left.size())
                .mapToObj(i -> Math.abs(left.get(i) - right.get(i)))
                .mapToInt(x -> x)
                .sum();
    }
}
