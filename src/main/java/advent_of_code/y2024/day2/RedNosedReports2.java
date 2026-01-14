package advent_of_code.y2024.day2;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports2 extends AbstractRunnable {

    public static void main(String[] args) {
        new RedNosedReports2().start();
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
        if (isStrictSafe(report))
            return true;
        for (int i = 0; i < report.levels.size(); i++) {
            List<Integer> levels = new ArrayList<>(report.levels);
            levels.remove(i);
            if (isStrictSafe(new Report(levels)))
                return true;
        }
        return false;
    }

    private boolean isStrictSafe(Report report) {
        int trend = report.levels.get(0).compareTo(report.levels.get(1));
        for (int i = 0; i < report.levels().size() - 1; i++) {
            if (report.levels.get(i).compareTo(report.levels.get(i + 1)) != trend)
                return false;
            int diff = Math.abs(report.levels.get(i) - report.levels.get(i + 1));
            if (diff < 1 || diff > 3)
                return false;
        }
        return true;
    }
}
