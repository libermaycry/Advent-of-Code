package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;

public class Numbers {

    public static boolean isPrime(long n) {

        if(n <= 1) return false;

        for (int i = 2; i <= n/2; i++){
            if (n % i == 0) return false;
        }

        return true;
    }

    public static List<Integer> getPrimeNumbersUpTo(long n) {

        List<Integer> list = new ArrayList<>();

        for(int i = 2; i < n; i++) {
            if(isPrime(i)) list.add(i);
        }

        return list;
    }

    public static long pow(long b, long e) {
        return Double.valueOf(Math.pow(b, e)).longValue();
    }

    public static int fromChar(char c) {
        return Character.getNumericValue(c);
    }
}
