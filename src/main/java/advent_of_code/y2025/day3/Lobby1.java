package advent_of_code.y2025.day3;


import advent_of_code.utils.Pair;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Lobby1().start();
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

    private int getVoltage(List<Integer> values) {

        List<Integer> ordered = new ArrayList<>(values);
        Collections.sort(ordered);
        ordered = ordered.reversed();

        Pair<Integer, Integer> firstValueAndIndex = find(ordered, values, 0, values.size() - 1);
        Pair<Integer, Integer> secondValueAndIndex = find(ordered, values, firstValueAndIndex.right + 1, values.size());

        System.out.println(firstValueAndIndex);
        System.out.println(secondValueAndIndex);
        System.out.println();

        return Integer.parseInt("" + firstValueAndIndex.left + secondValueAndIndex.left);
    }

    private Pair<Integer, Integer> find(List<Integer> ordered, List<Integer> values, int fromInclusive, int toExclusive) {
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
