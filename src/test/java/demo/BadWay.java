package demo;

import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class BadWay {

    @Test
    public void classicWay() {
        try {
            // desired
            //Stream.of("file # 1", "file # 2").filter(f -> filterFile(f)).forEach(f -> checkFile(f));

            // reality
            Stream.of("file # 1", "file # 2")
                .filter(f -> {
                    try {
                        return filterFile(f);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .forEach(f -> {
                    try {
                        checkFile(f);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

            Assert.fail(); // there will never be

        } catch (Exception ex) {
            System.out.println("Exception is instance of " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    static void checkFile(String filePath) throws FileNotFoundException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            throw new FileNotFoundException("File is not exist: " + filePath);
        }
        // ...
    }

    static boolean filterFile(String filePath) throws AccessDeniedException {
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException(filePath);

        if (filePath.endsWith("2")) // mock if
            throw new AccessDeniedException(filePath);
        return true;
    }

}
