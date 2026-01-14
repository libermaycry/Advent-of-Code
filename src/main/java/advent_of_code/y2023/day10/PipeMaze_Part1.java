package advent_of_code.y2023.day10;


import advent_of_code.y2023.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class PipeMaze_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new PipeMaze_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day10/pipes.txt";
    }

    record Position (int i, int j) {}


    List<Position> loop;

    Pipe[][] pipes;

    @Override
    public void run() {

        parseFile();

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

        System.out.println(loop.size()/2);
    }

    private Direction getInitialDirection(Position position) {
        return Direction.UP;
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

//        for (int i = 0; i < lines.size(); i++) {
//            for (int j = 0; j < lines.get(i).length(); j++) {
//                System.out.print("" + pipes[i][j].symbol);
//            }
//            System.out.println();
//        }

    }
}

