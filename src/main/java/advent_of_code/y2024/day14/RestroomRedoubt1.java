package advent_of_code.y2024.day14;


import advent_of_code.utils.Characters;
import advent_of_code.utils.Matrix;
import advent_of_code.utils.Numbers;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class RestroomRedoubt1 extends AbstractRunnable {

    public static void main(String[] args) {
        new RestroomRedoubt1().start();
    }

    static int W = 101;
    static int H = 103;

    @Override
    protected String source() { return "input.txt"; }

    List<Robot> robots;
    Matrix space;

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
        }
    }

    @Override
    protected void init() {
        space = new Matrix(new char[H][W]);
        robots = new ArrayList<>();
        for(String line : readLines().toList()) {
            Vector position, velocity;
            String[] splitSpace = line.split(" ");
            String[] slitComma = splitSpace[0].split(",");
            position = new Vector(Integer.parseInt(slitComma[0].replace("p=", "")), Integer.parseInt(slitComma[1]));
            slitComma = splitSpace[1].split(",");
            velocity = new Vector(Integer.parseInt(slitComma[0].replace("v=", "")), Integer.parseInt(slitComma[1]));
            robots.add(new Robot(position, velocity));
        }
    }

    @Override
    protected Object run() {

        updateSpace();
        delay();

        int topLeft = countSector(0, 0);
        int topRight = countSector(0, W / 2 + 1);
        int bottomLeft = countSector(H / 2 + 1, 0);
        int bottomRight = countSector(H / 2 + 1, W / 2 + 1);

        return topRight * topLeft * bottomRight * bottomLeft;
    }

    private int countSector(int startI, int startJ) {
        int count = 0;
        for (int i = startI; i < startI + H / 2; i++) {
            for (int j = startJ; j < startJ + W / 2; j++) {
                char c = space.get(i, j);
                if (c != '.') {
                    count += Numbers.fromChar(c);
                }
            }
        }
        return count;
    }

    private void delay() {
        for (int i = 0; i < 100; i++) {
            robots.forEach(Robot::move);
            updateSpace();
        }
    }

    private void updateSpace() {
        space.setAll('.');
        for (int i = 0; i < space.length(); i++) {
            for (int j = 0; j < space.get(i).length; j++) {
                int finalI = i;
                int finalJ = j;
                int robotsCount = (int) robots.stream()
                        .filter(robot -> robot.position.y == finalI && robot.position.x == finalJ)
                        .count();
                space.set(i, j, robotsCount == 0 ? '.' : Characters.fromInt(robotsCount));
            }
        }
    }

}
