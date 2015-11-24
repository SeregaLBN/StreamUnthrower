package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import utils.stream.Unchecker;

public class Test2 {

    @Test
    public void demo() {
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
            accounts.stream().filter(a -> Unchecker.uncheck(a::isActive)).map(a -> a.name).forEach(System.out::println);
            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            accounts.stream().filter(a -> Unchecker.uncheck(a::firstCharFilter, 'a')).map(a -> a.name).forEach(System.out::println);
            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            accounts.stream().filter(a -> Unchecker.uncheck(a::firstCharFilter, 'a', true)).map(a -> a.name).forEach(System.out::println);
            Assert.fail(); // there will never be
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
