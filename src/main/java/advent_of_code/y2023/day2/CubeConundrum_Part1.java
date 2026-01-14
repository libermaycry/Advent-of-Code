package advent_of_code.y2023.day2;


import advent_of_code.y2023.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CubeConundrum_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new CubeConundrum_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day2/cubes_games.txt";
    }

    enum Colors {BLUE, RED, GREEN;}

    private record Set (Map<Colors, Integer> cubes) {}

    Map <Integer, List<Set>> games = new HashMap<>();

    @Override
    public void run() {

        List<String> lines = readLines().toList();
        for (int i = 0; i < lines.size(); i++ ) {
            games.put(i+1, parseLine(lines.get(i)));
        }

        Integer result = games.keySet()
                .stream()
                .filter(gameId -> isPossible(games.get(gameId)))
                .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private boolean isPossible(List<Set> sets) {
        return sets.stream().allMatch(set ->
            set.cubes.getOrDefault(Colors.RED, 0) <= 12
                    && set.cubes.getOrDefault(Colors.GREEN, 0) <= 13
                    && set.cubes.getOrDefault(Colors.BLUE, 0) <= 14
        );
    }

    private List<Set> parseLine(String line) {

        line = line.substring(line.indexOf(":")+1);

        List<Set> sets = new ArrayList<>();

        for (String set : line.split(";")) {
            Map<Colors, Integer> cubesMap = new HashMap<>();
            String[] cubes = set.split(",");
            for(String cubeWithNumber : cubes) {
                String number = cubeWithNumber.trim().split(" ")[0];
                String color = cubeWithNumber.trim().split(" ")[1];
                cubesMap.put(Colors.valueOf(color.toUpperCase()), Integer.valueOf(number));
            }
            sets.add(new Set(cubesMap));
        }

        return sets;
    }

}

