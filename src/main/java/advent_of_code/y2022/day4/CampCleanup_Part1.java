package advent_of_code.y2022.day4;

import advent_of_code.y2023.AbstractRunnable;

public class CampCleanup_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new CampCleanup_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day4/sections.txt";
    }

    record Range(int fromSection, int toSection) {}

    record PairRange(Range firstRange, Range secondRange) {}

    @Override
    public void run() {

        int result = (int) readLines()
                        .map(this::toPairRange)
                        .filter(this::overlaps)
                        .count();

        System.out.println(result);
    }

    private boolean overlaps(PairRange pairRange) {
        return (pairRange.firstRange.fromSection >= pairRange.secondRange.fromSection
                && pairRange.firstRange.toSection <= pairRange.secondRange.toSection)
            ||
        (pairRange.secondRange.fromSection >= pairRange.firstRange.fromSection
                && pairRange.secondRange.toSection <= pairRange.firstRange.toSection);
    }

    private PairRange toPairRange(String line) {

        String[] split = line.split(",");
        String first = split[0];
        String second = split[1];

        int from = Integer.parseInt(first.split("-")[0]);
        int to = Integer.parseInt(first.split("-")[1]);

        Range firstRange = new Range(from, to);

        from = Integer.parseInt(second.split("-")[0]);
        to = Integer.parseInt(second.split("-")[1]);

        Range secondRange = new Range(from, to);

        return new PairRange(firstRange, secondRange);
    }

}
