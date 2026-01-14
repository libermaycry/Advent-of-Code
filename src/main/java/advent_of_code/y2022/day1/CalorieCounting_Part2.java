package advent_of_code.y2022.day1;

import advent_of_code.utils.Files;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CalorieCounting_Part2 {

    public static void main(String[] args) {

        List<String> lines = Files.readLines("resources/2022/day1/elfs_calories.txt").toList();

        Map<Integer, Integer> allCalories = new HashMap<>();
        List<Integer> caloriesByElf = new ArrayList<>();

        for (String line : lines) {
            if(Strings.isBlank(line)) {
                allCalories.put(allCalories.size(), caloriesByElf.stream().reduce(0, Integer::sum));
                caloriesByElf.clear();
            } else {
                caloriesByElf.add(Integer.valueOf(line));
            }
        }

        allCalories.put(allCalories.size(), caloriesByElf.stream().reduce(0, Integer::sum));

        int sumTop = 0;

        for (int i = 0; i < 3; i++) {
            AtomicInteger topElfSum = new AtomicInteger(0);
            AtomicInteger topElf = new AtomicInteger(-1);
            allCalories.forEach((elf, elfSum) -> {
                if (elfSum > topElfSum.get()) {
                    topElfSum.set(elfSum);
                    topElf.set(elf);
                }
            });
            sumTop += topElfSum.get();
            allCalories.remove(topElf.get());
        }

        System.out.println(sumTop);

    }

}
