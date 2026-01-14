package advent_of_code.y2024.day14;


import advent_of_code.utils.Characters;
import advent_of_code.utils.Matrix;
import advent_of_code.utils.Strings;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestroomRedoubt2 extends AbstractRunnable {

    public static void main(String[] args) {
        new RestroomRedoubt2().start();
    }

    static int W = 101;
    static int H = 103;

    @Override
    protected String source() { return "input.txt"; }

    List<Robot> robots;
    Matrix space;
    static Map<String, Integer> robotsPosition;

    static class Vector {
        int x;
        int y;
        Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Robot {
        Vector position;
        Vector velocity;
        Robot(Vector position, Vector velocity) {
            this.position = position;
            this.velocity = velocity;
        }
        void move() {
            this.position.x += this.velocity.x;
            this.position.y += this.velocity.y;

            if (this.position.x > W - 1)
                this.position.x = this.position.x - W;
            else if (this.position.x < 0)
                this.position.x = W - Math.abs(this.position.x);

            if (this.position.y > H - 1)
                this.position.y = this.position.y - H;
            else if (this.position.y < 0)
                this.position.y = H - Math.abs(this.position.y);

            String positionKey = Strings.of(this.position.x, this.position.y);
            int count = robotsPosition.getOrDefault(positionKey, 0);
            robotsPosition.put(positionKey, count + 1);
        }
    }

    @Override
    protected void init() {
        space = new Matrix(new char[H][W]);
        robots = new ArrayList<>();
        robotsPosition = new HashMap<>();
        for(String line : readLines().toList()) {
            Vector position, velocity;
            String[] splitSpace = line.split(" ");
            String[] slitComma = splitSpace[0].split(",");
            position = new Vector(Integer.parseInt(slitComma[0].replace("p=", "")), Integer.parseInt(slitComma[1]));
            slitComma = splitSpace[1].split(",");
            velocity = new Vector(Integer.parseInt(slitComma[0].replace("v=", "")), Integer.parseInt(slitComma[1]));
            robots.add(new Robot(position, velocity));
        }
        updateSpace();
    }

    @Override
    protected Object run() {
        for (int i = 1; i < 50000; i++) {
            updateRobotsPositions();
            updateSpace();
            if(treeIsPresent())
                return i;
        }
        return  -1;
    }

    private void updateRobotsPositions() {
        robotsPosition.clear();
        robots.forEach(Robot::move);
    }

    private boolean treeIsPresent() {
        return flat(space).contains("11111111111111");
    }

    private String flat(Matrix matrix) {
        StringBuilder flat = new StringBuilder();
        for (int i = 0; i < matrix.length(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                flat.append(matrix.get(i, j));
            }
        }
        return flat.toString();
    }

    private void updateSpace() {
        space.setAll('.');
        for (int i = 0; i < space.length(); i++) {
            for (int j = 0; j < space.get(i).length; j++) {
                int robotsCount = robotsPosition.getOrDefault(Strings.of(j,i), 0);
                space.set(i, j, robotsCount == 0 ? '.' : Characters.fromInt(robotsCount));
            }
        }
    }

}
