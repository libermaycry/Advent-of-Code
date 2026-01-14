package advent_of_code.y2023.day6;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class WaitForIt_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new WaitForIt_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day6/races.txt";
    }

    private record Race(int totalTime, int recordDistance) {}

    List<Race> races;

    @Override
    public void run() {

        parseFile();

        int result = races.stream()
                .map(this::waysToBeatRecord)
                .reduce(1, (a, b) -> a * b);

        System.out.println(result);
    }

    private int waysToBeatRecord(Race race) {
        int count = 0;
        for (int wait = 1; wait <= race.totalTime; wait++) {
            int travelTime = race.totalTime - wait;
            int distanceTraveled = wait * travelTime;
            if (distanceTraveled > race.recordDistance) count ++;
        }
        return count;
    }

    private void parseFile() {

        List<Integer> times = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();

        List<String> lines = readLines().toList();

        for(int i = 0; i < lines.size(); i++) {

            String line = lines.get(i).replace("Time:", "")
                    .replace("Distance:", "")
                    .trim();

            for(String split : line.split(" ")) {
                if(Strings.isBlank(split)) continue;
                int n = Integer.parseInt(split);
                if (i == 0) times.add(n);
                else distances.add(n);
            }
        }

        races = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
    }
}

