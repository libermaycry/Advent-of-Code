package advent_of_code.y2023.day8;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.*;

import static java.lang.Character.isDigit;

public class HauntedWasteland_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new HauntedWasteland_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day8/navigation.txt";
    }

    record Node (String name, String left, String right) {}

    Map<String, Node> nodes;

    Node currentNode;

    Node destinationNode;

    List<Character> instructions;

    @Override
    public void run() {

        parseFile();

        int count = 0;

        int instructionsIdx = 0;

        while (!currentNode.name.equals(destinationNode.name)) {

            if (instructionsIdx > instructions.size() - 1) {
                instructionsIdx = 0;
            }

            currentNode = instructions.get(instructionsIdx) == 'L'
                    ? nodes.get(currentNode.left)
                    : nodes.get(currentNode.right);

            instructionsIdx ++;
            count ++;
        }

        System.out.println(count);
    }

    void parseFile() {

        nodes = new HashMap<>();
        instructions = new ArrayList<>();

        List<String> lines = readLines().toList();

        for(char c : lines.get(0).toCharArray())
            instructions.add(c);

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if(Strings.isBlank(line)) continue;
            String[] splitEquals = line.split("=");
            String name = splitEquals[0].trim();
            String[] splitComma = splitEquals[1]
                    .replace("(", "")
                    .replace(")", "")
                    .split(",");

            Node node = new Node(name, splitComma[0].trim(), splitComma[1].trim());

            if (name.equals("AAA")) {
                currentNode = node;
            } else if (name.equals("ZZZ")) {
                destinationNode = node;
            }

            nodes.put(name, node);
        }
    }
}

