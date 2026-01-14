package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public static <T> List<List<T>> of(List<T> values, int n) {

        if (n == 1) {
            List<List<T>> result = new ArrayList<>();
            values.forEach(value -> result.add(List.of(value)));
            return result;
        }

        List<List<T>> result = new ArrayList<>();
        values.forEach(value ->
                Permutations.of(values, n - 1).forEach(perm -> {
                    List<T> newPerm = new ArrayList<>(perm);
                    newPerm.addFirst(value);
                    result.add(newPerm);
        }));
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Permutations.of(List.of("a", "b", "c", "d", "e"), 3));
    }
}
