package advent_of_code.y2024.day19;


import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LinenLayout1 extends AbstractRunnable {

    public static void main(String[] args) {
        new LinenLayout1().start();
    }

    @Override
    protected String source() {
        return "input.txt";
    }

    List<String> availablePatterns;
    List<String> designs;

    @Override
    protected void init() {
        List<List<String>> blocks = readBlocksOfLines();
        availablePatterns = new ArrayList<>();
        Arrays.stream(blocks.getFirst().getFirst().split(","))
                .forEach(p -> availablePatterns.add(p.trim()));
        designs = blocks.getLast();
    }

    @Override
    protected Object run() {
        return designs.stream().filter(this::isPossible).count();
    }

    HashSet<String> possibles = new HashSet<>();

    private boolean isPossible(String design) {

        if (possibles.contains(design))
            return true;

        List<String> nextPatterns = availablePatterns.stream()
                .filter(design::startsWith)
                .toList();

        for (String pattern : nextPatterns) {

            String toCheck = design.replaceFirst(pattern, "");

            if (Strings.isBlank(toCheck)) {
                possibles.add(design);
                return true;
            }

            if (isPossible(toCheck)) {
                possibles.add(toCheck);
                return true;
            }
        }

        return false;
    }

}
    // Find all possible solution, doesn't work, too slow
//    record Solution(List<String> patterns) {}
//    private List<Solution> search(Solution currentSolution, String design) {
//
//        if (Strings.isBlank(design))
//            return List.of(currentSolution);
//
//        List<String> nextPatterns = availablePatterns.stream()
//                .filter(design::startsWith)
//                .toList();
//
//        if (nextPatterns.isEmpty())
//            return Collections.emptyList();
//
//        List<Solution> solutions = new ArrayList<>();
//        nextPatterns.forEach(pattern -> {
//            ArrayList<String> currentSolutionPatterns = new ArrayList<>(currentSolution.patterns);
//            currentSolutionPatterns.add(pattern);
//            solutions.addAll(search(new Solution(currentSolutionPatterns), design.replaceFirst(pattern, "")));
//        });
//        return solutions;
//    }


