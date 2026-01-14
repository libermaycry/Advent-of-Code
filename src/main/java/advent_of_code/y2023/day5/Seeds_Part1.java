package advent_of_code.y2023.day5;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.*;

public class Seeds_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Seeds_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day5/almanac.txt";
    }

    record Range (long sourceStart, long destStart, long length) {}

    record Mapping (List<Range> ranges) {
        public Mapping() {
            this(new ArrayList<>());
        }
    }

    List<Mapping> mappings = new ArrayList<>();

    Map<Long, Long> seedsLocation = new HashMap<>();

    @Override
    public void run() {

        parseFile();

        for (long seed : seedsLocation.keySet()) {
            long value = seed;
            for (Mapping mapping : mappings) {
                value = map(value, mapping);

            }
            seedsLocation.put(seed, value);
        }

        long result = seedsLocation.keySet()
                .stream()
                .min(Long::compare)
                .orElseThrow(IllegalStateException::new);

        System.out.println(result);

    }

    private long map(long value, Mapping mapping) {

        Range rangeForValue = mapping.ranges.stream()
                .filter(range -> value >= range.sourceStart && value <= range.sourceStart + range.length - 1)
                .findFirst()
                .orElse(null);

        if (rangeForValue == null)
            return value;

        return rangeForValue.destStart + (value - rangeForValue.sourceStart);
    }

    private void parseFile() {

        List<String> lines = readLines().toList();

        Mapping mapping = null;

        for (String line : lines) {

            if (Strings.isBlank(line)) continue;

            if (line.contains("seeds:")) {

                line = line.replace("seeds:", "").trim();
                for (String n : line.split(" ")) {
                    seedsLocation.put(Long.parseLong(n.trim()), null);
                }

            } else if (line.contains("map:")) {

                if (mapping != null) {
                    mappings.add(mapping);
                }
                mapping = new Mapping();

            } else {
                String[] splits = line.split(" ");
                long destRangeStart = Long.parseLong(splits[0].trim());
                long sourceRangeStart = Long.parseLong(splits[1].trim());
                long rangeLength = Long.parseLong(splits[2].trim());
                mapping.ranges.add(new Range(sourceRangeStart, destRangeStart, rangeLength));
            }
        }

        if (!mapping.ranges.isEmpty()) {
            mappings.add(mapping);
        }
    }
}

