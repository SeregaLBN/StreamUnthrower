package demo;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExampleNoThrow {

    public static void main(String[] args) {
        // no try catch!
        new ExampleNoThrow().throwSafe();
    }

    void throwNotSafe() throws IOException {
        throw new FileNotFoundException("Hello world!");
    }

    // No throws declared!
    void throwSafe() {
        try {
            throwNotSafe();
        } catch (IOException ex) {
            rethrow(ex);
        }
    }

    @SuppressWarnings("unchecked")
    static <E extends Exception>
    void rethrow(Exception e) throws E {
        throw (E) e;
    }

}
