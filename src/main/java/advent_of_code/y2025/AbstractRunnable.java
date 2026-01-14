package advent_of_code.y2025;

import advent_of_code.utils.Files;
import advent_of_code.utils.Threads;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractRunnable {

    protected abstract String source();
    protected abstract Object run();
    protected void init() {}

    protected Stream<String> readLines() {
        return Files.readLines("src/main/resources/2025/day%s/%s".formatted(day(), source()));
    }

    private int day() {
        return Arrays.stream(this.getClass().getName().split("\\."))
                .filter(n -> n.contains("day"))
                .map(n -> n.replace("day", ""))
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow();
    }

    public void start() {
        System.out.println("\nstart " + this.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        init();
        Object result = run();
        System.out.println("result: " + result);
        System.out.println("duration: " + printMillis(System.currentTimeMillis() - start));
    }

    private static String printMillis(long millis) {
        return Duration.ofMillis(millis).toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    protected Map<Integer, String> readLinesToMap() {
        Map<Integer, String> map = new HashMap<>();
        List<String> lines = readLines().toList();
        IntStream.range(0, lines.size()).forEach(index -> map.put(index+1, lines.get(index)));
        return map;
    }

    protected List<List<String>> readBlocksOfLines() {
        List<List<String>> blocks = new ArrayList<>();
        List<String> block = new ArrayList<>();
        for (String line : readLines().toList()) {
            if (line.isEmpty()) {
                blocks.add(block);
                block = new ArrayList<>();
            } else {
                block.add(line);
            }
        }
        blocks.add(block);
        return blocks;
    }

    protected char[][] readLinesToMatrix() {
        return readLinesToMatrix(readLines().toList());
    }

    protected char[][] readLinesToMatrix(List<String> lines) {
        char[][] matrix = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }
        return matrix;
    }

    protected List<String> extractFromRegex(String string, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(string);
        List<String> result = new ArrayList<>();
        while (matcher.find()) result.add(matcher.group());
        return result;
    }

    // Threads
    protected List<Thread> threads = Threads.newList();

    protected void addThread(Runnable runnable) {
        Threads.add(threads, runnable);
    }
    protected void waitThreads() {
        Threads.waitFinish(threads);
    }
}
