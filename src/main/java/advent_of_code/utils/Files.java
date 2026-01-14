package advent_of_code.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Files {

    public static Stream<String> readLines(String path) {
        try {
            return java.nio.file.Files
                    .readAllLines(Paths.get(path))
                    .stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
