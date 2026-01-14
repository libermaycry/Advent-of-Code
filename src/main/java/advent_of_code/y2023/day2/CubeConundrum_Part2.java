package advent_of_code.y2023.day2;


import advent_of_code.y2023.AbstractRunnable;

import java.util.*;

public class CubeConundrum_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new CubeConundrum_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day2/cubes_games.txt";
    }

    enum Colors {BLUE, RED, GREEN;}

    record Set (Map<Colors, Integer> cubes) {}

    Map <Integer, List<Set>> games = new HashMap<>();

    @Override
    public void run() {

        List<String> lines = readLines().toList();
        for (int i = 0; i < lines.size(); i++ ) {
            games.put(i+1, parseLine(lines.get(i)));
        }

        int result = games.keySet()
                .stream()
                .map(gameId -> getMinSetOfCubes(games.get(gameId)))
                .map(set -> set.cubes.getOrDefault(Colors.BLUE, 0)
                            * set.cubes.getOrDefault(Colors.GREEN, 0)
                            * set.cubes.getOrDefault(Colors.RED, 0)
                )
                .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private Set getMinSetOfCubes(List<Set> sets) {
        Map<Colors, Integer> minCubes = new HashMap<>();
        for (Colors color : Colors.values()) {
            int maxForColor = sets.stream()
                    .map(set -> set.cubes.getOrDefault(color, 0))
                    .max(Integer::compare)
                    .orElse(0);
            minCubes.put(color, maxForColor);
        }
        return new Set(minCubes);
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

