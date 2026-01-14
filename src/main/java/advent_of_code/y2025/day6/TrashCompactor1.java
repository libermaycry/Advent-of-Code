package advent_of_code.y2025.day6;


import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrashCompactor1 extends AbstractRunnable {

    public static void main(String[] args) {
        new TrashCompactor1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<List<Long>> listOfValues;
    List<String> operations;

    @Override
    protected void init() {
        listOfValues = new ArrayList<>();
        operations = new ArrayList<>();
        List<String> lines = readLines().toList();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (i < lines.size() - 1) {
                List<Long> values = new ArrayList<>();
                for (String v : line.trim().split("\\s+")) {
                    values.add(Long.valueOf(v));
                }
                listOfValues.add(values);
            } else {
                operations.addAll(Arrays.asList(line.trim().split("\\s+")));
            }
        }
    }

    @Override
    protected Object run() {
        long result = 0;
        for (int col = 0; col < operations.size(); col++) {
            List<Long> operationValues = new ArrayList<>();
            for (List<Long> listOfValue : listOfValues) {
                operationValues.add(listOfValue.get(col));
            }
            result += doOperation(operationValues, operations.get(col));

        }

        return result;
    }

    private long doOperation(List<Long> operationValues, String operation) {
        long result = operation.equals("*") ? 1 : 0;
        for (Long val : operationValues) {
            result = operation.equals("*")
                    ? result * val
                    : result + val;
        }
        return result;
    }

}
