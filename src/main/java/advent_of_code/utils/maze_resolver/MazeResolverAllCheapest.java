package advent_of_code.utils.maze_resolver;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Position;
import advent_of_code.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public abstract class MazeResolverAllCheapest {

    public List<Solution> solutions;
    HashMap<String, Long> visited;

    Matrix maze;
    char floor;
    char start;
    char exit;
    char wall;
    Orientation startOrientation;

    public enum Orientation {UP, LEFT, RIGHT, DOWN}

    protected abstract long cost(List<Position> path);

    public MazeResolverAllCheapest(Matrix maze,
                                   char floor,
                                   char start,
                                   char exit,
                                   char wall,
                                   Orientation startOrientation) {
        this.floor = floor;
        this.start = start;
        this.exit = exit;
        this.wall = wall;
        this.maze = maze;
        this.startOrientation = startOrientation;
        this.solutions = new ArrayList<>();
        this.visited = new HashMap<>();
    }

    public void findSolutions() {
        visit(getStartPosition(), new ArrayList<>(), startOrientation);
    }

    public void printSolutions() {
        solutions.forEach(solution -> printPath(solution.positions));
    }

    private Position getStartPosition() {
        for(int i = 0; i < maze.length(); i++) {
            for(int j = 0; j < maze.get(i).length; j++) {
                if (maze.check(i,j,start)) {
                    return new Position(i,j);
                }
            }
        }
        throw new IllegalStateException();
    }

    private void visit(Position position, List<Position> path, Orientation orientation) {

        path.add(position);

        String key = Strings.of(position, orientation);
        long cost = cost(path);
        if (visited.containsKey(key) && cost > visited.get(key))
            return;

        visited.put(key, cost);

        if (!solutions.isEmpty() && cost > solutions.getFirst().cost) {
            return;
        }

        if (maze.check(position.i, position.j, exit)) {
            if (solutions.isEmpty()) {
                solutions.add(new Solution(path, cost));
            } else {
                if (cost < solutions.getFirst().cost)
                    solutions.clear();
                solutions.add(new Solution(path, cost));
            }
            return;
        }

        for(Position neighbor : getNeighborsNotVisited(position, path)) {
            visit(neighbor, new ArrayList<>(path), getOrientation(position, neighbor));
        }
    }

    private List<Position> getNeighborsNotVisited(Position position, List<Position> path) {
        return Stream.of(
                    new Position(position.i - 1, position.j),
                    new Position(position.i + 1, position.j),
                    new Position(position.i, position.j + 1),
                    new Position(position.i, position.j - 1))
                .filter(neighbor -> notVisited(neighbor, path))
                .toList();
    }

    private boolean notVisited(Position position, List<Position> path) {
        boolean isFloorOrExit = maze.check(position.i, position.j, floor) || maze.check(position.i, position.j, exit);
        return isFloorOrExit && !path.contains(position);
    }

    protected Orientation getOrientation(Position from, Position to) {
        if (from.i == to.i) {
            if (to.j > from.j) return Orientation.RIGHT;
            return Orientation.LEFT;
        } else {
            if (to.i > from.i) return Orientation.DOWN;
            return Orientation.UP;
        }
    }

    private void printPath(List<Position> positions) {
        System.out.println();
        for(int i = 0; i < maze.length(); i++) {
            for(int j = 0; j < maze.get(i).length; j++) {
                String cellPrint;
                if (maze.check(i,j,start) || maze.check(i,j,exit)) cellPrint = String.valueOf(maze.get(i,j));
                else if (positions.contains(new Position(i,j))) cellPrint = ".";
                else if (maze.check(i,j, floor)) cellPrint = " ";
                else cellPrint = ""+maze.get(i,j);

                System.out.print(cellPrint + " ");
            }
            System.out.println();
        }
    }
}
