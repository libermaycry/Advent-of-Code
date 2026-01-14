package advent_of_code.y2023.day15;

import advent_of_code.y2023.AbstractRunnable;

import java.util.Arrays;

public class LensLibrary_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new LensLibrary_Part1().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day15/sequence.txt";
    }

    @Override
    public void run() {

        String[] steps = readLines().toList().get(0).split(",");

        int result = Arrays.stream(steps)
                .mapToInt(this::hash)
                .sum();

        System.out.println(result);
    }

    int hash(String string) {
        int hash = 0;
        for (char c : string.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }
}
