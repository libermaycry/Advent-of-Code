package advent_of_code.y2024.day22;


import advent_of_code.utils.Binary;
import advent_of_code.utils.Threads;
import advent_of_code.y2024.AbstractRunnable;

import java.util.List;

public class MonkeyMarket1 extends AbstractRunnable {

    public static void main(String[] args) {
        new MonkeyMarket1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Long> secrets;

    @Override
    protected void init() {
        secrets = readLines().map(Long::valueOf).toList();
    }

    long result = 0;

    @Override
    protected Object run() {
        for (Long secret : secrets) {
            Threads.add(threads, () -> process(secret));
        }
        Threads.waitFinish(threads);
        return result;
    }

    private void process(Long secret) {
        for (int i = 0; i < 2000; i++) {
            secret = next(secret);
        }
        addToResult(secret);
    }

    synchronized void addToResult(long value) {
        result += value;
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
