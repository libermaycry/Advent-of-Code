package advent_of_code.y2022.day2;

import advent_of_code.utils.Files;

import java.util.ArrayList;
import java.util.List;

import static advent_of_code.y2022.day2.RockPaperScissors_Part2.Outcome.*;
import static advent_of_code.y2022.day2.RockPaperScissors_Part2.Shape.*;

public class RockPaperScissors_Part2 {

    public static void main(String[] args) {
        new RockPaperScissors_Part2().run();
    }

    enum Shape {ROCK(1), PAPER(2), SCISSORS(3);
        final int value;
        Shape(int value) {
            this.value = value;
        }
    }

    enum Outcome {LOST(0), WIN(6), DRAW(3);
        final int value;
        Outcome(int value) {
            this.value = value;
        }
    }

    record Round (Shape opponentPlay, Shape responsePlay) {}

    public void run() {

        List<String> lines = Files.readLines("resources/2022/day2/strategy_guide.txt").toList();

        int result = getRounds(lines).stream()
                .map(round -> {
                    Shape opponent = round.opponentPlay;
                    Shape response = round.responsePlay;
                    return response.value + getOutcome(response, opponent).value;
                })
                .reduce(0, Integer::sum);

        System.out.println(result);

    }

    private List<Round> getRounds(List<String> lines) {

        List<Round> rounds = new ArrayList<>();

        for (String line : lines) {

            Shape opponentPlay, responsePlay;

            String[] split = line.split(" ");
            String first = split[0];
            String second = split[1];
            if(first.equals("A")) opponentPlay = ROCK;
            else if(first.equals("B")) opponentPlay = PAPER;
            else opponentPlay = SCISSORS;

            if(second.equals("X")) {
                responsePlay = switch(opponentPlay) {
                    case ROCK -> SCISSORS;
                    case PAPER -> ROCK;
                    case SCISSORS -> PAPER;
                };
            } else if(second.equals("Y")) {
                responsePlay = opponentPlay;
            } else {
                responsePlay = switch(opponentPlay) {
                    case ROCK -> PAPER;
                    case PAPER -> SCISSORS;
                    case SCISSORS -> ROCK;
                };
            }

            rounds.add(new Round(opponentPlay, responsePlay));
        }

        return rounds;
    }

    private Outcome getOutcome(Shape first, Shape against) {
        return switch (first) {
            case ROCK -> switch(against) {
                case ROCK -> DRAW;
                case PAPER -> LOST;
                case SCISSORS -> WIN;
            };
            case PAPER -> switch(against) {
                case ROCK -> WIN;
                case PAPER -> DRAW;
                case SCISSORS -> LOST;
            };
            case SCISSORS -> switch(against) {
                case ROCK -> LOST;
                case PAPER -> WIN;
                case SCISSORS -> DRAW;
            };
        };
    }

}
