package advent_of_code.y2022.day6;

import advent_of_code.y2023.AbstractRunnable;

import java.util.*;

public class TuningTrouble_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new TuningTrouble_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day6/signals.txt";
    }

    @Override
    public void run() {

        String line = readLines().toList().get(0);

        for (int i = 0; i < line.toCharArray().length; i++) {

            String fourChars = line.substring(i, i+14);
            
            if (!containsDuplicates(fourChars)) {
                System.out.println(fourChars);
                System.out.println(i+14);
                return;
            }
        }
    }

    private boolean containsDuplicates(String fourChars) {
        List<Character> list = fourChars
                .chars()
                .mapToObj(c -> (char) c)
                .toList();
        Set<Character> set = new HashSet<>(list);
        return set.size() < list.size();
    }

}
