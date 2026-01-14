package advent_of_code.y2023.day1;

import advent_of_code.y2023.AbstractRunnable;

import java.util.Map;

import static java.lang.Character.isDigit;

public class Trebuchet_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Trebuchet_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day1/calibration_document.txt";
    }

    Map<String, Integer> digitsInWords = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    @Override
    public void run() {
        int result = readLines()
                .map(this::getCalibrationValue)
                .reduce(0, Integer::sum);
        System.out.println(result);
    }

    private int getCalibrationValue(String line) {
        int firstDigit = getFirstDigit(line);
        int lastDigit = getLastDigit(line);
        return Integer.parseInt(String.valueOf(firstDigit) + lastDigit);
    }

    private Integer getFirstDigit(String line) {

        char[] chars = line.toCharArray();
        int indexFirstActualDigit = -1;
        int firstActualDigit = -1;

        for (int i = 0; i < chars.length; i++) {
            if (isDigit(chars[i])) {
                indexFirstActualDigit = i;
                firstActualDigit = Integer.parseInt(""+chars[i]);
                break;
            }
        }

        int indexFirstDigitInWord = -1;
        String firstWord = null;

        for (String digitInWord : digitsInWords.keySet()) {
            int index = line.indexOf(digitInWord);
            if (index >= 0 && (firstWord == null || index < indexFirstDigitInWord)) {
                indexFirstDigitInWord = index;
                firstWord = digitInWord;
            }
        }

        if (firstWord == null) {
            return firstActualDigit;
        }

        return indexFirstActualDigit < indexFirstDigitInWord
                ? firstActualDigit
                : digitsInWords.get(firstWord);
    }

    private int getLastDigit(String line) {

        char[] chars = line.toCharArray();
        int indexLastActualDigit = -1;
        int lastActualDigit = -1;

        for (int i = chars.length-1; i >= 0; i--) {
            if (isDigit(chars[i])) {
                indexLastActualDigit = i;
                lastActualDigit = Integer.parseInt(""+chars[i]);
                break;
            }
        }

        int indexLastDigitInWord = -1;
        String firstWord = null;

        for (String digitInWord : digitsInWords.keySet()) {
            int index = line.lastIndexOf(digitInWord);
            if (index >= 0 && (firstWord == null || index > indexLastDigitInWord)) {
                indexLastDigitInWord = index;
                firstWord = digitInWord;
            }
        }

        if (firstWord == null) {
            return lastActualDigit;
        }

        return indexLastActualDigit > indexLastDigitInWord
                ? lastActualDigit
                : digitsInWords.get(firstWord);
    }

}
