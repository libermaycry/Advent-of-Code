package advent_of_code.y2024.day21;


import advent_of_code.utils.Position;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class KeypadConundrum extends AbstractRunnable {

    protected abstract int getNRobots();

    @Override
    protected String source() { return "input.txt"; }

    List<String> toOpenDoorCodes;

    Map<Character, Integer> priority = Map.of(
            '<', 3,
            'v', 2,
            '^', 1,
            '>', 1
    );

    static final Position A_POS_NUMPAD = new Position(3,2);
    static final Position A_POS_DIRECTIONAL = new Position(0,2);

    static final Position EMPTY_POS_NUMPAD = new Position(3,0);
    static final Position EMPTY_POS_DIRECTIONAL = new Position(0,0);

    Map<String, Long> memory = new HashMap<>();

    @Override
    protected void init() {
        toOpenDoorCodes = readLines().toList();
    }

    @Override
    protected Object run() {

        long result = 0;
        for (String code : toOpenDoorCodes) {
            Position current = A_POS_NUMPAD;
            long cost = 0;
            for (char c : code.toCharArray()) {
                Position target = getPositionForNumpad(c);
                char[] sequence = getSequence(current, target, EMPTY_POS_NUMPAD);
                for (int i = 0; i < sequence.length; i++) {
                    Position from = i == 0 ? A_POS_DIRECTIONAL : getPositionForDirectional(sequence[i-1]);
                    cost += cost(from, getPositionForDirectional(sequence[i]), 1);
                }
                current = target;
            }
            result += numeric(code) * cost;
        }

        return result;
    }

    private Long cost(Position current, Position target, int robot) {

        String stateString = stateString(current, target, robot);
        if (memory.containsKey(stateString)) {
            return memory.get(stateString);
        }

        char[] sequence = getSequence(current, target, EMPTY_POS_DIRECTIONAL);

        long cost;
        if (robot == getNRobots()) {
            cost = sequence.length;
        } else {
            cost = 0;
            for (int i = 0; i < sequence.length; i++) {
                Position from = i == 0 ? A_POS_DIRECTIONAL : getPositionForDirectional(sequence[i-1]);
                cost += cost(from, getPositionForDirectional(sequence[i]), robot + 1);
            }
        }

        memory.put(stateString, cost);
        return cost;
    }

    private String stateString(Position current, Position target, int robot) {
        return Strings.of(robot, current, target);
    }

    private char[] getSequence(Position current, Position target, Position toAvoid) {

        int toMoveVertically = target.i - current.i;
        int toMoveHorizontally = target.j - current.j;
        if (toMoveVertically == 0 && toMoveHorizontally == 0) {
            return "A".toCharArray();
        }

        List<String> movements = getValidMovements(toMoveVertically, toMoveHorizontally).stream()
                .filter(p -> notPassForEmptyButton(p, current, toAvoid))
                .toList();

        String movement;
        if (movements.size() > 1) {
            movement = choose(movements.getFirst(), movements.getLast());
        } else {
            movement = movements.getFirst();
        }

        return movement.concat("A").toCharArray();
    }

    private String choose(String m1, String m2) {
        for (int i = 0; i < m1.length(); i++) {
            int p1 = priority.get(m1.charAt(i));
            int p2 = priority.get(m2.charAt(i));
            if (!Objects.equals(p1, p2)) {
                return p1 > p2 ? m1 : m2;
            }
        }
        return m1;
    }

    private Position getPositionForNumpad(char c) {
        return switch (c) {
            case 'A' -> new Position(3,2);
            case '0' -> new Position(3,1);
            case '1' -> new Position(2, 0);
            case '2' -> new Position(2, 1);
            case '3' -> new Position(2, 2);
            case '4' -> new Position(1, 0);
            case '5' -> new Position(1, 1);
            case '6' -> new Position(1, 2);
            case '7' -> new Position(0, 0);
            case '8' -> new Position(0, 1);
            case '9' -> new Position(0, 2);
            default -> throw new IllegalArgumentException();
        };
    }

    private Position getPositionForDirectional(char c) {
        return switch (c) {
            case '^' -> new Position(0,1);
            case 'A' -> A_POS_DIRECTIONAL;
            case '<' -> new Position(1, 0);
            case 'v' -> new Position(1, 1);
            case '>' -> new Position(1, 2);
            default -> throw new IllegalArgumentException();
        };
    }

    private List<String> getValidMovements(int toMoveVertically, int toMoveHorizontally) {
        String vertically = "";
        if (toMoveVertically > 0) {
            for (int i = 0; i < toMoveVertically; i++) {
                vertically = vertically.concat("v");
            }
        } else if (toMoveVertically < 0) {
            for (int i = 0; i < Math.abs(toMoveVertically); i++) {
                vertically = vertically.concat("^");
            }
        }
        String horizontally = "";
        if (toMoveHorizontally > 0) {
            for (int i = 0; i < toMoveHorizontally; i++) {
                horizontally = horizontally.concat(">");
            }
        } else if (toMoveHorizontally < 0) {
            for (int i = 0; i < Math.abs(toMoveHorizontally); i++) {
                horizontally = horizontally.concat("<");
            }
        }

        if (Strings.isBlank(vertically)) {
            return List.of(horizontally);
        }

        if (Strings.isBlank(horizontally)) {
            return List.of(vertically);
        }

        return List.of(
                vertically.concat(horizontally),
                horizontally.concat(vertically));
    }

    private boolean notPassForEmptyButton(String movement, Position start, Position toAvoid) {
        Position current = new Position(start.i, start.j);
        for (char c : movement.toCharArray()) {
            switch (c) {
                case '^': current.i --; break;
                case 'v': current.i ++; break;
                case '<': current.j --; break;
                case '>': current.j ++; break;
                default: throw new IllegalArgumentException();
            }
            if (toAvoid.equals(current))
                return false;
        }
        return true;
    }

    private int numeric(String code) {
        StringBuilder number = new StringBuilder();
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            }
        }
        return Integer.parseInt(number.toString());
    }

}
