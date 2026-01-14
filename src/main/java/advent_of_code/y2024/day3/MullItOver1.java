package advent_of_code.y2024.day3;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver1 extends AbstractRunnable {

    public static void main(String[] args) {
        new MullItOver1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    record Mul(int n1, int n2) {}

    List<Mul> mulInstructions;

    @Override
    protected void init() {
        mulInstructions = new ArrayList<>();
        String regex = "mul\\(\\d+,\\d+\\)";
        readLines().forEach(line -> {
            Matcher matcher = Pattern.compile(regex).matcher(line);
            while (matcher.find()) {
                String[] split = matcher.group().replace("mul", "")
                        .replace("(", "")
                        .replace(")", "")
                        .split(",");
                mulInstructions.add(new Mul(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }
        });
    }

    @Override
    protected Object run() {
        return mulInstructions.stream()
                .map(mul -> mul.n1 * mul.n2)
                .mapToLong(x -> x)
                .sum();
    }





}
