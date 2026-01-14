package advent_of_code.y2023.day12;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotSprings_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new HotSprings_Part1().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day12/records.txt";
    }

    record Record (List<Character> characters, List<Integer> condition) {}

    List<Record> records;

    Map<Integer, List<List<Integer>>> combinationsCache = new HashMap<>();

    @Override
    public void run() {

        parseFile();

        int result = 0;

        for (Record record : records) {

            List<Integer> unkPositions = getUnkPositions(record.characters);

            List<List<Integer>> combinations = getCombinations(unkPositions.size());
            List<List<Integer>> indexesCombinations = toIndexes(combinations);

            for (List<Integer> indexes : indexesCombinations) {
                List<Character> attempt = new ArrayList<>(record.characters);
                for (int index : indexes) {
                    attempt.set(unkPositions.get(index), '#');
                }
                if (toGroupsCondition(attempt).equals(record.condition)) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    List<List<Integer>> toIndexes(List<List<Integer>> combinations) {
        return combinations.stream()
                .map(combination -> {
                    List<Integer> indexes = new ArrayList<>();
                    for (int i = 0; i < combination.size(); i++) {
                        if (combination.get(i) == 1) indexes.add(i);
                    }
                    return indexes;
                }).toList();
    }

    List<List<Integer>> getCombinations(int size) {

        if (combinationsCache.containsKey(size)) {
            return combinationsCache.get(size);
        }

        if (size == 0) return List.of();

        if (size == 1) {
            List<List<Integer>> combinations = List.of(List.of(1), List.of(0));
            combinationsCache.put(1, combinations);
            return combinations;
        }

        List<List<Integer>> previousCombinations = getCombinations(size - 1);

        List<List<Integer>> combinations = new ArrayList<>();

        previousCombinations.forEach(combination -> {

            List<Integer> newCombination = new ArrayList<>(combination);
            newCombination.add(0);
            combinations.add(newCombination);

            newCombination = new ArrayList<>(combination);
            newCombination.add(1);
            combinations.add(newCombination);
        });

        combinationsCache.put(size, combinations);
        return combinations;
    }

    List<Integer> getUnkPositions(List<Character> characters) {
        List<Integer> unkPositions = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i) == '?') unkPositions.add(i);
        }
        return unkPositions;
    }

    List<Integer> toGroupsCondition(List<Character> attempt) {
        List<Integer> arrangement = new ArrayList<>();
        Integer startGroup = null;
        for (int i = 0; i < attempt.size(); i++) {
            if (attempt.get(i) != '#') {
                if (startGroup != null) {
                    arrangement.add(i - startGroup);
                    startGroup = null;
                }
            } else {
                if (startGroup == null) {
                    startGroup = i;
                }
            }
        }
        if (startGroup != null) {
            arrangement.add(attempt.size() - startGroup);
        }
        return arrangement;
    }

    void parseFile() {
        records = new ArrayList<>();
        for (String line : readLines().toList()) {
            String left = line.split(" ")[0];
            String right = line.split(" ")[1];

            List<Character> characters = new ArrayList<>();
            for (char c : left.toCharArray()) characters.add(c);

            List<Integer> condition = new ArrayList<>();

            for (String n : right.split(",")) {
                if(Strings.isBlank(n)) continue;
                condition.add(Integer.parseInt(n.trim()));
            }

            records.add(new Record(characters, condition));
        }
    }
}

