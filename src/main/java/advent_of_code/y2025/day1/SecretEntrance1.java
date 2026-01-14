package advent_of_code.y2025.day1;


import advent_of_code.y2025.AbstractRunnable;

public class SecretEntrance1 extends AbstractRunnable {

    public static void main(String[] args) {
        new SecretEntrance1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    @Override
    protected Object run() {
        int current = 50;
        int password = 0;
        for (String line : readLines().toList()) {
            String dir = String.valueOf(line.charAt(0));
            int value = Integer.parseInt(line.replace(dir, ""));
            int sign = dir.equals("L") ? -1 : 1;
            value = value % 100;
            current += sign * value;
            if (current < 0) {
                current = 99 - (-current) + 1;
            } else if (current > 99) {
                current = current - 99 - 1;
            }
            if (current == 0) password ++;
        }

        return password;
    }

}
