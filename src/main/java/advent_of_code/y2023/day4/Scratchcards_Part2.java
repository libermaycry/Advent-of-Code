package advent_of_code.y2023.day4;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.*;

public class Scratchcards_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Scratchcards_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day4/hands.txt";
    }

    private record Card (int cardNumber, List<Integer> winning, List<Integer> mine) {}

    @Override
    public void run() {

        Map<Integer, Card> originals = new HashMap<>();

        Stack<Card> toProcess = new Stack<>();
        Stack<Card> processed = new Stack<>();

        readLines()
            .map(this::toCard)
            .map(toProcess::push)
            .forEach(card -> originals.put(card.cardNumber, card));

        while (!toProcess.isEmpty()) {

            Card card = toProcess.pop();

            List<Integer> matchingNumbers = card.mine.stream()
                    .filter(card.winning::contains)
                    .toList();

            for (int i = 0; i < matchingNumbers.size(); i++)
                toProcess.push(copy(originals.get(card.cardNumber + 1 + i)));

            processed.push(card);
        }

        System.out.println(processed.size());
    }

    private Card toCard(String line) {

        String left = line.split(" \\| ")[0];
        String right = line.split(" \\| ")[1];

        List<Integer> winning = new ArrayList<>();
        List<Integer> my = new ArrayList<>();

        String leftColon = left.split(":")[0];
        String rightColon = left.split(":")[1];

        int cardNumber = Integer.parseInt(leftColon.replace("Card", "").trim());

        for (String s : rightColon.split(" ")) {
            s = s.trim();
            if(Strings.isBlank(s)) continue;
            winning.add(Integer.parseInt(s));
        }

        for (String s : right.split(" ")) {
            s = s.trim();
            if(Strings.isBlank(s)) continue;
            my.add(Integer.parseInt(s));
        }

        return new Card(cardNumber, winning, my);
    }

    private Card copy(Card toCopy) {
        return new Card(toCopy.cardNumber, toCopy.winning, toCopy.mine);
    }
}

