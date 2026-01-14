package advent_of_code.y2022.day1;

import advent_of_code.utils.Files;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class CalorieCounting_Part1 {

    public static void main(String[] args) {

        List<String> lines = Files.readLines("resources/2022/day1/elfs_calories.txt").toList();

        List<List<Integer>> allCalories = new ArrayList<>();
        List<Integer> caloriesByElf = new ArrayList<>();

        for (String line : lines) {
            if(Strings.isBlank(line)) {
                allCalories.add(new ArrayList<>(caloriesByElf));
                caloriesByElf.clear();
            } else {
                caloriesByElf.add(Integer.valueOf(line));
            }
        }

        allCalories.add(new ArrayList<>(caloriesByElf));


        Integer maxCalories = allCalories.stream()
                .map(calories -> {
                    return calories.stream().reduce(0, Integer::sum);
                })
                .max(Integer::compare)
                .orElse(0);

        System.out.println(maxCalories);

    }

}
