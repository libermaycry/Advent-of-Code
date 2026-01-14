package advent_of_code.y2025.day3;


import advent_of_code.utils.Pair;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Lobby2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<List<Integer>> banks = new ArrayList<>();

    @Override
    protected void init() {
        readLines().forEach(line -> {
            char[] chars = line.toCharArray();
            List<Integer> values = new ArrayList<>();
            for (char aChar : chars) {
                values.add(Integer.valueOf(String.valueOf(aChar)));
            }
            banks.add(values);
        });
    }

    @Override
    protected Object run() {
        long result = 0;
        for (List<Integer> values : banks) {
            result += getVoltage(values);
        }
        return result;
    }

    private long getVoltage(List<Integer> values) {

        List<Integer> ordered = new ArrayList<>(values);
        Collections.sort(ordered);
        ordered = ordered.reversed();

        StringBuilder voltage = new StringBuilder();
        int size = values.size();
        int lastIndex = -1;
        int wantedSize = 12;

        while (voltage.length() != wantedSize) {
            int fromInclusive = lastIndex + 1;
            int toExclusive = size - (wantedSize - voltage.length()) + 1;
            Pair<Integer, Integer> pair = find(ordered, values, fromInclusive, toExclusive);
            voltage.append(pair.left);
            lastIndex = pair.right;
        }

        return Long.parseLong(voltage.toString());
    }

    private Pair<Integer, Integer> find(List<Integer> ordered,
                                        List<Integer> values,
                                        int fromInclusive,
                                        int toExclusive) {
        for(int o : ordered) {
            for (int i = fromInclusive; i < toExclusive; i++) {
                if (values.get(i).equals(o)) {
                    return Pair.of(values.get(i), i);
                }
            }
        }
        throw new IllegalStateException();
    }
}
