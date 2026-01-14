package advent_of_code.y2023.day16;

import advent_of_code.y2023.AbstractRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static advent_of_code.y2023.day16.Direction.*;

public class FloorWillBeLava_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new FloorWillBeLava_Part2().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day16/layout.txt";
    }

    Stack<Beam> beams = new Stack<>();

    Map<Position, Integer> energized = new HashMap<>();

    char[][] tiles;

    @Override
    public void run() {

        tiles = readLinesToMatrix();

        int max = 0;

        for (int j = 0; j < tiles[0].length; j++) //top row towards down
            max = Math.max(max, process(new Position(0, j), DOWN));

        for (int j = 0; j < tiles[0].length; j++) //last row towards up
            max = Math.max(max, process(new Position(tiles.length - 1, j), UP));

        for (int i = 1; i < tiles.length - 1; i++) //from left to right
            max = Math.max(max, process(new Position(i, 0), RIGHT));

        for (int i = 1; i < tiles.length - 1; i++) //from right to left
            max = Math.max(max, process(new Position(i, tiles[0].length - 1), LEFT));

        System.out.println(max);
    }

    int process(Position startPos, Direction startDirection) {

        beams.clear();
        energized.clear();

        beams.add(new Beam(startPos, startDirection));
        energized.put(new Position(startPos.i, startPos.j), 1);

        while(!beams.isEmpty()) {
            move(beams.pop());
        }

        return energized.keySet().size();
    }

    void move (Beam beam) {

        Position target = getTarget(beam);

        if (isOut(target)) return;

        char targetTile = getTile(target);

        if (energized.containsKey(target) && List.of('-', '|').contains(targetTile)) {
            return;
        }

        if (targetTile == '.'
                || (targetTile == '-' && List.of(LEFT, RIGHT).contains(beam.direction))
                || (targetTile == '|' && List.of(UP, DOWN).contains(beam.direction))) {
            beam.position = new Position(target.i, target.j);
            putEnergized(target);
            beams.push(beam);
            return;
        }

        putEnergized(target);

        if (targetTile == '|') {
            beams.add(new Beam(target, UP));
            beams.add(new Beam(target, DOWN));
        } else if (targetTile == '-') {
            beams.add(new Beam(target, LEFT));
            beams.add(new Beam(target, RIGHT));
        } else if (targetTile == '/') {
            switch (beam.direction) {
                case RIGHT -> beams.add(new Beam(target, UP));
                case LEFT -> beams.add(new Beam(target, DOWN));
                case DOWN -> beams.add(new Beam(target, LEFT));
                case UP -> beams.add(new Beam(target, RIGHT));
            }
        } else {
            switch (beam.direction) {
                case RIGHT -> beams.add(new Beam(target, DOWN));
                case LEFT -> beams.add(new Beam(target, UP));
                case DOWN -> beams.add(new Beam(target, RIGHT));
                case UP -> beams.add(new Beam(target, LEFT));
            }
        }
    }

    void putEnergized(Position position) {
        if (energized.containsKey(position)) {
            if (getTile(position) == '.') {
                energized.put(position, 1 + energized.getOrDefault(position, 0));
            }
        } else {
            energized.put(position, 1);
        }
    }

    Position getTarget(Beam beam) {
        return switch (beam.direction) {
            case UP -> new Position(beam.position.i - 1, beam.position.j);
            case LEFT -> new Position(beam.position.i, beam.position.j - 1);
            case RIGHT -> new Position(beam.position.i, beam.position.j + 1);
            case DOWN -> new Position(beam.position.i + 1, beam.position.j);
        };
    }

    boolean isOut(Position position) {
        return position.i < 0 || position.i > tiles.length - 1
                || position.j < 0 || position.j > tiles[0].length - 1;
    }

    char getTile(Position position) {
        return tiles[position.i][position.j];
    }

    void print() {
        System.out.println();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int count = energized.getOrDefault(new Position(i, j), 0);
                if (count == 0) {
                    System.out.print(""+tiles[i][j]);
                } else if (count == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(count);
                }
            }
            System.out.println();
        }
    }

}
