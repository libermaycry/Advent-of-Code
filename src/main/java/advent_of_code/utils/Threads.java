package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;

public class Threads {

    public static void waitFinish(List<Thread> threads) {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("finished all threads " + threads.size());
    }

    public static List<Thread> newList() {
        return new ArrayList<>();
    }

    public static void add(List<Thread> threads, Runnable runnable) {
        threads.add(Thread.startVirtualThread(runnable));
    }
}
