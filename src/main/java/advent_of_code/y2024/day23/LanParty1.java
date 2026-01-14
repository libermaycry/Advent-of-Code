package advent_of_code.y2024.day23;


import advent_of_code.y2024.AbstractRunnable;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanParty1 extends AbstractRunnable {

    public static void main(String[] args) {
        new LanParty1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Map<String, List<String>> network = new HashMap<>();

    @Override
    protected void init() {
        readLines().forEach(line -> {
            String[] split = line.split("-");
            String first = split[0];
            String second = split[1];
            List<String> list = network.getOrDefault(first, new ArrayList<>());
            list.add(second);
            network.put(first, list);
            list = network.getOrDefault(second, new ArrayList<>());
            list.add(first);
            network.put(second, list);
        });
    }

    @Override
    protected Object run() {
        return findGroups().stream()
                .filter(group -> group.stream().anyMatch(c -> c.startsWith("t")))
                .count();
    }

    protected List<List<String>> findGroups() {
        List<List<String>> groups = new ArrayList<>();
        network.keySet().forEach(c1 -> {
            List<String> connections = network.get(c1);
            for (int i = 0; i < connections.size() - 1; i++) {
                String c2 = connections.get(i);
                for (int j = i + 1; j < connections.size(); j++) {
                    String c3 = connections.get(j);
                    if (network.get(c2).contains(c3)) {
                        List<String> group = List.of(c1, c2, c3);
                        if (groups.stream().noneMatch(g -> CollectionUtils.isEqualCollection(g, group))) {
                            groups.add(group);
                        }
                    }
                }
            }
        });
        return groups;
    }
}
