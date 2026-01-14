package advent_of_code.y2023.day12;


import advent_of_code.y2023.AbstractRunnable;
import advent_of_code.utils.Strings;

import java.util.*;

public class HotSprings_v2 extends AbstractRunnable {

    public static void main(String[] args) {
        new HotSprings_v2().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day12/records.txt";
    }

    record Record (List<Character> characters, List<Integer> condition) {}

    List<Record> records;


    @Override
    public void run() {
        parseFile();
        System.out.println(records.stream().mapToInt(this::getArrangements).sum());
    }

    int getArrangements(Record record) {

        List<List<Character>> arrangements = apply(record.condition.get(0), record.characters);

        for (int i = 1; i < record.condition.size(); i++) {

            int group = record.condition.get(i);
            int I = i;
            List<List<Character>> newArrangements = arrangements.stream()
                    .map(arrangement -> {
                        List<List<Character>> applied = apply(group, arrangement);
                        if (I == record.condition.size() - 1) {
                            applied = applied.stream().filter(a -> toGroupsCondition(a).equals(record.condition)).toList();
                        }
                        if (!applied.isEmpty()) {
                            return applied;
                        }
                        return List.of(arrangement);
                    })
                    .flatMap(Collection::stream)
                    .distinct()
                    .filter(x -> {
                        List<Integer> groupsCondition = toGroupsCondition(x);
                        for (int j = 0; j < I; j++) {
                            if (groupsCondition.size() <= j) {
                                return false;
                            }
                            if (groupsCondition.get(j) > record.condition.get(j)) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .toList();

//            for (List<Character> chars : newArrangements) {
//                Integer groupsCondition = toGroupsCondition(chars).get(i);
//            }

            arrangements = new ArrayList<>(newArrangements);
        }

        List<List<Character>> result = arrangements.stream().filter(a -> toGroupsCondition(a).equals(record.condition)).toList();
        return result.size();
    }

    private List<List<Character>> apply(int groupToPlace, List<Character> characters) {

        List<Group> superGroups = getSuperGroups(characters)
                .stream().filter(g -> g.end - g.start + 1 >= groupToPlace).toList();

        List<List<Character>> applied = new ArrayList<>();

        for (Group group : superGroups) {

            int cursor = group.start;

            while (cursor + groupToPlace - 1 <= group.end) {

                List<Character> newChars = new ArrayList<>(characters);

                for (int i = cursor; i < cursor + groupToPlace; i++) {
                    newChars.set(i, '#');
                }

                if (!applied.contains(newChars)) {
                    applied.add(newChars);
                }
                cursor ++;
            }

        }

        return applied;
    }

    class Group {int start; int end;}

    List<Group> getSuperGroups(List<Character> chars) {
        List<Group> groups = new ArrayList<>();
        Group group = null;
        for (int i = 0; i < chars.size(); i++) {
            if (!List.of('?', '#').contains(chars.get(i))) {
                if (group != null) {
                    group.end = i-1;
                    groups.add(group);
                    group = null;
                }
            } else {
                if (group == null) {
                    group = new Group();
                    group.start = i;
                }
            }
        }
        if (group != null) {
            group.end = chars.size() - 1;
            groups.add(group);
        }
        return groups;
    }

    List<Integer> toGroupsCondition(List<Character> attempt) {
        List<Integer> arrangement = new ArrayList<>();
        Integer startGroup = null;
        for (int i = 0; i < attempt.size(); i++) {
            if (attempt.get(i) != '#') {
                if (startGroup != null) {
                    arrangement.add(i - startGroup);
                    startGroup = null;
                }
            } else {
                if (startGroup == null) {
                    startGroup = i;
                }
            }
        }
        if (startGroup != null) {
            arrangement.add(attempt.size() - startGroup);
        }
        return arrangement;
    }

    void parseFile() {
        records = new ArrayList<>();
        for (String line : readLines().toList()) {
            String left = line.split(" ")[0];
            left = left + "?" + left + "?" + left + "?" + left + "?" + left;

            String right = line.split(" ")[1];
            right = right + "," + right + "," + right + "," + right + "," + right;

            List<Character> characters = new ArrayList<>();
            for (char c : left.toCharArray()) characters.add(c);

            List<Integer> condition = new ArrayList<>();

            for (String n : right.split(",")) {
                if(Strings.isBlank(n)) continue;
                condition.add(Integer.parseInt(n.trim()));
            }

            records.add(new Record(characters, condition));
        }
    }
}

