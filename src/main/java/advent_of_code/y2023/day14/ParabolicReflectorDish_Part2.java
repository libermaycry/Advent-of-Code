package advent_of_code.y2023.day14;

import advent_of_code.y2023.AbstractRunnable;

public class ParabolicReflectorDish_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new ParabolicReflectorDish_Part2().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day14/rocks.txt";
    }

    char[][] chars;

    @Override
    public void run() {

        System.out.println(1_000_000_000 % 156);

        chars = readLinesToMatrix();

        for (int i = 0; i < 1_000_000_000; i++) {
//            if (i % 100_000 == 0) System.out.println(i);
            cycle();
        }

        System.out.println(getLoad());
    }

    void cycle() {
        while(rollUp());
        while(rollLeft());
        while(rollDown());
        while(rollRight());
        System.out.println(getLoad());
    }

    boolean rollUp() {
        boolean rolled = false;
        for (int i = 1; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                char tile = chars[i][j];
                char target = chars[i-1][j];
                if (tile == 'O' && target == '.') {
                    chars[i-1][j] = 'O';
                    chars[i][j] = '.';
                    rolled = true;
                }
            }
        }
        return rolled;
    }

    boolean rollLeft() {
        boolean rolled = false;
        for (int j = 1; j < chars[0].length; j++) {
            for (int i = 0; i < chars.length; i++) {
                char tile = chars[i][j];
                char target = chars[i][j-1];
                if (tile == 'O' && target == '.') {
                    chars[i][j-1] = 'O';
                    chars[i][j] = '.';
                    rolled = true;
                }
            }
        }
        return rolled;
    }

    boolean rollDown() {
        boolean rolled = false;
        for (int i = chars.length - 2; i >= 0; i--) {
            for (int j = 0; j < chars[i].length; j++) {
                char tile = chars[i][j];
                char target = chars[i+1][j];
                if (tile == 'O' && target == '.') {
                    chars[i+1][j] = 'O';
                    chars[i][j] = '.';
                    rolled = true;
                }
            }
        }
        return rolled;
    }

    boolean rollRight() {
        boolean rolled = false;
        for (int j = chars[0].length - 2; j >= 0; j--) {
            for (int i = 0; i < chars.length; i++) {
                char tile = chars[i][j];
                char target = chars[i][j+1];
                if (tile == 'O' && target == '.') {
                    chars[i][j+1] = 'O';
                    chars[i][j] = '.';
                    rolled = true;
                }
            }
        }
        return rolled;
    }

    int getLoad() {
        int load = 0;
        for (int i = 0; i < chars.length; i++) {
            int n = 0;
            for (int j = 0; j < chars[i].length; j++) {
                if (chars[i][j] == 'O') n++;
            }
            load += n * (chars.length - i);
        }
        return load;
    }
}
