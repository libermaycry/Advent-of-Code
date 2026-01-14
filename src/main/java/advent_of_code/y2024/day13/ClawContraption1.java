package advent_of_code.y2024.day13;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class ClawContraption1 extends AbstractRunnable {

    public static void main(String[] args) {
        new ClawContraption1().start();
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

    record Machine(List<Button> buttons, int priceX, int priceY) {}

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
        machines.add(new Machine(buttons, priceX, priceY));
    }

    @Override
    protected Object run() {
        return machines.stream()
                .map(this::minTokensToWin)
                .mapToInt(x -> x)
                .sum();
    }

    private int minTokensToWin(Machine machine) {

        Button buttonA = getButton(machine, ButtonType.A);
        Button buttonB = getButton(machine, ButtonType.B);

        List<Integer> costs = new ArrayList<>();

        for (int nA = 1; nA <= 100; nA++) {
            for (int nB = 1; nB <= 100; nB++) {
                int endX = buttonA.x * nA + buttonB.x * nB;
                int endY = buttonA.y * nA + buttonB.y * nB;
                if (machine.priceX == endX && machine.priceY == endY) {
                    costs.add(nA * ButtonType.A.tokens + nB * ButtonType.B.tokens);
                }
            }
        }

        return costs.stream().mapToInt(i -> i).min().orElse(0);
    }

    private Button getButton(Machine machine, ButtonType type) {
        return machine.buttons
                .stream()
                .filter(b -> type.equals(b.type))
                .findFirst()
                .orElseThrow();
    }

}
