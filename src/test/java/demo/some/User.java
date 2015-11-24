package demo.some;

import java.io.IOException;
import java.util.function.Consumer;

public class User {
    String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public boolean isActiveN() {
        return firstCharFilterN('a');
    }

    public boolean isActiveE() throws IOException {
        return firstCharFilterE('a');
    }

    public boolean firstCharFilterN(char c) {
        return firstCharFilterFailValueN('a', false);
    }

    public boolean firstCharFilterE(char c) throws IOException {
        return firstCharFilterE('a', false);
    }

    public boolean firstCharFilterFailValueN(char c, boolean ifFailResult) {
        return (name.charAt(0) == c) ? true
                                     : ifFailResult;
    }

    public boolean firstCharFilterE(char c, boolean ifFailResult) throws IOException {
        try {
            return firstCharFilterFailValueN(c, ifFailResult);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public User upFirstCharN() {
        return upFirstCharsN(1);
    }

    public User upFirstCharE() throws IOException {
        return upFirstCharsE(1);
    }

    public User upFirstCharsN(int numberChars) {
        return upFirstCharsSuffixN(numberChars, null);
    }

    public User upFirstCharsE(int numberChars) throws IOException {
        return upFirstCharsSuffixE(numberChars, null);
    }

    public User upFirstCharsSuffixN(int numberChars, String suffix) {
        name = name.substring(0, numberChars).toUpperCase() + name.substring(numberChars) + suffix;
        return this;
    }

    public User upFirstCharsSuffixE(int numberChars, String suffix) throws IOException {
        try {
            return upFirstCharsSuffixN(numberChars, suffix);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public void printNameN() {
        printNameTargetN(System.out::println);
    }

    public void printNameE() throws IOException {
        printNameTargetE(System.out::println);
    }

    public void printNameTargetN(Consumer<String> target) {
        printNameTargetPrefixN(target, null);
    }

    public void printNameTargetE(Consumer<String> target) throws IOException {
        printNameTargetPrefixE(target, null);
    }

    public void printNameTargetPrefixN(Consumer<String> target, String prefix) {
        target.accept(prefix + name);
    }

    public void printNameTargetPrefixE(Consumer<String> target, String prefix) throws IOException {
        try {
            printNameTargetPrefixN(target, prefix);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

}
