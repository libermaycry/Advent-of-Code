package advent_of_code.y2024.day16;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.maze_resolver.MazeResolverCheapest;
import advent_of_code.y2024.AbstractRunnable;

public class ReindeerMaze1 extends AbstractRunnable {

    public static void main(String[] args) {
        new ReindeerMaze1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    ReindeerMazeResolver1 mazeResolver;

    @Override
    protected void init() {
        Matrix maze = new Matrix(readLinesToMatrix());
        mazeResolver = new ReindeerMazeResolver1(maze, '.', 'S', 'E', '#', MazeResolverCheapest.Orientation.RIGHT);
    }

    @Override
    protected Object run() {
        mazeResolver.findSolution();
        System.out.println(mazeResolver.solution.positions.size());
        return mazeResolver.solution.cost;
    }

}
