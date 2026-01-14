package advent_of_code.y2025.day9;


import advent_of_code.utils.Position;
import advent_of_code.utils.raycasting.RayCasting;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieTheater2 extends AbstractRunnable {

    public static void main(String[] args) {
        new MovieTheater2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Position> positions;
    RayCasting raycasting;

    @Override
    protected void init() {
        positions = new ArrayList<>();
        readLines().forEach(line -> {
            String[] split = line.split(",");
            positions.add(new Position(Integer.parseInt(split[1]), Integer.parseInt(split[0])));
        });
        raycasting = new RayCasting(positions);
    }

    @Override
    protected Object run() {
        long maxArea = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Position p1 = positions.get(i);
                Position p2 = positions.get(j);
                if (validRectangle(p1,p2)) {
                    long base = Math.abs(p1.i - p2.i) + 1;
                    long height = Math.abs(p1.j - p2.j) + 1;
                    maxArea = Math.max(base * height, maxArea);
                }
            }
        }
        return maxArea;
    }

    private boolean validRectangle(Position p1, Position p2) {
        Position p3 = new Position(p1.i, p2.j);
        Position p4 = new Position(p2.i, p1.j);
        if (raycasting.isNotInternal(p1)
                || raycasting.isNotInternal(p2)
                || raycasting.isNotInternal(p3)
                || raycasting.isNotInternal(p4)) {
            return false;
        }
        for (int i = 0; i < 10000; i++) {
            int fromI = Math.min(p1.i, p2.i);
            int toI = Math.max(p1.i, p2.i);
            int fromJ = Math.min(p1.j, p2.j);
            int toJ = Math.max(p1.j, p2.j);
            Position randomPoint = new Position(
                    fromI == toI ? fromI : new Random().nextInt(fromI, toI),
                    fromJ == toJ ? fromJ : new Random().nextInt(fromJ, toJ));
            if (raycasting.isNotInternal(randomPoint)) {
                return false;
            }
        }
        return true;
    }
}
