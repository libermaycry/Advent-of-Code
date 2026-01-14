package advent_of_code.y2025.day11;


import advent_of_code.y2025.AbstractRunnable;

import java.util.*;

public class Reactor2 extends AbstractRunnable {

    public static void main(String[] args) {
        new Reactor2().start();
    }

    Map<String, List<String>> map = new HashMap<>();
//    Map<String, Long> cache = new HashMap<>();

    @Override
//    protected String source() { return "test.txt"; }
    protected String source() { return "input.txt"; }

    @Override
    protected void init() {
        readLines().forEach(line -> {
            String[] split = line.split(":");
            String[] outputs = split[1].trim().split(" ");
            map.put(split[0], Arrays.asList(outputs));
        });
    }

//    class Path {
//        List<String> nodes;
//        boolean dacVisited;
//        boolean fftVisited;
//        public Path(List<String> nodes, boolean dacVisited, boolean fftVisited) {
//            this.nodes = nodes;
//            this.dacVisited = dacVisited;
//            this.fftVisited = fftVisited;
//        }
//    }

    @Override
    protected Object run() {
        return count("dac", "out")
                * count("fft", "dac")
                * count("svr", "fft");
    }

    private long count(String node, String destination) {
        return count(node, destination, new HashMap<>());
    }

    private long count(String node, String destination, Map<String, Long> cache) {

        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        List<String> outputs = Optional
                .ofNullable(map.get(node))
                .orElse(Collections.emptyList());

        if (outputs.contains(destination)) {
            return 1;
        }

        long result = 0;
        for (String output : outputs) {
            long count = count(output, destination, cache);
            cache.put(output, count);
            result += count;
        }
        return result;
    }

//    private long count(List<Path> paths, String node, Path currentPath) {
//
//        if (currentPath.nodes.contains(node)) {
//            System.out.println(node);
//        }
//
//        currentPath.nodes.add(node);
//
//        if (node.equals("dac")) {
//            currentPath.dacVisited = true;
//        }
//
//        if (node.equals("fft")) {
//            currentPath.fftVisited = true;
//        }
//
//        if (node.equals("out")) {
//            if (currentPath.dacVisited && currentPath.fftVisited) {
//                return 1;
//            }
//            return 0;
//        }
//
//        long result = 0;
//        for (String output : map.get(node)) {
//            long count = count(paths, output,
//                    new Path(new ArrayList<>(currentPath.nodes), currentPath.dacVisited, currentPath.fftVisited));
//            result += count;
//        }
//        return result;
//    }
//    private List<Path> visit(List<Path> paths, String node, Path currentPath) {
//
//        currentPath.nodes.add(node);
//
//        if (node.equals("dac")) {
//            currentPath.dacVisited = true;
//        }
//
//        if (node.equals("fft")) {
//            currentPath.fftVisited = true;
//        }
//
//        if (node.equals("out")) {
//            if (currentPath.dacVisited && currentPath.fftVisited) {
//                paths.add(currentPath);
//            }
//            return paths;
//        }
//
//        for (String output : map.get(node)) {
//            visit(paths, output, new Path(
//                    new ArrayList<>(currentPath.nodes), currentPath.dacVisited, currentPath.fftVisited));
//        }
//        return paths;
//    }

}
