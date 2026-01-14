package advent_of_code.y2025.day6;


import advent_of_code.utils.Matrix;
import advent_of_code.utils.Pair;
import advent_of_code.utils.Strings;
import advent_of_code.y2025.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;

public class TrashCompactor2 extends AbstractRunnable {

    public static void main(String[] args) {
        new TrashCompactor2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Matrix matrix;
    List<Pair<String, Integer>> operationsWithIndex;

    @Override
    protected void init() {
        matrix = new Matrix(readLinesToMatrix());
        operationsWithIndex = new ArrayList<>();
        for (int j = 0; j < matrix.get(0).length; j++) {
            char c = matrix.get(matrix.length() - 1, j);
            if (c != '.') {
                operationsWithIndex.add(Pair.of(String.valueOf(c), j));
            }
        }
    }

    @Override
    protected Object run() {
        long result = 0;
        for (int opIndex = 0; opIndex < operationsWithIndex.size(); opIndex++) {
            Pair<String, Integer> opWithIndex = operationsWithIndex.get(opIndex);
            int startJ = opWithIndex.right;
            int endJ = opIndex < operationsWithIndex.size() - 1
                    ? operationsWithIndex.get(opIndex + 1).right
                    : matrix.get(0).length;
            List<Long> operationValues = new ArrayList<>();
            for (int j = startJ; j < endJ; j++) {
                String number = "";
                for (int i = 0; i < matrix.length() - 1; i++) {
                    char c = matrix.get(i,j);
                    if (c != '.') {
                        number = number.concat(String.valueOf(c));
                    }
                }
                if (!Strings.isBlank(number)) {
                    operationValues.add(Long.valueOf(number));
                }
            }
            result += doOperation(operationValues, opWithIndex.left);
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
