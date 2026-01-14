package advent_of_code.y2023.day1;


import advent_of_code.y2023.AbstractRunnable;

import static java.lang.Character.isDigit;

public class Trebuchet_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Trebuchet_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day1/calibration_document.txt";
    }

    @Override
    public void run() {
        int result = readLines()
                .map(this::getCalibrationValue)
                .reduce(0, Integer::sum);
        System.out.println(result);
    }

    private int getCalibrationValue(String line) {

        char[] chars = line.toCharArray();

        Character firstDigit = null;
        Character lastDigit = null;

        for (char c : chars) {
            if (isDigit(c)) {
                firstDigit = c;
                break;
            }
        }

        for (int i = chars.length-1; i >= 0; i--) {
            if (isDigit(chars[i])) {
                lastDigit = chars[i];
                break;
            }
        }

        return Integer.parseInt("" + firstDigit + lastDigit);
    }


}
