package advent_of_code.y2024.day22;


import advent_of_code.utils.Binary;
import advent_of_code.utils.Numbers;
import advent_of_code.utils.Threads;
import advent_of_code.y2024.AbstractRunnable;

import java.util.*;

public class MonkeyMarket2 extends AbstractRunnable {

    public static void main(String[] args) {
        new MonkeyMarket2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    class Buyer {

        List<Integer> swings;
        Map<String, Integer> sequencesWithPrice;

        public Buyer(List<Integer> prices) {
            this.swings = new ArrayList<>();
            for (int i = 1; i < prices.size(); i++) {
                swings.add(prices.get(i) - prices.get(i - 1));
            }
            this.sequencesWithPrice = new HashMap<>();
            for (int i = 0; i < swings.size() - 4; i++) {
                List<Integer> sequence = List.of(swings.get(i), swings.get(i + 1), swings.get(i + 2), swings.get(i + 3));
                if (!sequencesWithPrice.containsKey(sequence.toString())) {
                    sequencesWithPrice.put(sequence.toString(), prices.get(i+4));
                }
            }
        }
    }

    List<Buyer> buyers;
    final Map<String, Long> sequencesWithSum = new HashMap<>();
    long result = -1;

    @Override
    protected void init() {
        buyers = new ArrayList<>();
        readLines()
                .map(Long::valueOf)
                .forEach(secret -> Threads.add(threads, () -> initBuyer(secret)));
        Threads.waitFinish(threads);
    }

    @Override
    protected Object run() {
        buyers.forEach(buyer -> Threads.add(threads, () -> check(buyer)));
        Threads.waitFinish(threads);
        return result;
    }

    private void check(Buyer buyer) {
        for (int i = 0; i < buyer.swings.size() - 4; i++) {
            List<Integer> sequence = List.of(
                    buyer.swings.get(i),
                    buyer.swings.get(i + 1),
                    buyer.swings.get(i + 2),
                    buyer.swings.get(i + 3));
            updateResult(getSumForSequence(sequence));
        }
    }

    private long getSumForSequence(List<Integer> sequence) {
        if (sequencesWithSum.containsKey(sequence.toString())) {
            return sequencesWithSum.get(sequence.toString());
        }
        long sum = 0;
        for (Buyer buyer : buyers) {
            sum += buyer.sequencesWithPrice.getOrDefault(sequence.toString(), 0);
        }
        synchronized (sequencesWithSum) {
            sequencesWithSum.put(sequence.toString(), sum);
        }
        return sum;
    }

    private synchronized void updateResult(long result) {
        this.result = Math.max(this.result, result);
    }

    private void initBuyer(Long secret) {
        List<Integer> prices = new ArrayList<>();
        prices.add(lastDigit(secret));
        for (int i = 0; i < 2000; i++) {
            secret = next(secret);
            prices.add(lastDigit(secret));
        }
        addBuyer(prices);
    }

    private synchronized void addBuyer(List<Integer> prices) {
        buyers.add(new Buyer(prices));
    }

    private Integer lastDigit(Long n) {
        return Numbers.fromChar(n.toString().charAt(n.toString().length() - 1));
    }

    private long next(long secret) {
        secret = prune(mix(secret * 64, secret));
        secret = prune(mix((long) Math.floor((double) secret / 32), secret));
        secret = prune(mix(secret*2048, secret));
        return secret;

    }

    private long mix(long value, long secret) {
        return Binary.xor(value, secret);
    }

    private long prune(long value) {
        return value % 16777216;
    }
}
