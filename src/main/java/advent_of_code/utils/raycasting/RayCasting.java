package advent_of_code.utils.raycasting;

import advent_of_code.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RayCasting {
    
    List<Line> lines;
    List<Line> verticalLines;
    List<Line> horizontalLinesSameSide;
    List<Line> horizontalLinesOppositeSide;
    Map<Position, Boolean> isInternalCache;

    /**
     * Vertexes are the vertexes of the shape, they must be ordered (clockwise or anticlockwise).
     * For example if the shape is a rectangle like this:
     * A --- B
     * |     |
     * D --- C
     * The vertexes list is [A,B,C,D]
     */
    public RayCasting(List<Position> vertexes) {
        this.lines = buildLines(vertexes);
        this.isInternalCache = new HashMap<>();
        this.verticalLines = lines.stream().filter(this::isVertical).toList();
        this.horizontalLinesOppositeSide = new ArrayList<>();
        this.horizontalLinesSameSide = new ArrayList<>();
        this.lines.stream().filter(this::isHorizontal).forEach(line -> {
            Line previousLine = getPreviousLine(line);
            Line nextLine = getNextLine(line);
            if ((previousLine.start().i < line.start().i && nextLine.end().i < line.start().i)
                    || (previousLine.start().i > line.start().i && nextLine.end().i > line.start().i)) {
                horizontalLinesSameSide.add(line);
            } else {
                horizontalLinesOppositeSide.add(line);
            }
        });
    }

    private List<Line> buildLines(List<Position> vertexes) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < vertexes.size() - 1; i++) {
            lines.add(new Line(vertexes.get(i), vertexes.get(i+1)));
        }
        lines.add(new Line(vertexes.getLast(), vertexes.getFirst()));
        return lines;
    }

    public boolean isNotInternal(Position test) {
        return !isInternal(test);
    }

    public boolean isInternal(Position test) {
        if (isInternalCache.containsKey(test))
            return isInternalCache.get(test);
        boolean result = computeIsInternal(test);
        isInternalCache.put(test, result);
        return result;
    }

    private boolean computeIsInternal(Position test) {
        // If point is on a line it surely is inside
        if (getLineContainingPosition(test) != null)
            return true;

        long intersections = 0;

        // Count vertical lines on the right which intersect a ray cast from the tested point to the right
        intersections += verticalLines.stream()
                .filter(line -> line.start().j > test.j)
                .filter(line -> (line.start().i < test.i && test.i < line.end().i) || (line.end().i < test.i && test.i < line.start().i))
                .count();

        // Count horizontal lines that are on the right and have the same y height (same i)
        intersections += horizontalLinesOppositeSide.stream()
                .filter(line -> line.start().i == test.i)
                .filter(line -> line.start().j > test.j && line.end().j > test.j)
                .count();

        return intersections % 2 != 0;
    }

    private Line getLineContainingPosition(Position position) {
        for (Line line : lines) {
            if (isHorizontal(line)) {
                if (position.i == line.start().i
                        && Math.min(line.start().j, line.end().j) <= position.j
                        && position.j <= Math.max(line.start().j, line.end().j)) {
                    return line;
                }
            } else {
                if (position.j == line.start().j
                        && Math.min(line.start().i, line.end().i) <= position.i
                        && position.i <= Math.max(line.start().i, line.end().i)) {
                    return line;
                }
            }
        }
        return null;
    }

    private Line getPreviousLine(Line line) {
        return lines.stream().filter(l -> l.end().equals(line.start())).findFirst().orElseThrow();
    }

    private Line getNextLine(Line line) {
        return lines.stream().filter(l -> l.start().equals(line.end())).findFirst().orElseThrow();
    }

    public boolean isHorizontal(Line line) {
        return line.start().i == line.end().i;
    }

    private boolean isVertical(Line line) {
        return !isHorizontal(line);
    }

    public List<Line> getLines() {
        return lines;
    }

//    public static void main(String[] args) {
//        Matrix matrix = new Matrix(new char[][] {
//                { '.', '.', 'X', 'X', 'X', 'X', '.', '.', '.', '.' },
//                { '.', '.', 'X', '.', '.', 'X', '.', '.', '.', '.' },
//                { '.', '.', 'X', 'X', '.', 'X', '.', '.', '.', '.' },
//                { '.', '.', '.', 'X', '.', 'X', '.', '.', '.', '.' },
//                { '.', '.', '.', 'X', 'X', 'X', '.', '.', '.', '.' }
//        });
//        List<Position> vertexes = new ArrayList<>();
//        vertexes.add(new Position(0, 2));
//        vertexes.add(new Position(0, 5));
//        vertexes.add(new Position(4, 5));
//        vertexes.add(new Position(4, 3));
//        vertexes.add(new Position(2, 3));
//        vertexes.add(new Position(2, 2));
//
//
//        //System.out.println(new Raycasting(new Position(1,4), vertexes).isInside()); //inside
//        System.out.println(new Raycasting(vertexes).isInside(new Position(4,2))); //outside
//
//        for (int i = 0; i < matrix.length(); i++) {
//            for (int j = 0; j < matrix.get(i).length; j++) {
//                if (matrix.check(i,j,'.') && new Raycasting(vertexes).isInside(new Position(i,j))) {
//                    System.out.println(new Position(i,j));
//                }
//            }
//        }
//    }

//    public boolean isInside(Position test) {
//        if (getLineContainingPositionWithCache(test) != null)
//            return true;
//        int intersections = 0;
//        final int i = test.i;
//        final int maxJ = vertexes.stream().map(p -> p.j).mapToInt(x -> x).max().orElseThrow();
//        for (int j = test.j + 1; j < maxJ + 1; j++) {
//            //if (matrix.get(test.i, j) == emptySymbol) continue;
//            Line line = getLineContainingPositionWithCache(new Position(i, j));
//            if (line == null) continue;
//            if (isHorizontal(line)) {
//                Line previousLine = getPreviousLine(line);
//                Line nextLine = getNextLine(line);
//                if ((previousLine.start().i < i && nextLine.end().i < i) || (previousLine.start().i > i && nextLine.end().i > i)) {
//                    intersections += 2;
//                } else {
//                    intersections ++;
//                }
//                j = Math.max(line.start().j, line.end().j);
//            } else {
//                intersections ++;
//            }
//        }
//        return intersections % 2 != 0;
//    }

//    public Matrix buildMatrix() {
//        int maxI = vertexes.stream().map(p -> p.i).mapToInt(x -> x).max().orElseThrow();
//        int maxJ = vertexes.stream().map(p -> p.j).mapToInt(x -> x).max().orElseThrow();
//        Matrix matrix = new Matrix(new char[maxI+1][maxJ+1]);
//        matrix.setAll(emptySymbol);
//        for (Line line : lines) {
//            if (isHorizontal(line)) {
//                int fromJ = Math.min(line.start().j, line.end().j);
//                int toJ = Math.max(line.start().j, line.end().j);
//                for (int j = fromJ; j <= toJ; j++) {
//                    matrix.set(line.start().i, j, shapeSymbol);
//                }
//            } else {
//                int fromI = Math.min(line.start().i, line.end().i);
//                int toI = Math.max(line.start().i, line.end().i);
//                for (int i = fromI; i <= toI; i++) {
//                    matrix.set(i, line.start().j, shapeSymbol);
//                }
//            }
//        }
//        return matrix;
//    }
}
