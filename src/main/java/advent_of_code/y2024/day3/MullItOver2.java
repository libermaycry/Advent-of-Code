package advent_of_code.y2024.day3;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class MullItOver2 extends AbstractRunnable {

    public static void main(String[] args) {
        new MullItOver2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    @Override
    protected Object run() {

        boolean enabled = true;
        List<Long> multiplications = new ArrayList<>();

        String regex = "mul\\(\\d+,\\d+\\)|(do|don't)\\(\\)";
        for (String line : readLines().toList()) {
            for (String instruction : extractFromRegex(line, regex)) {
                if (instruction.startsWith("mul") && enabled) {
                    String[] split = splitMul(instruction);
                    multiplications.add(Long.parseLong(split[0]) * Long.parseLong(split[1]));
                } else if (instruction.equals("do()")) {
                    enabled = true;
                } else if (instruction.equals("don't()")) {
                    enabled = false;
                }
            }
        }

        return multiplications.stream().mapToLong(x -> x).sum();
    }

    private String[] splitMul(String instruction) {
        return instruction
                .replace("mul", "")
                .replace("(", "")
                .replace(")", "")
                .split(",");
    }
}
