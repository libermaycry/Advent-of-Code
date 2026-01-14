package advent_of_code.y2022.day8;


import advent_of_code.y2023.AbstractRunnable;

import java.util.List;

public class TreetopTreeHouse_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new TreetopTreeHouse_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day8/input.txt";
    }

    int[][] trees;

    @Override
    public void run() {

        parseFile();

        int count = 0;

        for (int i = 0; i < trees.length; i++) {
            for (int j = 0; j < trees[i].length; j++) {
                if (isVisible(i,j)) count++;
            }
        }

        System.out.println(count);

    }

    private boolean isVisible(int I, int J) {

        if (I == 0 || J == 0 || I == trees.length - 1 || J == trees[0].length - 1)
            return true;

        for (int i = I; i < trees.length; i++) {
            for (int j = 0; j < trees[i].length; j++) {
                if (i == I && j == J) continue;
                if (trees[i][j] >= trees[I][J])
                    return false;
            }
        }

        for (int j = J; j < trees.length; j++) {
            for (int i = 0; i < trees.length; i++) {
                if (i == I && j == J) continue;
                if (trees[i][j] >= trees[I][J])
                    return false;
            }
        }

        return true;
    }

    void parseFile() {

        List<String> lines = readLines().toList();

        trees = new int[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                trees[i][j] = Integer.parseInt(""+chars[j]);
            }
        }

//        for (int i = 0; i < lines.size(); i++) {
//            for (int j = 0; j < lines.get(i).length(); j++) {
//                System.out.print("" + trees[i][j]);
//            }
//            System.out.println();
//        }

    }
}

