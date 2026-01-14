package advent_of_code.y2024.day23;


import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class LanParty2 extends AbstractRunnable {

    public static void main(String[] args) {
        new LanParty2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Map<String, List<String>> network = new HashMap<>();
    List<Group> groups = new ArrayList<>();

    class Group {
        List<String> computers;
        public Group() {
            this.computers = new ArrayList<>();
        }
        void add(String computer) {
            this.computers.add(computer);
            Collections.sort(this.computers);
        }
    }

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

            Group group = new Group();
            group.add(first);
            group.add(second);
            groups.add(group);
        });
    }

    @Override
    protected Object run() {

        findGroups();

        int maxSize = groups.stream()
                .map(g -> g.computers.size())
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();

        Group lanParty = groups.stream()
                .filter(g -> g.computers.size() == maxSize)
                .findFirst()
                .orElseThrow();

        return String.join(",", lanParty.computers);
    }

    private void findGroups() {
        network.keySet().forEach(computer -> {
            HashSet<String> connections = new HashSet<>(network.get(computer));
            for (Group group : groups) {
                if (!group.computers.contains(computer) && connections.containsAll(group.computers)) {
                    group.add(computer);
                }
            }
            removeDuplicatedGroups();
        });
    }

    private void removeDuplicatedGroups() {
        List<Group> newGroups = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        groups.forEach(group -> {
            if (!set.contains(group.toString())) {
                newGroups.add(group);
                set.add(group.toString());
            }
        });
        groups = newGroups;
    }
}
