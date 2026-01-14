package advent_of_code.y2025.day8;


import advent_of_code.utils.Numbers;
import advent_of_code.utils.Pair;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playground1 extends AbstractRunnable {

    public static void main(String[] args) {
        new Playground1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    static final int DISTANCES_SIZE = 1000;

    record Junction (int x, int y, int z) {}

    record Distance (double value, Pair<Junction, Junction> pair) {}

    record Circuit (List<Junction> junctions) {}

    List<Junction> juncs;
    List<Distance> distances;
    List<Circuit> circuits;

    @Override
    protected void init() {
        juncs = new ArrayList<>();
        distances = new ArrayList<>();
        circuits = new ArrayList<>();
        readLines().forEach(line -> {
            String[] split = line.split(",");
            Junction j = new Junction(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            juncs.add(j);
            List<Junction> singleList = new ArrayList<>();
            singleList.add(j);
            circuits.add(new Circuit(singleList));
        });
        for (int i = 0; i < juncs.size() - 1; i++) {
            for (int j = i + 1; j < juncs.size(); j++) {
                Junction j1 = juncs.get(i);
                Junction j2 = juncs.get(j);
                distances.add(new Distance(dist(j1,j2), Pair.of(j1,j2)));
            }
        }
        distances.sort(Comparator.comparing(Distance::value));
        distances = distances.subList(0, DISTANCES_SIZE);
    }

    @Override
    protected Object run() {
        for (Distance distance : distances) {
            boolean sameCircuit = circuits.stream().anyMatch(
                    circuit -> circuit.junctions.contains(distance.pair.left)
                            && circuit.junctions.contains(distance.pair.right));
            if (sameCircuit) continue;
            Circuit c1 = circuits.stream()
                    .filter(circuit -> circuit.junctions.contains(distance.pair.left))
                    .findFirst()
                    .orElseThrow();
            Circuit c2 = circuits.stream()
                    .filter(circuit -> circuit.junctions.contains(distance.pair.right))
                    .findFirst()
                    .orElseThrow();
            circuits.remove(c1);
            circuits.remove(c2);
            List<Junction> merged = new ArrayList<>(c1.junctions);
            merged.addAll(c2.junctions);
            circuits.add(new Circuit(merged));
        }
        circuits.sort(Comparator.comparingInt(c -> c.junctions().size()));
        circuits = circuits.reversed();
        return circuits.subList(0, 3)
                .stream()
                .map(circuit -> circuit.junctions.size())
                .reduce((subtotal, size) -> subtotal * size)
                .orElseThrow();
    }

    private double dist(Junction j1, Junction j2) {
        return Math.sqrt(Numbers.pow(j1.x - j2.x, 2)
                        + Numbers.pow(j1.y - j2.y, 2)
                        + Numbers.pow(j1.z - j2.z, 2));
    }

}
