package demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import utils.stream.Unchecker;

public class GoodWay {

    @Test
    public void demo1() {
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


    @Test
    public void demo2() {
        class User {
            String name;

            User(String name) {
                this.name = name;
            }

            public boolean isActive() throws IOException {
                return firstCharFilter('a');
            }

            public boolean firstCharFilter(char c) throws IOException {
                return firstCharFilter('a', true);
            }

            public boolean firstCharFilter(char c, boolean defaultResult) throws IOException {
                if (!name.startsWith("" + c))
                    throw new IOException("Bad name '" + name + "'");
                return defaultResult;
            }
        }

        List<User> accounts = new ArrayList<>(Arrays.asList(new User("artem"), new User("alex"), new User("mark")));

        try {
            accounts.stream()
                .filter(a -> Unchecker.uncheck(a::isActive))
                .map(a -> a.name)
                .forEach(System.out::println);

            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            accounts.stream()
                .filter(a -> Unchecker.uncheck(a::firstCharFilter, 'a'))
                .map(a -> a.name)
                .forEach(System.out::println);

            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            accounts.stream()
                .filter(a -> Unchecker.uncheck(a::firstCharFilter, 'a', true))
                .map(a -> a.name)
                .forEach(System.out::println);

            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
