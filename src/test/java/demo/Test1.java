package demo;

import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import utils.stream.Unchecker;

public class Test1 {

    @Test
    void demo1() {
        try {
            Stream.of("file # 1", "file # 2")
                .filter(f -> Unchecker.uncheck(() -> filterFile(f)))
                .forEach(f -> Unchecker.uncheckProc((x) -> checkFile(x), f));

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
