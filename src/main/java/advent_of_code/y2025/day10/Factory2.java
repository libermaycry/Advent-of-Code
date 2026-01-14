package advent_of_code.y2025.day10;


import advent_of_code.utils.Permutations;
import advent_of_code.y2025.AbstractRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factory2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Factory2().start();
    }

    record Button (List<Integer> indexes) {}

    record Machine (List<Button> buttons, List<Integer> expected) {}

    List<Machine> machines;

    @Override
//    protected String source() { return "input.txt"; }
    protected String source() { return "test.txt"; }

    @Override
    protected void init() {
        machines = new ArrayList<>();
        readLines().forEach(line -> {
            List<Integer> expected = new ArrayList<>();
            for(String val : line.substring(line.indexOf('{') + 1, line.indexOf('}')).trim().split(",")) {
                expected.add(Integer.parseInt(val));
            }
            List<Button> buttons = getButtons(line);
            machines.add(new Machine(buttons, expected));
        });
    }

    private List<Button> getButtons(String line) {
        String[] split = line.substring(line.indexOf(']') + 1, line.indexOf('{')).trim().split(" ");
        List<Button> buttons = new ArrayList<>();
        for (String buttonString : split) {
            List<Integer> values = new ArrayList<>();
            String[] splitComma = buttonString.replace("(", "").replace(")", "").split(",");
            for (String val : splitComma) {
                values.add(Integer.parseInt(val));
            }
            buttons.add(new Button(values));
        }
        return buttons;
    }

    @Override
    protected Object run() {
        fewest(machines.getFirst());
        return null;
    }

    private long fewest(Machine machine) {

        buildPermutationsFile(machine.buttons, 8);
        return 1;

    }

    private void pressButton(Button button, List<Integer> values) {
        for (int index : button.indexes) {
            values.set(index, values.get(index) + 1);
        }
    }

    private void reset(List<Integer> values) {
        values.replaceAll(ignored -> 0);
    }

    private <T> String buildPermutationsFile(List<T> values, int n) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.text", true))) {
            buildPermutations(values,n,writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> void buildPermutations(List<T> values, int n, BufferedWriter writer) throws IOException {

        if (n == 1) {
            values.forEach(value -> {
                try {
                    writer.write(List.of(value).toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        values.forEach(value ->
                Permutations.of(values, n - 1).forEach(perm -> {
                    List<T> newPerm = new ArrayList<>(perm);
                    newPerm.addFirst(value);
                    try {
                        writer.write(newPerm.toString());
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }
}
