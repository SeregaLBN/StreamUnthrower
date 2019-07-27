package demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

import utils.stream.Unthrow;

/** Demo Unthrow usage */
public class Demo {

    static class BlaBla {
        void method() throws IOException { log("BlaBla.method"); }
        boolean isSomething() throws IOException { return (UUID.randomUUID().hashCode() & 1) == 0; }
    }

    private List<BlaBla> getBlaBlaList() {
        return Arrays.asList(
            new BlaBla(), new BlaBla(),
            new BlaBla(), new BlaBla()
        );
    }

    @Test
    public void blablaTest() {
        log("=============");
        // :)
        getBlaBlaList().stream()
                .filter( blaBla -> Unthrow.wrap(() -> blaBla.isSomething()) )
                .forEach(blaBla -> Unthrow.wrapProc(() -> blaBla.method() ) );
        log("-------------");
        // :(
        getBlaBlaList().stream()
                .filter( blaBla -> { try { return blaBla.isSomething(); } catch (Exception ex) { throw new RuntimeException(ex); } } )
                .forEach(blaBla -> { try { blaBla.method(); } catch (Exception ex) { throw new RuntimeException(ex); } } );
    }

    @Test
    public void blablaTest2() throws IOException { // <---- don't hide IOException !
        log("=============");
        Unthrow.<BlaBla, IOException>of(getBlaBlaList().stream())
                .filter( blaBla -> Unthrow.wrap(() -> blaBla.isSomething()) )
                .forEach(blaBla -> Unthrow.wrapProc(() -> blaBla.method()) );
    }

    public static void main(String[] args) {
        new Demo().blablaTest();
    }

    private Integer myMethod(String s) throws IOException {
        return 1;
    }

    @Test
    public void test2() {
        ToIntFunction<String> func1 = s -> Unthrow.wrap(() -> myMethod(s));
        ToIntFunction<String> func2 = s -> Unthrow.wrap(this::myMethod, s);
        func1.applyAsInt("test1");
        func2.applyAsInt("test2");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

}
