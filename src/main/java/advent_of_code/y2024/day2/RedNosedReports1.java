package advent_of_code.y2024.day2;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports1 extends AbstractRunnable {

    public static void main(String[] args) {
        new RedNosedReports1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Report (List<Integer> levels) {}

    List<Report> reports;

    @Override
    protected void init() {
        reports = new ArrayList<>();
        readLines().forEach(line -> {
            reports.add(new Report(Arrays
                    .stream(line.split(" "))
                    .map(Integer::valueOf)
                    .toList()));
        });
    }

    @Override
    protected Object run() {
        return reports.stream()
                .filter(this::isSafe)
                .count();
    }

    private boolean isSafe(Report report) {
        List<Integer> levels = report.levels;
        int trend = levels.get(0).compareTo(levels.get(1));
        for (int i = 0; i < report.levels().size() - 1; i++) {
            if (levels.get(i).compareTo(levels.get(i + 1)) != trend)
                return false;
            int diff = Math.abs(levels.get(i) - levels.get(i + 1));
            if (diff < 1 || diff > 3)
                return false;
        }
        return true;
    }
}
