package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;

public class Binary {

    public enum Port { AND, OR, XOR }

    public static int apply(Port port, int bit1, int bit2) {
        return switch (port) {
            case AND -> bit1 == 1 && bit2 == 1;
            case OR -> bit1 == 1 || bit2 == 1;
            case XOR -> bit1 != bit2;
        } ? 1 : 0;
    }

    public static List<Integer> apply(Port port, List<Integer> bits1, List<Integer> bits2) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < bits1.size(); i++) {
            result.add(apply(port, bits1.get(i), bits2.get(i)));
        }
        return result;
    }

    public static List<Integer> toBinary(long n) {
        List<Integer> binary = new ArrayList<>();
        if (n == 0) {
            binary.add(0);
            return binary;
        }
        while (n > 0) {
            binary.add((int)(n % 2));
            n /= 2;
        }
        return binary.reversed();
    }

    public static long toDecimal(List<Integer> binary) {
        StringBuilder sb = new StringBuilder();
        binary.forEach(sb::append);
        return Long.parseLong(sb.toString(),2);
    }

    public static long xor(long n1, long n2) {
        List<Integer> bits1 = toBinary(n1);
        List<Integer> bits2 = toBinary(n2);
        padSameSize(bits1, bits2);
        return toDecimal(apply(Port.XOR, bits1, bits2));
    }

    public static void padSameSize(List<Integer> bits1, List<Integer> bits2) {
        int diffSize = Math.abs(bits1.size() - bits2.size());
        if (diffSize > 0) {
            for (int i = 0; i < diffSize; i++) {
                if (bits1.size() < bits2.size()) bits1.addFirst(0);
                else bits2.addFirst(0);
            }
        }
    }
}
