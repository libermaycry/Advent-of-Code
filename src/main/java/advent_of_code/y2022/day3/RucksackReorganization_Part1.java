package advent_of_code.y2022.day3;

import advent_of_code.y2023.AbstractRunnable;

import java.util.List;

public class RucksackReorganization_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new RucksackReorganization_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day3/rucksacks.txt";
    }

    public void run() {

        int result = readLines()
                        .map(this::findError)
                        .map(this::getPriority)
                        .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private Character findError(String rucksack) {

        List<Character> first = rucksack.substring(0, rucksack.length() / 2)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();

        List<Character> second = rucksack.substring(rucksack.length()/2)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();

        return first.stream()
                .filter(item1 -> second.stream().anyMatch(item2 -> item2.equals(item1)))
                .findFirst()
                .orElse(null);
    }

    private int getPriority(Character item) {
        int ascii = (int) item;
        if (ascii >= 97) {
            return ascii - 96;
        }
        return ascii - 64 + 26;
    }



}
