package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import demo.some.User;
import utils.stream.Unthrow;;

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
        List<User> users = new ArrayList<>(Arrays.asList(new User("artem"), new User("alex"), new User("mark")));
        return users;
    }
    /////////////////////////////   tests   /////////////////////////////

    @Test
    public void exampleWrapperArray() {
        Stream.of("file # 1", "file # 2")
            .filter(GoodWay::filterFileN)    //  <==>  .filter(f -> filterFileN(f))
            .forEach(GoodWay::checkFileN);   //  <==>  .forEach(f -> checkFileN(f))

        Stream.of("file # 1", "file # 2")
            .filter(f -> Unthrow.wrap(() -> filterFileE(f)))
            .forEach(f -> Unthrow.wrapProc((x) -> checkFileE(x), f)); 
    }


    /** example: wrapper methods without parameters */
    @Test
    public void exampleWrapperMethodsWithoutParametersTest() {
        getUsers().stream()
            .filter(User::isActiveN)
            .map(User::upFirstCharN)
            .forEach(User::printNameN); // .forEach(System.out::println)

        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::isActiveE))
            .map(u -> Unthrow.wrap(u::upFirstCharE))
            .forEach(u -> Unthrow.wrapProc(u::printNameE));
    }

    /** example: wrapper methods with one parameter */
    @Test
    public void exampleWrapperMethodsWithOneParameter() {
        getUsers().stream()
            .filter(u -> u.firstCharFilterN('a'))
            .map(u -> u.upFirstCharsN(1))
            .forEach(u -> u.printNameTargetN(System.out::println));

        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::firstCharFilterE, 'a'))
            .map(u -> Unthrow.wrap(u::upFirstCharsE, 1))
            .forEach(u -> Unthrow.wrapProc(u::printNameTargetE, System.out::println));
    }

    /** example: wrapper methods with two parameters */
    @Test
    public void exampleWrapperMethodsWithTwoParameters() {
        getUsers().stream()
            .filter(u -> u.firstCharFilterFailValueN('a', true))
            .map(u -> u.upFirstCharsSuffixN(1, "^"))
            .forEach(u -> u.printNameTargetPrefixN(System.out::println, "mr. "));

        getUsers().stream()
            .filter(u -> Unthrow.wrap(u::firstCharFilterFailValueN, 'a', true))
            .map(u -> Unthrow.wrap(u::upFirstCharsSuffixN, 1, "^"))
            .forEach(u -> Unthrow.wrapProc(u::printNameTargetPrefixN, System.out::println, "mr. "));
    }

}
