package advent_of_code.y2023.day9;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.*;

public class MirageMaintenance_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new MirageMaintenance_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day9/oasis_report.txt";
    }

    List<List<Integer>> histories;

    @Override
    public void run() {

        parseFile();

        int result = histories.stream()
                .map(this::predict)
                .reduce(Integer::sum)
                .orElse(0);

        System.out.println(result);
    }

    private int predict(List<Integer> history) {

        List<List<Integer>> differences = new ArrayList<>();
        differences.add(history);

        while (!differences.get(differences.size() - 1).stream().allMatch(n -> n == 0)) {

            List<Integer> last = differences.get(differences.size() - 1);
            List<Integer> newLine = new ArrayList<>();

            for (int i = 1; i < last.size(); i++) {
                newLine.add(last.get(i) - last.get(i-1));
            }

            differences.add(newLine);
        }

        differences.get(differences.size() - 1).add(0);

        for (int i = differences.size() - 2; i >= 0; i--) {
            List<Integer> thisLine = differences.get(i);
            int lastOnThisLine = thisLine.get(thisLine.size() - 1);
            List<Integer> lineBelow = differences.get(i+1);
            int x = lastOnThisLine + lineBelow.get(lineBelow.size() - 1);
            thisLine.add(x);
        }

        List<Integer> firstLine = differences.get(0);
        return firstLine.get(firstLine.size() - 1);
    }

    void parseFile() {
        histories = new ArrayList<>();
        for(String line : readLines().toList()) {
            List<Integer> history = new ArrayList<>();
            String[] split = line.split(" ");
            for(String s : split) {
                if(Strings.isBlank(s)) continue;
                history.add(Integer.parseInt(s.trim()));
            }
            histories.add(history);
        }
    }
}

