package advent_of_code.y2025.day12;


import advent_of_code.utils.Matrix;
import advent_of_code.y2025.AbstractRunnable;

import java.util.*;

public class Template extends AbstractRunnable {

    public static void main(String[] args) {
        new Template().start();
    }

    Map<Integer, Matrix> shapes;
    List<Region> regions;

    record Region(int width, int height, List<Integer> howMany) {}

    @Override
    protected String source() { return "test.txt"; }

    @Override
    protected void init() {
        shapes = new HashMap<>();
        regions = new ArrayList<>();
        List<List<String>> blocks = readBlocksOfLines();
        for (int i = 0; i < blocks.size(); i++) {
            List<String> lines = blocks.get(i);
            if (i < blocks.size() - 1) {
                shapes.put(i, new Matrix(readLinesToMatrix(lines.subList(1, lines.size()))));
            } else {
                lines.forEach(line -> {
                    String[] split = line.split(":");
                    String[] byX = split[0].split("x");
                    int width = Integer.parseInt(byX[0]);
                    int height = Integer.parseInt(byX[1]);
                    List<Integer> howMany = new ArrayList<>();
                    for(String s : split[1].trim().split(" ")) {
                        howMany.add(Integer.parseInt(s));
                    }
                    regions.add(new Region(width, height, howMany));
                });
            }
        };
    }

    @Override
    protected Object run() {
        System.out.println(shapes);
        return null;
    }

}
