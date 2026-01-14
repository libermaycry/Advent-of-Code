package advent_of_code.y2023.day4;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class Scratchcards_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Scratchcards_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day4/hands.txt";
    }

    private record Card (List<Integer> winning, List<Integer> my) {}

    @Override
    public void run() {

        int result = readLines()
                .map(this::toCard)
                .map(this::getMyWinningNumbers)
                .map(this::getScore)
                .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private Card toCard(String line) {

        String left = line.split(" \\| ")[0];
        String right = line.split(" \\| ")[1];

        List<Integer> winning = new ArrayList<>();
        List<Integer> my = new ArrayList<>();

        for (String s : left.split(":")[1].split(" ")) {
            s = s.trim();
            if(Strings.isBlank(s)) continue;
            winning.add(Integer.parseInt(s));
        }

        for (String s : right.split(" ")) {
            s = s.trim();
            if(Strings.isBlank(s)) continue;
            my.add(Integer.parseInt(s));
        }

        return new Card(winning, my);
    }

    private List<Integer> getMyWinningNumbers(Card card) {
        return card.my.stream()
                .filter(card.winning::contains)
                .toList();
    }

    private int getScore(List<Integer> myWinningNumbers) {
        if (myWinningNumbers.isEmpty()) {
            return 0;
        }
        return (int) Math.pow(2, myWinningNumbers.size() - 1);
    }
}

