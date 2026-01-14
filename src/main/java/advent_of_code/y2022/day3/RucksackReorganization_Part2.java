package advent_of_code.y2022.day3;

import advent_of_code.y2023.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class RucksackReorganization_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new RucksackReorganization_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day3/rucksacks.txt";
    }

    public void run() {

        List<String> lines = readLines().toList();

        List<Character> errors = new ArrayList<>();

        List<String> group = new ArrayList<>();

        for (int i = 1; i <= lines.size(); i++) {
            group.add(lines.get(i-1));
            if(i % 3 == 0) {
                errors.add(findError(group));
                group.clear();
            }
        }

        int result = errors.stream()
                        .map(this::getPriority)
                        .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private Character findError(List<String> group) {

        List<Character> first = group.get(0)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();
        List<Character> second = group.get(1)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();
        List<Character> third = group.get(2)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();

        List<Character> potentials = first.stream()
                .filter(item1 -> second.stream().anyMatch(item2 -> item2.equals(item1)))
                .toList();

        Character found = third.stream()
                .filter(item1 -> potentials.stream().anyMatch(item2 -> item2.equals(item1)))
                .findFirst()
                .orElse(null);

        return found;

    }

    private int getPriority(Character item) {
        int ascii = (int) item;
        if (ascii >= 97) {
            return ascii - 96;
        }
        return ascii - 64 + 26;
    }



}
