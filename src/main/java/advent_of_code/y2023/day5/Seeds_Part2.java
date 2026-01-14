package advent_of_code.y2023.day5;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class Seeds_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Seeds_Part2().run();
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

    List<Range> seeds = new ArrayList<>();

    @Override
    public void run() {

        parseFile();

        long result = Long.MAX_VALUE;

        for (Range seedRange: seeds) {
            System.out.println("seedRange " + seedRange);
            for (long seed = seedRange.sourceStart; seed <= seedRange.destStart; seed++) {
                long value = seed;
                for (Mapping mapping : mappings) {
                    value = map(value, mapping);
                }
                result = Math.min(result, value);
            }
        }

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

                String[] split = line.replace("seeds:", "").trim().split(" ");

                for (int i = 0; i < split.length; i+=2) {
                    long start = Long.parseLong(split[i].trim());
                    long end = Long.parseLong(split[i+1].trim()) + start;
                    seeds.add(new Range(start, end, end-start));
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

