package advent_of_code.y2022.day7;

import advent_of_code.y2023.AbstractRunnable;

import java.util.*;

public class NoSpaceOnDevice_Part1 extends AbstractRunnable {

    public static void main(String[] args) {
        new NoSpaceOnDevice_Part1().run();
    }

    @Override
    protected String getPath() {
        return "resources/2022/day7/terminal_output.txt";
    }

    record File (String name, int size) {}

    record Directory (String name, List<File> files, List<Directory> directories) {}

    @Override
    public void run() {

        List<Directory> directories = new ArrayList<>();
        Directory root = new Directory("/", new ArrayList<>(), new ArrayList<>());
        directories.add(root);

        Directory currentDir = root;

        Stack<Directory> path = new Stack<>();
        path.push(currentDir);

        for (String line : readLines().toList()) {

            if (line.startsWith("$")) {

                if (line.contains("cd")) {

                    if (line.endsWith("..")) {
                        path.pop();
                        currentDir = path.lastElement();
                    } else {
                        String changedDir = line.replace("$ cd", "").trim();
                        currentDir = currentDir.directories.stream()
                                .filter(directory -> changedDir.equals(directory.name)).findFirst().get();
                        path.push(currentDir);
                        directories.add(currentDir);
                    }
                }

            } else {

                if (line.startsWith("dir")) {
                    String dir = line.replace("dir", "").trim();
                    currentDir.directories.add(new Directory(dir, new ArrayList<>(), new ArrayList<>()));
                } else {
                    String[] split = line.split(" ");
                    currentDir.files.add(new File(split[1], Integer.parseInt(split[0])));
                }
            }
        }

        int result = directories.stream()
                .map(this::getSize)
                .filter(size -> size < 100_000)
                .reduce(0, Integer::sum);

        System.out.println(result);
    }

    private Integer getSize(Directory directory) {

        int sumFiles = directory.files.stream()
                .map(File::size)
                .reduce(Integer::sum)
                .orElse(0);

        int sumDirs = directory.directories.stream()
                .map(this::getSize)
                .reduce(Integer::sum)
                .orElse(0);

        return sumFiles + sumDirs;
    }

}
