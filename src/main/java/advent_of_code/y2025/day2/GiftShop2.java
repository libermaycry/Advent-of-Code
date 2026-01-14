package advent_of_code.y2025.day2;


import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftShop2 extends AbstractRunnable {

    public static void main(String[] args) {
        new GiftShop2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Range(long start, long end) {}

    List<Range> ranges;
    Map<Long, Boolean> cache = new HashMap<>();

    @Override
    protected void init() {
        ranges = new ArrayList<>();
        for (String splitByComma : readLines().findFirst().orElseThrow().split(",")) {
            String[] splitByDash = splitByComma.split("-");
            ranges.add(new Range(Long.parseLong(splitByDash[0]), Long.parseLong(splitByDash[1])));
        }
    }

    @Override
    protected Object run() {
        long result = 0;
        for (Range range : ranges) {
            for (long n = range.start; n <= range.end; n++) {
                if (!isValid(n)) {
                    result += n;
                }
            }
        }
        return result;
    }

    private boolean isValid(long value) {
        if (cache.containsKey(value)) {
            return cache.get(value);
        }
        String valueString = String.valueOf(value);
        for (int i = 1; i < valueString.length() / 2 + 1; i++) {
            String sub = valueString.substring(0, i);
            if (valueString.replaceAll(sub, "").isEmpty()) {
                cache.put(value, false);
                return false;
            }
        }
        cache.put(value, true);
        return true;
    }

}
