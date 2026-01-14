package advent_of_code.y2024.day16;

import advent_of_code.utils.Matrix;
import advent_of_code.utils.maze_resolver.MazeResolverCheapest;
import advent_of_code.utils.Position;

import java.util.List;

public class ReindeerMazeResolver1 extends MazeResolverCheapest {

    public ReindeerMazeResolver1(Matrix maze, char floor, char start, char exit, char wall, Orientation startOrientation) {
        super(maze, floor, start, exit, wall, startOrientation);
    }

    @Override
    protected long cost(List<Position> path) {
        Orientation orientation = Orientation.RIGHT;
        long turns = 0;
        Position current = path.getFirst();
        for (int i = 1; i < path.size(); i++) {
            Position position = path.get(i);
            Orientation newOrientation = getOrientation(current, position);
            if (newOrientation != orientation) {
                turns ++;
                orientation = newOrientation;
            }
            current = position;
        }
        return turns * 1000 + path.size() - 1;
    }

}
