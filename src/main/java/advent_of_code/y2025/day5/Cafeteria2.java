package advent_of_code.y2025.day5;


import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Cafeteria2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Range (long start, long end) {}

    List<Range> ranges;

    @Override
    protected void init() {
        ranges = new ArrayList<>();
        readBlocksOfLines().getFirst().forEach(line -> {
            String[] split = line.split("-");
            ranges.add(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
        });
        ranges.sort(Comparator.comparing(Range::start));
    }

    @Override
    protected Object run() {
        long result = 0;
        long last = -1;
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            if (i > 0) {
                if (range.start > last) {
                    result += range.end - range.start + 1;
                } else {
                    if (range.end <= last)
                        continue;
                    result += range.end - last;
                }
            } else {
                result += range.end - range.start + 1;
            }
            last = range.end;
        }
        return result;
    }
}
