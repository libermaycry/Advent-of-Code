package advent_of_code.utils.maze_resolver;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MazeResolverAll {

    public List<Solution> solutions;

    Matrix maze;
    char floor;
    char start;
    char exit;
    char wall;

    public MazeResolverAll(Matrix maze,
                           char floor,
                           char start,
                           char exit,
                           char wall) {
        this.floor = floor;
        this.start = start;
        this.exit = exit;
        this.wall = wall;
        this.maze = maze;
        this.solutions = new ArrayList<>();
    }

    public void findSolutions() {
        visit(getStartPosition(), new ArrayList<>());
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

    private void visit(Position position, List<Position> path) {
        path.add(position);
        if (maze.check(position.i,position.j, exit)) {
            solutions.add(new Solution(path, path.size()));
            return;
        }
        for (Position neighbor : getNeighborsNotVisited(position, path)) {
            visit(neighbor, new ArrayList<>(path));
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
