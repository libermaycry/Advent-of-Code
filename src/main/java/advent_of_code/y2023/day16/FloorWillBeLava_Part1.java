package advent_of_code.y2023.day16;

import advent_of_code.y2023.AbstractRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static advent_of_code.y2023.day16.Direction.*;

public class FloorWillBeLava_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new FloorWillBeLava_Part1().start();
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

        Position startPos = new Position(0, 0);
        Direction startDirection = DOWN;

        beams.add(new Beam(startPos, startDirection));
        energized.put(new Position(startPos.i, startPos.j), 1);

        while(!beams.isEmpty()) {
            move(beams.pop());
        }

        System.out.println(energized.keySet().size());
    }

    void move (Beam beam) {

        Position target = getTarget(beam);
        
        if (target == null) return;
        
        char targetTile = getTile(target);

        if (energized.containsKey(target) && List.of('-', '|').contains(targetTile)) {
            return;
        }

        if (targetTile == '.'
                || (targetTile == '-' && List.of(LEFT, RIGHT).contains(beam.direction))
                || (targetTile == '|' && List.of(UP, DOWN).contains(beam.direction))) {
            update(beam, target);
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

    void update(Beam beam, Position target) {
        beam.position.i = target.i;
        beam.position.j = target.j;
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
        Position target = switch (beam.direction) {
            case UP -> new Position(beam.position.i - 1, beam.position.j);
            case LEFT -> new Position(beam.position.i, beam.position.j - 1);
            case RIGHT -> new Position(beam.position.i, beam.position.j + 1);
            case DOWN -> new Position(beam.position.i + 1, beam.position.j);
        };
        if (isOut(target)) {
            target = null;
        }
        return target;
    }

    boolean isOut(Position position) {
        return position.i < 0 || position.i > tiles.length - 1
                || position.j < 0 || position.j > tiles[0].length - 1;
    }

    boolean isInternal(Position position) {
        return !isOut(position);
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
