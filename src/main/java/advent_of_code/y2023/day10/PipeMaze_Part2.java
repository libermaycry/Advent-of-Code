package advent_of_code.y2023.day10;


import advent_of_code.y2023.AbstractRunnable;

import java.util.*;

public class PipeMaze_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new PipeMaze_Part2().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day10/pipes.txt";
    }

    private Direction getInitialDirection(Position position) {
        return Direction.LEFT;
    }
    private Pipe getStartReplacement() {
        return Pipe.J;
    }

    record Position (int i, int j) {}

    class Line {Position start; Position end; int intersectionCount;}

    List<Position> loop;

    Pipe[][] pipes;

    Map<Integer, List<Line>> linesCache = new HashMap<>();

    int result = 0;

    @Override
    public void run() {
        parseFile();
        findLoop();
        pipes[loop.get(0).i][loop.get(0).j] = getStartReplacement();

        result = 0;

        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                int I = i;
                int J = j;
                if (loop.stream().noneMatch(pos -> pos.i == I && pos.j == J)) {
                    if (isEnclosed(new Position(i,j))) {
                        result ++;
                    }
                }
            }
        }

        System.out.println(result);
    }

    boolean isEnclosed(Position position) {

        List<Line> lines = getLines(position.i);

        int count = 0;

        for (int j = position.j + 1; j < pipes[0].length; j++) {
            int J = j;
            Line lineThrough = lines.stream()
                    .filter(line -> J >= line.start.j && J <= line.end.j)
                    .findFirst()
                    .orElse(null);
            if (lineThrough == null) {
                if (loop.stream().anyMatch(pos -> pos.i == position.i && pos.j == J)) {
                    count ++;
                }
            } else {
                count += lineThrough.intersectionCount;
                j = lineThrough.end.j;
            }
        }

        return count % 2 != 0;
    }

    private List<Line> getLines(int row) {

        if (linesCache.containsKey(row)) {
            return linesCache.get(row);
        }

        List<Line> lines = new ArrayList<>();
        Line currentLine = null;
        for (int j = 0; j < pipes[0].length; j++) {

            int J = j;
            Optional<Position> posInLoop = loop.stream()
                    .filter(pos -> pos.i == row && pos.j == J).findFirst();

            if (posInLoop.isEmpty() || Pipe.VER == pipes[posInLoop.get().i][posInLoop.get().j]) {
                if (currentLine != null) {
                    currentLine.end = new Position(row, j-1);
                    lines.add(currentLine);
                    currentLine = null;
                }
            } else if (List.of(Pipe.F, Pipe._7, Pipe.J, Pipe.L).contains(pipes[posInLoop.get().i][posInLoop.get().j])) {
                if (currentLine == null) {
                    currentLine = new Line();
                    currentLine.start = new Position(row, j);
                } else {
                    currentLine.end = new Position(row, j);
                    lines.add(currentLine);
                    currentLine = null;
                }
            } else {
                if (currentLine == null) {
                    currentLine = new Line();
                    currentLine.start = new Position(row, j);
                }
            }
        }

        if (currentLine != null) {
            currentLine.end = new Position(row, pipes[0].length - 1);
            lines.add(currentLine);
        }

        int rowStart, rowEnd;

        for (Line line : lines) {

            Position elementUpStart = loop.stream()
                    .filter(pos -> pos.i == row - 1 && pos.j == line.start.j)
                    .findFirst()
                    .orElse(null);

            rowStart = elementUpStart == null || notContiguous(line.start, elementUpStart)
                    ? row + 1 : row - 1;

            Position elementUpEnd = loop.stream()
                    .filter(pos -> pos.i == row - 1 && pos.j == line.end.j)
                    .findFirst()
                    .orElse(null);

            rowEnd = elementUpEnd == null || notContiguous(line.end, elementUpEnd)
                    ? row + 1 : row - 1;

            line.intersectionCount = rowStart != rowEnd ? 1 : 0;
        }

        linesCache.put(row, lines);
        return lines;
    }

    private boolean notContiguous(Position p1, Position p2) {
        return !contiguous(p1, p2);
    }

    private boolean contiguous(Position p1, Position p2) {
        int index1 = -1, index2 = -1;
        for (int i = 0; i < loop.size(); i++) {
            Position p = loop.get(i);
            if (p.i == p1.i && p.j == p1.j) {
                index1 = i;
                break;
            }
        }
        for (int i = 0; i < loop.size(); i++) {
            Position p = loop.get(i);
            if (p.i == p2.i && p.j == p2.j) {
                index2 = i;
                break;
            }
        }
        int diff = Math.abs(index1 - index2);
        return diff == 1 || diff == loop.size() - 1;
    }

    private void findLoop() {

        Direction from = null;

        while (true) {

            Position currentPosition = loop.get(loop.size() - 1);
            Pipe currentPipe = pipes[currentPosition.i][currentPosition.j];

            if (currentPipe == Pipe.START && loop.size() > 1) {
                break;
            }

            Direction to;

            if (from == null) {
                to = getInitialDirection(currentPosition);
            } else {
                to = currentPipe.connection1 == from
                        ? currentPipe.connection2
                        : currentPipe.connection1;
            }

            from = Direction.getOpposite(to);

            loop.add(switch (to) {
                case UP -> new Position(currentPosition.i - 1, currentPosition.j);
                case DOWN -> new Position(currentPosition.i + 1, currentPosition.j);
                case LEFT -> new Position(currentPosition.i, currentPosition.j - 1);
                case RIGHT -> new Position(currentPosition.i, currentPosition.j + 1);
            });
        }

        loop.remove(loop.size() - 1);
    }

    void parseFile() {

        List<String> lines = readLines().toList();

        pipes = new Pipe[lines.size()][lines.get(0).length()];
        loop = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                Pipe pipe = Pipe.fromSymbol(chars[j]);
                pipes[i][j] = pipe;
                if (pipe == Pipe.START) {
                    loop.add(new Position(i,j));
                }
            }
        }
    }
}

