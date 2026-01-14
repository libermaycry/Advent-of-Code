package advent_of_code.y2024.day19;


import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LinenLayout2 extends AbstractRunnable {

    public static void main(String[] args) {
        new LinenLayout2().start();
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
        return designs.stream().map(this::count).mapToLong(x -> x).sum();
    }

    HashMap<String, Long> designsWithCount = new HashMap<>();

    private long count(String design) {

        if (designsWithCount.containsKey(design))
            return designsWithCount.get(design);

        if (Strings.isBlank(design))
            return 1L;

        List<String> nextPatterns = availablePatterns.stream()
                .filter(design::startsWith)
                .toList();

        long count = 0;
        for (String pattern : nextPatterns) {
            count += count(design.replaceFirst(pattern, ""));
        }
        designsWithCount.put(design, count);
        return count;
    }
}


