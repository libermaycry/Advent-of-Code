package advent_of_code.y2025.day10;


import advent_of_code.utils.Permutations;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class Factory1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Factory1().start();
    }

    record Button (List<Integer> indexes) {}

    record Machine (List<Boolean> expectedLights, List<Button> buttons) {}

    List<Machine> machines;

    @Override
    protected String source() { return "input.txt"; }
//    protected String source() { return "test.txt"; }

    @Override
    protected void init() {
        machines = new ArrayList<>();
        readLines().forEach(line -> {
            char[] expectedLightsCharArray = line.split(" ")[0]
                    .replace("[", "")
                    .replace("]", "")
                    .toCharArray();
            List<Boolean> expectedLights = new ArrayList<>();
            for (char c : expectedLightsCharArray) {
                expectedLights.add(c == '#');
            }
            List<Button> buttons = getButtons(line);
            machines.add(new Machine(expectedLights, buttons));
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
        return machines.stream()
                .map(this::fewest)
                .mapToLong(x -> x)
                .sum();
    }

    private long fewest(Machine machine) {
        System.out.println(machine);
        for (int n = 1; true; n++) {

            // All the combinations of n buttons
            List<List<Button>> permutations = Permutations.of(machine.buttons, n);
            List<Boolean> lights = new ArrayList<>(machine.expectedLights);

            for (List<Button> buttons : permutations) {
                reset(lights);
                buttons.forEach(button -> pressButton(button, lights));
                if (lights.equals(machine.expectedLights)) {
                    return n;
                }
            }
        }
    }

    private void pressButton(Button button, List<Boolean> lights) {
        for (int index : button.indexes) {
            lights.set(index, !lights.get(index));
        }
    }

    private void reset(List<Boolean> lights) {
        lights.replaceAll(ignored -> false);
    }

}
