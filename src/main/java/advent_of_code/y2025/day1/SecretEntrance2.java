package advent_of_code.y2025.day1;


import advent_of_code.y2025.AbstractRunnable;

public class SecretEntrance2 extends AbstractRunnable {

    public static void main(String[] args) {
        new SecretEntrance2().start();
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
            for (int i = 0; i < value; i++) {
                current = current + sign;
                if (current == 0) {
                    password ++;
                } else if (current < 0) {
                    current = 99;
                } else if (current > 99) {
                    current = 0;
                    password ++;
                }
            }
        }
        return password;
    }

}
