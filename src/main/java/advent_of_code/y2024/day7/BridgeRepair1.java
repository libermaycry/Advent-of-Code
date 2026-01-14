package advent_of_code.y2024.day7;


import advent_of_code.utils.Permutations;
import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class BridgeRepair1 extends AbstractRunnable {

    public static void main(String[] args) {
        new BridgeRepair1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Equation (long result, List<Long> values) {}

    List<Equation> equations;
    Map<Integer, List<List<String>>> permutationsCache;

    @Override
    protected void init() {
        equations = new ArrayList<>();
        readLines().forEach(line -> {
            String[] splitColon = line.split(":");
            equations.add(new Equation(Long.parseLong(splitColon[0]),
                    Arrays.stream(splitColon[1].trim().split(" "))
                    .map(Long::parseLong)
                    .toList()));
        });
        permutationsCache = new HashMap<>();
    }

    @Override
    protected Object run() {
        return equations.stream()
                .filter(this::isSolvable)
                .mapToLong(eq -> eq.result)
                .sum();
    }

    private boolean isSolvable(Equation equation) {
        List<List<String>> permutations = permutationsCache.computeIfAbsent(equation.values.size() - 1,
                key -> Permutations.of(List.of("+", "x"), key));
        return permutations.stream()
                .anyMatch(permutation -> validPermutation(permutation, equation));
    }

    private boolean validPermutation(List<String> permutation, Equation equation) {
        long result = equation.values.getFirst();
        for (int i = 0; i < equation.values.size() - 1; i++) {
            result = applyOperator(result, equation.values.get(i+1), permutation.get(i));
            if (result > equation.result) return false;
        }
        return result == equation.result;
    }

    private long applyOperator(long n1, long n2, String operator) {
        return switch (operator) {
            case "+" -> n1 + n2;
            case "x" -> n1 * n2;
            default -> throw new IllegalArgumentException();
        };
    }
}
