package advent_of_code.y2023.day7;


import advent_of_code.y2023.AbstractRunnable;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;

public class CamelCards_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new CamelCards_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day7/hands.txt";
    }

    enum Card {A(12), K(11), Q(10), T(8), _9(7),
        _8(6), _7(5), _6(4), _5(3), _4(2), _3(1), _2(0), J(-1);
        final int value;
        Card(int value) {
            this.value = value;
        }
    }

    enum HandType {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);
        final int strength;
        HandType(int strength) {
            this.strength = strength;
        }
    }

    record Hand (int id, List<Card> cards, int bid) {}

    List<Hand> hands;

    @Override
    public void run() {

        parseFile();

        List<Hand> orderedDesc = new ArrayList<>();

        while (!hands.isEmpty()) {

            Hand max = hands.stream().filter(
                    h1 -> hands.stream().noneMatch(h2 -> h2.id != h1.id && moreStrength(h2, h1)))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);

            orderedDesc.add(max);
            hands.removeIf(hand -> hand.id == max.id);
        }

        int result = IntStream
                .range(0, orderedDesc.size())
                .map(index -> {
                    int rank = orderedDesc.size() - index;
                    return orderedDesc.get(index).bid * rank;
                }).sum();

        System.out.println(result);
    }

    private boolean moreStrength(Hand first, Hand second) {

        HandType firstType = getType(first);
        HandType secondType = getType(second);

        if (firstType.strength > secondType.strength) {
            return true;
        }

        if (firstType.strength < secondType.strength) {
            return false;
        }

        for (int i = 0; i < 5; i++) {
            Card firstCard = first.cards.get(i);
            Card secondCard = second.cards.get(i);
            if (firstCard == secondCard) continue;
            return firstCard.value > secondCard.value;
        }

        throw new IllegalStateException();
    }

    private HandType getType(Hand hand) {

        Map<Card, Integer> groups = new HashMap<>();

        for (Card card : hand.cards) {
            int count = groups.getOrDefault(card, 0);
            groups.put(card, count + 1);
        }

        if (groups.containsKey(Card.J)) {

            Integer jokerCount = groups.get(Card.J);

            groups.remove(Card.J);

            if (groups.isEmpty())
                return HandType.FIVE_OF_A_KIND;

            int maxGroupCount = groups.values().stream()
                    .max(Integer::compare)
                    .orElseThrow(IllegalStateException::new);

            Card jokerReplace = groups.keySet().stream()
                    .filter(card -> groups.get(card) == maxGroupCount)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            groups.put(jokerReplace, groups.getOrDefault(jokerReplace, 0) + jokerCount);
        }

        if (groups.size() == 1) {
            return HandType.FIVE_OF_A_KIND;
        }

        if (groups.size() == 2) {
            Integer firstValue = groups.values().stream().toList().get(0);
            if (firstValue == 1 || firstValue == 4) {
                return HandType.FOUR_OF_A_KIND;
            }
            return HandType.FULL_HOUSE;
        }

        if (groups.size() == 3) {
            if (groups.values().stream().anyMatch(val -> val == 3)) {
                return HandType.THREE_OF_A_KIND;
            }
            return HandType.TWO_PAIR;
        }

        if (groups.size() == 4) {
            return HandType.ONE_PAIR;
        }

        return HandType.HIGH_CARD;
    }

    private void parseFile() {
        hands = new ArrayList<>();
        int id = 1;
        for(String line : readLines().toList()) {
            List<Card> cards = new ArrayList<>();
            String[] split = line.split(" ");
            for (char c : split[0].toCharArray()) {
                cards.add(Card.valueOf(isDigit(c) ? "_"+c : ""+c));
            }
            hands.add(new Hand(id++, cards, Integer.parseInt(split[1])));
        }
    }
}

