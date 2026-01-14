package advent_of_code.y2023.day8;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Numbers;
import advent_of_code.utils.Strings;
import advent_of_code.utils.Threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HauntedWasteland_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new HauntedWasteland_Part2().run();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day8/navigation.txt";
    }

    record Node (String name, String left, String right) {}

    Map<String, Node> nodes;

    List<Node> currentNodes;

    List<Character> instructions;

    List<Integer> indexes = new ArrayList<>();

    @Override
    public void run() {

        parseFile();

        List<Thread> threads = new ArrayList<>();

        currentNodes.forEach(node -> Threads.add(threads, () -> findIndex(node)));

        Threads.waitFinish(threads);

        System.out.println(getMcm(indexes));
    }

    long getMcm(List<Integer> numbers) {

        List<Map<Integer, Integer>> primeFactorsList = new ArrayList<>();

        for(Integer number : numbers) {
            primeFactorsList.add(getPrimeFactors(number));
        }

        Map<Integer, Integer> result = new HashMap<>();

        List<Integer> factors = primeFactorsList.stream()
                .flatMap(x -> x.keySet().stream())
                .distinct()
                .toList();

        for (int factor : factors) {
            int max = 0;
            for (Map<Integer, Integer> primeFactors : primeFactorsList) {
                max = Math.max(max, primeFactors.getOrDefault(factor, 0));
            }
            result.put(factor, max);
        }

        return result.keySet()
                .stream()
                .map(factor -> Numbers.pow(factor, result.get(factor)))
                .reduce(1L, (a, b) -> a * b);
    }

    private Map<Integer, Integer> getPrimeFactors(Integer number) {

        List<Integer> primeNumbersUpTo = Numbers.getPrimeNumbersUpTo(number);

        Map<Integer, Integer> result = new HashMap<>();

        while(number > 1) {

            Integer finalNumber = number;
            Integer factor = primeNumbersUpTo
                    .stream()
                    .filter(n -> finalNumber % n == 0)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            number /= factor;
            result.put(factor, 1 + result.getOrDefault(factor, 0));
        }

        return result;
    }

    void parseFile() {

        nodes = new HashMap<>();
        instructions = new ArrayList<>();
        currentNodes = new ArrayList<>();

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

            if (name.endsWith("A")) {
                currentNodes.add(node);
            }

            nodes.put(name, node);
        }
    }

    record Step(int n, Node node, int instructionsIdx) {
        @Override
        public boolean equals(Object o) {
            Step other = (Step) o;
            return this.node.name.equals(other.node.name)
                    && this.instructionsIdx == other.instructionsIdx;

        }
    }

    void findIndex(Node node) {

        List<Step> steps = new ArrayList<>();

        int instructionsIdx = 0;
        int n = 1;

        while (true) {

            if (instructionsIdx > instructions.size() - 1) {
                instructionsIdx = 0;
            }

            Node currentNode = steps.isEmpty() ? node : steps.get(steps.size() - 1).node;

            char direction = instructions.get(instructionsIdx);

            currentNode = direction == 'L'
                    ? nodes.get(currentNode.left)
                    : nodes.get(currentNode.right);

            Step step = new Step(n++, currentNode, instructionsIdx);

            if (steps.contains(step)) {
                break;
            }

            steps.add(step);
            instructionsIdx ++;
        }

        int index = steps.stream()
                .filter(step -> step.node.name.endsWith("Z"))
                .map(Step::n)
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        updateIndexes(index);
    }

    synchronized void updateIndexes(int index) {
        indexes.add(index);
    }

}

