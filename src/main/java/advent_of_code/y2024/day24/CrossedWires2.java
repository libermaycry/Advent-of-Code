package advent_of_code.y2024.day24;


import advent_of_code.utils.Binary;
import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

import static advent_of_code.utils.Binary.padSameSize;
import static advent_of_code.utils.Binary.toBinary;

public class CrossedWires2 extends AbstractRunnable {

    public static void main(String[] args) {
        new CrossedWires2().start();
    }

    class Gate {
        Binary.Port port;
        String inputWire1;
        String inputWire2;
        String outputWire;
    }

    int N_BITS_RESULT = 45;
    long MAX_45_BIT = 35184372088831L;

    Map<String, Integer> wires = new HashMap<>();
    List<Gate> gates = new ArrayList<>();
    List<List<String>> blocks;
    List<Integer> _45bits = new ArrayList<>();

    long x, y;

    @Override
    protected String source() { return "input.txt"; }

    @Override
    protected void init() {
        for (int i = 0; i < 45; i++) _45bits.add(1);
        blocks = readBlocksOfLines();
        restartGates();
    }

    private void restartGates() {
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

        List<String> wrong1And2 = gates.stream()
                .filter(gate -> !cond1Respected(gate) || !cond2Respected(gate))
                .map(gate -> gate.outputWire)
                .toList();

        List<String> wrong3 = gates.stream()
                .filter(gate -> !wrong1And2.contains(gate.outputWire))
                .filter(this::faultyXOR)
                .map(g -> g.outputWire)
                .filter(w -> !w.equals("z00"))
                .toList();

        List<String> wrong4 = gates.stream()
                .filter(gate -> !wrong1And2.contains(gate.outputWire))
                .filter(this::faultyAND)
                .map(g -> g.outputWire)
                .filter(w -> !w.equals("z00"))
                .toList();

        List<String> result = new ArrayList<>();
        result.addAll(wrong1And2);
        result.addAll(wrong3);
        result.addAll(wrong4);
        Collections.sort(result);

        return String.join(",", result);
    }

    private boolean faultyXOR(Gate gate) {
        if (gate.port.equals(Binary.Port.XOR)
                && isInput(gate.inputWire1)
                && isInput(gate.inputWire2)
                && !gate.inputWire1.contains("00")
                && !gate.inputWire2.contains("00")) {
            List<Gate> xors = gates.stream().filter(g -> g.port.equals(Binary.Port.XOR)).toList();
            return xors.stream().noneMatch(g -> g.inputWire1.equals(gate.outputWire) || g.inputWire2.equals(gate.outputWire));
        }
        return false;
    }

    private boolean faultyAND(Gate gate) {
        if (gate.port.equals(Binary.Port.AND)
                && isInput(gate.inputWire1)
                && isInput(gate.inputWire2)
                && !gate.inputWire1.contains("00")
                && !gate.inputWire2.contains("00")) {
            List<Gate> ors = gates.stream().filter(g -> g.port.equals(Binary.Port.OR)).toList();
            return ors.stream().noneMatch(g -> g.inputWire1.equals(gate.outputWire) || g.inputWire2.equals(gate.outputWire));
        }
        return false;

    }

    private boolean cond1Respected(Gate gate) {
        if (!gate.outputWire.startsWith("z") || gate.outputWire.equals("z45"))
            return true;
        return gate.port.equals(Binary.Port.XOR);
    }

    private boolean cond2Respected(Gate gate) {
        if (gate.outputWire.startsWith("z"))
            return true;
        if (isInput(gate.inputWire1) || isInput(gate.inputWire2))
            return true;
        return gate.port.equals(Binary.Port.AND) || gate.port.equals(Binary.Port.OR);
    }

    private boolean isInput(String wire) {
        return wire.startsWith("x") || wire.startsWith("y");
    }

    private List<String> wiresInvolved(String wire) {

        List<String> involved = new ArrayList<>();

        Gate gate = gates.stream().filter(g -> g.outputWire.equals(wire)).findFirst().orElseThrow();

        if (!gate.inputWire1.startsWith("x") && !gate.inputWire1.startsWith("y")) {
            involved.add(gate.inputWire1);
            involved.addAll(wiresInvolved(gate.inputWire1));
        }

        if (!gate.inputWire2.startsWith("x") && !gate.inputWire2.startsWith("y")) {
            involved.add(gate.inputWire2);
            involved.addAll(wiresInvolved(gate.inputWire2));
        }

        return new HashSet<>(involved).stream().toList();
    }

    private void swap(int b1, int b2) {
        String w1 = b1 < 10 ? "z0" + b1 : "z" + b1;
        String w2 = b2 < 10 ? "z0" + b2 : "z" + b2;
        Gate g1 = gates.stream().filter(g -> g.outputWire.equals(w1)).findFirst().orElseThrow();
        Gate g2 = gates.stream().filter(g -> g.outputWire.equals(w2)).findFirst().orElseThrow();
        g1.outputWire = w2;
        g2.outputWire = w1;
    }

    private void swap(Gate g1, Gate g2) {
        String w1 = g1.outputWire;
        g1.outputWire = g2.outputWire;
        g2.outputWire = w1;
    }

    private String toZWireName(int bit) {
        return bit < 10 ? "z0" + bit : "z" + bit;
    }

    private List<Integer> wrongBits() {
        List<Integer> bitsExpected = toBinary(x + y);
        List<Integer> bitsSum = toBinary(apply());
        padSameSize(bitsExpected, bitsSum);
        return wrongBits(bitsSum, bitsExpected);
    }

    private List<Integer> wrongBits(List<Integer> bits, List<Integer> expected) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < bits.size(); i++) {
            if (!bits.get(i).equals(expected.get(i))) {
                indexes.add(bits.size() - i - 1);
            }
        }
        return indexes;
    }

    private void restart() {
        restartWires();
    }

    private void restartWires() {
        wires = new HashMap<>();
        initializeX();
        initializeY();
    }

    private void initializeX() {
        List<Integer> bits = toBinary(x);
        Binary.padSameSize(bits, _45bits);
        for (int i = 0; i < bits.size(); i++) {
            int idx = 44 - i;
            String nString = idx < 10 ? "0"+idx : ""+idx;
            wires.put("x"+nString, bits.get(i));
        }
    }

    private void initializeY() {
        List<Integer> bits = toBinary(y);
        Binary.padSameSize(bits, _45bits);
        for (int i = 0; i < bits.size(); i++) {
            int idx = 44 - i;
            String nString = idx < 10 ? "0"+idx : ""+idx;
            wires.put("y"+nString, bits.get(i));
        }
    }

    private long apply() {
        restart();
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
