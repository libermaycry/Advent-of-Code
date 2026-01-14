package advent_of_code.y2024.day11;


import advent_of_code.y2024.AbstractRunnable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlutonianPebbles2 extends AbstractRunnable {

    public static void main(String[] args) {
        new PlutonianPebbles2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Integer> stones;
    Map<BigInteger, Map<Integer, BigInteger>> stonesWithBlinks = new HashMap<>();

    @Override
    protected void init() {
        String[] split = readLines().toList().getFirst().split(" ");
        stones = Arrays.stream(split).map(Integer::valueOf).toList();
    }

    @Override
    protected Object run() {
        return stones.stream()
                .map(stone -> blink(new BigInteger(stone.toString()), 75))
                .reduce(BigInteger::add)
                .map(BigInteger::longValue)
                .orElseThrow();
    }
    
    private BigInteger blink(BigInteger stone, int blinks) {

        if (stonesWithBlinks.containsKey(stone) && stonesWithBlinks.get(stone).containsKey(blinks)) {
            return stonesWithBlinks.get(stone).get(blinks);
        }

        if (blinks == 1) {
            if (String.valueOf(stone).length() % 2 == 0) {
                return new BigInteger("2");
            }
            return BigInteger.ONE;
        }

        String stoneString = stone.toString();

        if (stoneString.length() % 2 == 0) {
            String left = stoneString.substring(0, stoneString.length() / 2);
            String right = stoneString.substring(stoneString.length() / 2);

            BigInteger resultLeft = blink(new BigInteger(left), blinks - 1);
            BigInteger resultRight = blink(new BigInteger(right), blinks - 1);
            BigInteger result = resultLeft.add(resultRight);

            Map<Integer, BigInteger> blinksWithResult = stonesWithBlinks.getOrDefault(stone, new HashMap<>());
            blinksWithResult.put(blinks, result);
            stonesWithBlinks.put(stone, blinksWithResult);
            return result;
        }

        if (stone.equals(BigInteger.ZERO))
            return blink(BigInteger.ONE, blinks - 1);

        return blink(stone.multiply(new BigInteger("2024")), blinks - 1);
    }

}
