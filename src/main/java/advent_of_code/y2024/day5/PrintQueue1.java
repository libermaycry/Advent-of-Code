package advent_of_code.y2024.day5;


import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class PrintQueue1 extends AbstractRunnable {

    public static void main(String[] args) {
        new PrintQueue1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Map<Integer, List<Integer>> rules;
    List<List<Integer>> pagesToProduce;

    @Override
    protected void init() {
        rules = new HashMap<>();
        List<String> lines = readLines().toList();
        List<String> rulesLines = lines.stream().takeWhile(line -> !line.isEmpty()).toList();
        rulesLines.forEach(rule -> {
            String[] split = rule.split("\\|");
            int left = Integer.parseInt(split[0]);
            int right = Integer.parseInt(split[1]);
            List<Integer> list = rules.getOrDefault(left, new ArrayList<>());
            list.add(right);
            rules.put(left, list);
        });

        pagesToProduce = new ArrayList<>();
        lines.stream().skip(rulesLines.size() + 1)
                .forEach(line -> {
                    pagesToProduce.add(Arrays.stream(line.split(","))
                            .map(Integer::parseInt)
                            .toList());
                });
    }

    @Override
    protected Object run() {
        return pagesToProduce.stream()
                .filter(pages -> pages.stream().allMatch(page -> isOrdered(page, pages)))
                .map(pages -> pages.get(pages.size()/2))
                .mapToInt(x -> x)
                .sum();
    }

    private boolean isOrdered(int page, List<Integer> pages) {
        List<Integer> after = pages.stream().skip(pages.indexOf(page) + 1).toList();
        return new HashSet<>(rules.getOrDefault(page, List.of())).containsAll(after);
    }


}
