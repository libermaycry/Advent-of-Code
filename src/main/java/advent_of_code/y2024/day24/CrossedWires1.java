package advent_of_code.y2024.day24;


import advent_of_code.utils.Binary;
import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class CrossedWires1 extends AbstractRunnable {

    public static void main(String[] args) {
        new CrossedWires1().start();
    }

    class Gate {
        Binary.Port port;
        String inputWire1;
        String inputWire2;
        String outputWire;
    }

    Map<String, Integer> wires = new HashMap<>();
    List<Gate> gates = new ArrayList<>();

    @Override
    protected String source() { return "input.txt"; }

    @Override
    protected void init() {
        List<List<String>> blocks = readBlocksOfLines();
        blocks.getFirst().forEach(line -> {
            String[] split = line.split(":");
            wires.put(split[0], Integer.parseInt(split[1].trim()));
        });
        blocks.getLast().forEach(line -> {
            String[] splitArrow = line.split("->");
            String[] splitSpace = splitArrow[0].trim().split(" ");
            Gate gate = new Gate();
            gate.inputWire1 = splitSpace[0];
            gate.inputWire2 = splitSpace[2];
            gate.port = Binary.Port.valueOf(splitSpace[1]);
            gate.outputWire = splitArrow[1].trim();
            gates.add(gate);
        });
    }

    @Override
    protected Object run() {

        do {
            gates.forEach(gate -> {
                Integer bit1 = wires.get(gate.inputWire1);
                Integer bit2 = wires.get(gate.inputWire2);
                if (bit1 != null && bit2 != null) {
                    wires.put(gate.outputWire, Binary.apply(gate.port, bit1, bit2));
                }
            });
        } while(gates.stream().anyMatch(gate -> wires.get(gate.outputWire) == null));

        List<String> zWires = new ArrayList<>(wires.keySet().stream()
                .filter(wire -> wire.startsWith("z"))
                .toList());
        Collections.sort(zWires);

        List<Integer> binary = new ArrayList<>();
        for (int i = zWires.size() - 1; i >= 0; i--) {
            binary.add(wires.get(zWires.get(i)));
        }

        return Binary.toDecimal(binary);
    }
}
