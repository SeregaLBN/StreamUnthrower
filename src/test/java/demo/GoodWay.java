package demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import demo.some.User;
import utils.stream.Unthrow;

public class GoodWay {

    /////////////////////////////   dummy stubs   /////////////////////////////
    static void checkFileN(String filePath) {
        // ...
    }
    static void checkFileE(String filePath) throws IOException {
        checkFileN(filePath);
    }

    static boolean filterFileN(String filePath) {
        return true;
    }
    static boolean filterFileE(String filePath) throws IOException {
        return filterFileN(filePath);
    }

    private static List<User> getUsers() {
        return Arrays.asList(new User("artem"), new User("alex"), new User("mark"));
    }
    /////////////////////////////   tests   /////////////////////////////

    @Test
    public void exampleWrapperArray() {
        // classic stream way - no checked exceptions
        Stream.of("file # 1", "file # 2")
            .filter(GoodWay::filterFileN)    //  <==>  .filter(f -> filterFileN(f))
            .forEach(GoodWay::checkFileN);   //  <==>  .forEach(f -> checkFileN(f))

        // new stream way - hide existing checked exceptions
        Stream.of("file # 1", "file # 2")
            .filter(f -> Unthrow.wrap(() -> filterFileE(f)))
            .forEach(f -> Unthrow.wrapProc(GoodWay::checkFileE, f));

        // new stream way - _don't_ hide existing checked exceptions -
        try {
            Unthrow.<String, IOException>of(Stream.of("file # 1", "file # 2"))
                    .filter(f -> Unthrow.wrap(() -> filterFileE(f)))
                    .forEach(f -> Unthrow.wrapProc(GoodWay::checkFileE, f));
        } catch (IOException ex) {
            Assertions.fail(ex.getMessage());
        }
    }


    /** example: wrapper methods without parameters */
    @Test
    public void exampleWrapperMethodsWithoutParametersTest() {
        // classic stream way - no checked exceptions
        getUsers().stream()
            .filter(User::isActiveN)
            .map(User::upFirstCharN)
            .forEach(User::printNameN); // .forEach(GoodWay::log)

        // new stream way - hide existing checked exceptions
        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::isActiveE))
            .map(u -> Unthrow.wrap(u::upFirstCharE))
            .forEach(u -> Unthrow.wrapProc(u::printNameE));

        // new stream way - _don't_ hide existing checked exceptions -
        try {
            Unthrow.<User, IOException>of(getUsers().stream())
                    .filter(u -> Unthrow.wrap(u::isActiveE))
                    .map(u -> Unthrow.wrap(u::upFirstCharE))
                    .forEach(u -> Unthrow.wrapProc(u::printNameE));
        } catch (IOException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    /** example: wrapper methods with one parameter */
    @Test
    public void exampleWrapperMethodsWithOneParameter() {
        // classic stream way - no checked exceptions
        getUsers().stream()
            .filter(u -> u.firstCharFilterN('a'))
            .map(u -> u.upFirstCharsN(1))
            .forEach(u -> u.printNameTargetN(GoodWay::log));

        // new stream way - hide existing checked exceptions
        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::firstCharFilterE, 'a'))
            .map(u -> Unthrow.wrap(u::upFirstCharsE, 1))
            .forEach(u -> Unthrow.wrapProc(u::printNameTargetE, GoodWay::log));

        // new stream way - _don't_ hide existing checked exceptions -
        try {
            Unthrow.<User, IOException>of(getUsers().stream())
                    .filter(u -> Unthrow.wrap(u::firstCharFilterE, 'a'))
                    .map(u -> Unthrow.wrap(u::upFirstCharsE, 1))
                    .forEach(u -> Unthrow.wrapProc(u::printNameTargetE, GoodWay::log));
        } catch (IOException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    /** example: wrapper methods with two parameters */
    @Test
    public void exampleWrapperMethodsWithTwoParameters() {
        // classic stream way - no checked exceptions
        getUsers().stream()
            .filter(u -> u.firstCharFilterFailValueN('a', true))
            .map(u -> u.upFirstCharsSuffixN(1, "^"))
            .forEach(u -> u.printNameTargetPrefixN(GoodWay::log, "mr. "));

        // new stream way - hide existing checked exceptions
        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::firstCharFilterFailValueN, 'a', true))
            .map(u -> Unthrow.wrap(u::upFirstCharsSuffixN, 1, "^"))
            .forEach(u -> Unthrow.wrapProc(u::printNameTargetPrefixN, GoodWay::log, "mr. "));

        // new stream way - _don't_ hide existing checked exceptions -
        try {
            Unthrow.<User, IOException>of(getUsers().stream())
                    .filter(u -> Unthrow.wrap(u::firstCharFilterFailValueN, 'a', true))
                    .map(u -> Unthrow.wrap(u::upFirstCharsSuffixN, 1, "^"))
                    .forEach(u -> Unthrow.wrapProc(u::printNameTargetPrefixN, GoodWay::log, "mr. "));
        } catch (IOException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

}
