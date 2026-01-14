package advent_of_code.y2023.day6;


import advent_of_code.y2023.AbstractRunnable;

import java.util.List;

public class WaitForIt_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new WaitForIt_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day6/races.txt";
    }

    private record Race(long totalTime, long recordDistance) {}

    Race race;

    @Override
    public void run() {
        parseFile();
        System.out.println(waysToBeatRecord(race));
    }

    private long waysToBeatRecord(Race race) {
        long count = 0;
        for (long wait = 1; wait <= race.totalTime; wait++) {
            long travelTime = race.totalTime - wait;
            long distanceTraveled = wait * travelTime;
            if (distanceTraveled > race.recordDistance) count ++;
        }
        return count;
    }

    private void parseFile() {

        Long time = null;
        Long distance = null;

        List<String> lines = readLines().toList();

        for(int i = 0; i < lines.size(); i++) {

            String line = lines.get(i).replace("Time:", "")
                    .replace("Distance:", "")
                    .trim();

            while(line.contains(" ")) line = line.replace(" ", "");

            long n = Long.parseLong(line);
            if (i == 0) time = n;
            else distance = n;
        }

        race = new Race(time, distance);
    }
}

