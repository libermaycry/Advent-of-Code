package advent_of_code.y2024.day16;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Strings;
import advent_of_code.utils.maze_resolver.MazeResolverAllCheapest;
import advent_of_code.y2024.AbstractRunnable;

import java.util.HashSet;

public class ReindeerMaze2 extends AbstractRunnable {

    public static void main(String[] args) {
        new ReindeerMaze2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    ReindeerMazeResolver2 mazeResolver;

    @Override
    protected void init() {
        Matrix maze = new Matrix(readLinesToMatrix());
        mazeResolver = new ReindeerMazeResolver2(maze, '.', 'S', 'E', '#', MazeResolverAllCheapest.Orientation.RIGHT);
    }

    @Override
    protected Object run() {
        mazeResolver.findSolutions();
        HashSet<String> tiles = new HashSet<>();
        mazeResolver.solutions.forEach(solution -> solution.positions.forEach(position ->
                tiles.add(Strings.of(position)))
        );
        return tiles.size();
    }

}
