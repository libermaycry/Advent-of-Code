package advent_of_code.y2023.day14;

import advent_of_code.y2023.AbstractRunnable;

public class ParabolicReflectorDish_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new ParabolicReflectorDish_Part1().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day14/rocks.txt";
    }

    char[][] chars;

    @Override
    public void run() {

        chars = readLinesToMatrix();

        while(rollUp());

        System.out.println(getLoad());
    }

    boolean rollUp() {

        boolean rolled = false;

        for (int i = 1; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                char tile = chars[i][j];
                char tileUp = chars[i-1][j];
                if (tile == 'O' && tileUp == '.') {
                    chars[i-1][j] = 'O';
                    chars[i][j] = '.';
                    rolled = true;
                }
            }
        }

        printMatrix(chars);

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
