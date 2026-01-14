package advent_of_code.y2022.day5;

import advent_of_code.y2023.AbstractRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SupplyStacks_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new SupplyStacks_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day5/cranes_steps.txt";
    }

    Map<Integer, Stack<String>> stacks;

    @Override
    public void run() {

        initStacks();

        readLines().forEach(line -> {

            int nMoves = Integer.parseInt(line.substring(4, line.indexOf("from")).trim());
            int from = Integer.parseInt(line.substring(line.indexOf("from") + 4, line.indexOf("to")).trim());
            int to = Integer.parseInt(line.substring(line.indexOf("to") + 2).trim());

            for (int i = 0; i < nMoves; i++) {
                String popped = stacks.get(from).pop();
                stacks.get(to).push(popped);
            }
        });

        String result = "";

        for (int nStack : stacks.keySet()) {
            result = result.concat(stacks.get(nStack).pop());
        }

        System.out.println(result);
    }

    private void initStacks() {

        stacks = new HashMap<>();

        Stack<String> stack = new Stack<>();


        stack.push("R");
        stack.push("S");
        stack.push("L");
        stack.push("F");
        stack.push("Q");
        stacks.put(1, stack);

        stack = new Stack<>();
        stack.push("N");
        stack.push("Z");
        stack.push("Q");
        stack.push("G");
        stack.push("P");
        stack.push("T");
        stacks.put(2, stack);

        stack = new Stack<>();
        stack.push("S");
        stack.push("M");
        stack.push("Q");
        stack.push("B");
        stacks.put(3, stack);

        stack = new Stack<>();
        stack.push("T");
        stack.push("G");
        stack.push("Z");
        stack.push("J");
        stack.push("H");
        stack.push("C");
        stack.push("B");
        stack.push("Q");
        stacks.put(4, stack);

        stack = new Stack<>();
        stack.push("P");
        stack.push("H");
        stack.push("M");
        stack.push("B");
        stack.push("N");
        stack.push("F");
        stack.push("S");
        stacks.put(5, stack);

        stack = new Stack<>();
        stack.push("P");
        stack.push("C");
        stack.push("Q");
        stack.push("N");
        stack.push("S");
        stack.push("L");
        stack.push("V");
        stack.push("G");
        stacks.put(6, stack);

        stack = new Stack<>();
        stack.push("W");
        stack.push("C");
        stack.push("F");
        stacks.put(7, stack);

        stack = new Stack<>();
        stack.push("Q");
        stack.push("H");
        stack.push("G");
        stack.push("Z");
        stack.push("W");
        stack.push("V");
        stack.push("P");
        stack.push("M");
        stacks.put(8, stack);

        stack = new Stack<>();
        stack.push("G");
        stack.push("Z");
        stack.push("D");
        stack.push("L");
        stack.push("C");
        stack.push("N");
        stack.push("R");
        stacks.put(9, stack);
    }
}
