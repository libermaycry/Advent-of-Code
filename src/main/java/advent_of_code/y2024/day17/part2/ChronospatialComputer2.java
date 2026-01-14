package advent_of_code.y2024.day17.part2;


import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

import static advent_of_code.utils.Binary.xor;
import static advent_of_code.utils.Numbers.pow;

public class ChronospatialComputer2 extends AbstractRunnable {

    public static void main(String[] args) {
        new ChronospatialComputer2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    String output;
    Map<Integer, List<Long>> stepsWithPossibilities = new HashMap<>();

    @Override
    protected void init() {
        output = readBlocksOfLines().getLast().getFirst().split(":")[1].trim();
    }

    @Override
    protected Object run() {

        List<Integer> reversedOutput = Arrays.stream(output.split(","))
                .map(Integer::parseInt)
                .toList()
                .reversed();

        long lastA = 0;

        for (int step = 0; step < reversedOutput.size(); step++) {
            List<Long> possibilities = getPossibilities(lastA, reversedOutput.get(step));
            if (!possibilities.isEmpty()) {
                lastA = possibilities.removeFirst();
                stepsWithPossibilities.put(step, possibilities);
            } else {
                int stepToGoBack = stepsWithPossibilities.keySet()
                        .stream()
                        .filter(s -> !stepsWithPossibilities.get(s).isEmpty())
                        .mapToInt(x -> x)
                        .max()
                        .orElseThrow();
                lastA = stepsWithPossibilities.get(stepToGoBack).removeFirst();
                step = stepToGoBack;
            }
        }

        return lastA;
    }

    private List<Long> getPossibilities(long lastA, int out) {
        List<Long> possibilities = new ArrayList<>();
        for (long maybeA = 8*lastA; maybeA <= 8*lastA + 7; maybeA++) {
            if (getB(maybeA) % 8 == out)
                possibilities.add(maybeA);
        }
        return possibilities;
    }

    private long getB(long A) {
        return xor(
                xor(xor(A % 8, 1), 5),
                (long) Math.floor((double) A / pow(2, xor(A % 8, 1)))
        );
    }
}
