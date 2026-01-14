package advent_of_code.y2025.day11;


import advent_of_code.y2025.AbstractRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reactor1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Reactor1().start();
    }

    Map<String, List<String>> map = new HashMap<>();
    Map<String, Long> cache = new HashMap<>();

    @Override
    protected String source() { return "input.txt"; }

    @Override
    protected void init() {
        readLines().forEach(line -> {
            String[] split = line.split(":");
            String[] outputs = split[1].trim().split(" ");
            map.put(split[0], Arrays.asList(outputs));
        });
    }

    @Override
    protected Object run() {
        return count(from);
    }

    String from = "you";
    String to = "out";

    private long count(String node) {

        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        if (map.get(node) == null) return 0;

        if (map.get(node).contains(to)) {
            return 1;
        }

        long result = 0;
        for (String output : map.get(node)) {
            long count = count(output);
            cache.put(output, count);
            result += count;
        }
        return result;
    }

}
