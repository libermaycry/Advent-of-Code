package advent_of_code.y2025.day9;


import advent_of_code.utils.Position;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class MovieTheater1 extends AbstractRunnable {

    public static void main(String[] args) {
        new MovieTheater1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Position> positions;

    @Override
    protected void init() {
        positions = new ArrayList<>();
        readLines().forEach(line -> {
            String[] split = line.split(",");
            positions.add(new Position(Integer.parseInt(split[1]), Integer.parseInt(split[0])));
        });
    }

    @Override
    protected Object run() {
        long maxArea = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Position p1 = positions.get(i);
                Position p2 = positions.get(j);
                long base = Math.abs(p1.i - p2.i) + 1;
                long height = Math.abs(p1.j - p2.j) + 1;
                long area = base * height;
                maxArea = Math.max(area, maxArea);
            }
        }
        return maxArea;
    }
}
