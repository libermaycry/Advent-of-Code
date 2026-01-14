package advent_of_code.y2024.day13;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class ClawContraption2 extends AbstractRunnable {

    public static void main(String[] args) {
        new ClawContraption2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    enum ButtonType {
        A(3),
        B(1);

        final int tokens;

        ButtonType(int tokens) {
            this.tokens = tokens;
        }
    }

    record Button(ButtonType type, int x, int y) {}

    record Machine(List<Button> buttons, long priceX, long priceY) {}

    List<Machine> machines;

    @Override
    protected void init() {
        machines = new ArrayList<>();
        for (List<String> block : readBlocksOfLines()) {
            parseBlock(block);
        }
    }

    private void parseBlock(List<String> block) {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < block.size() - 1; i++) {
            String[] splitColon = block.get(i).split(":");
            String name = splitColon[0].replace("Button ", "");
            String[] splitComma = splitColon[1].split(",");
            int x = Integer.parseInt(splitComma[0].replace(" X+", ""));
            int y = Integer.parseInt(splitComma[1].replace(" Y+", ""));
            ButtonType buttonType = name.equals("A") ? ButtonType.A : ButtonType.B;
            buttons.add(new Button(buttonType, x, y));
        }
        String[] splitCommaPrize = block.getLast().split(":")[1].split(",");
        int priceX = Integer.parseInt(splitCommaPrize[0].replace(" X=", ""));
        int priceY = Integer.parseInt(splitCommaPrize[1].replace(" Y=", ""));
        long offset = 10000000000000L;
        machines.add(new Machine(buttons, priceX + offset, priceY + offset));
    }

    @Override
    protected Object run() {
        return machines.stream()
                .map(this::minTokensToWin)
                .mapToLong(x -> x)
                .sum();
    }

    private long minTokensToWin(Machine machine) {

        Button buttonA = getButton(machine, ButtonType.A);
        Button buttonB = getButton(machine, ButtonType.B);

        long Ax = buttonA.x;
        long Ay = buttonA.y;
        long Bx = buttonB.x;
        long By = buttonB.y;
        long Px = machine.priceX;
        long Py = machine.priceY;

        double numerator = Double.parseDouble(Long.valueOf(Ax * Py - Ay * Px).toString());
        double denominator = Double.parseDouble(Long.valueOf(Ax * By - Ay * Bx).toString());

        double nB = numerator / denominator;
        double nA = (Px - nB * Bx) / Ax;

        // If nA and nB are not integers there is no way to win the prize
        if (Math.floor(nA) != nA || Math.floor(nB) != nB) return 0;

        return Double.valueOf(nA).longValue() * ButtonType.A.tokens
                + Double.valueOf(nB).longValue() * ButtonType.B.tokens;
    }

    private Button getButton(Machine machine, ButtonType type) {
        return machine.buttons
                .stream()
                .filter(b -> type.equals(b.type))
                .findFirst()
                .orElseThrow();
    }
}
