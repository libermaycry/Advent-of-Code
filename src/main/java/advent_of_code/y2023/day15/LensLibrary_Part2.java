package advent_of_code.y2023.day15;

import advent_of_code.y2023.AbstractRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LensLibrary_Part2 extends AbstractRunnable {

    public static void main(String[] args) {
        new LensLibrary_Part2().start();
    }

    @Override
    protected String getPath() {
        return "resources/2023/day15/sequence.txt";
    }

    record Lens (String label, int focalLength) {}

    Map<String, Integer> labelsBox = new HashMap<>();

    Map<Integer, List<Lens>> boxes = new HashMap<>();

    record Step (String label, char operation, Integer focalLength) {}

    @Override
    public void run() {

        List<Step> steps = Arrays.stream(readLines().toList().get(0).split(","))
                .map(this::toStep)
                .toList();

        for (Step step : steps) {

            int boxNumber = labelsBox.getOrDefault(step.label, hash(step.label));
            labelsBox.put(step.label, boxNumber);

            List<Lens> lenses = boxes.getOrDefault(boxNumber, new ArrayList<>());

            if (step.operation == '=') {
                findIndex(lenses, step.label)
                        .ifPresentOrElse(index ->
                            lenses.set(index, new Lens(step.label, step.focalLength))
                        , () ->
                            lenses.add(new Lens(step.label, step.focalLength))
                        );
            } else {
                lenses.removeIf(lens -> lens.label.equals(step.label));
            }

            boxes.put(boxNumber, lenses);
        }

        System.out.println(getFocusingPower());
    }

    int getFocusingPower() {
        AtomicInteger result = new AtomicInteger();
        boxes.forEach((boxNumber, lenses) -> {
            for (int i = 0; i < lenses.size(); i++) {
                result.addAndGet((boxNumber+1) * (i+1) * lenses.get(i).focalLength);
            }
        });
        return result.get();
    }

    Optional<Integer> findIndex(List<Lens> lenses, String label) {
        for (int i = 0; i < lenses.size(); i++) {
            if (lenses.get(i).label.equals(label)) return Optional.of(i);
        }
        return Optional.empty();
    }

    Step toStep (String stepString) {

        String label;
        char operation;
        Integer focalLength = null;

        if (stepString.contains("-")) {
            label = stepString.substring(0, stepString.length()-1);
            operation = '-';
        } else {
            label = stepString.substring(0, stepString.length()-2);
            operation = '=';
            focalLength = Integer.valueOf(String.valueOf(stepString.charAt(stepString.length()-1)));
        }

        return new Step(label, operation, focalLength);
    }

    int hash(String string) {
        int hash = 0;
        for (char c : string.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }
}
