package demo;

import org.junit.Test;
import utils.stream.Unthrow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Demo Unthrow usage */
public class Demo {

    static class BlaBla {
        void method() throws IOException { System.out.println("BlaBla.method"); }
        boolean isSomething() throws IOException { return (UUID.randomUUID().hashCode() & 1) == 0; }
    }

    private List<BlaBla> getBlaBlaList() {
        return new ArrayList<BlaBla>() {{
            add(new BlaBla()); add(new BlaBla());
            add(new BlaBla()); add(new BlaBla());
        }};
    }

    @Test
    public void blablaTest() {
        System.out.println("=============");
        // :)
        getBlaBlaList().stream()
                .filter( blaBla -> Unthrow.wrap(() -> blaBla.isSomething()) )
                .forEach(blaBla -> Unthrow.wrapProc(() -> blaBla.method() ) );
        System.out.println("-------------");
        // :(
        getBlaBlaList().stream()
                .filter( blaBla -> { try { return blaBla.isSomething(); } catch (Exception ex) { throw new RuntimeException(ex); } } )
                .forEach(blaBla -> { try { blaBla.method(); } catch (Exception ex) { throw new RuntimeException(ex); } } );
    }

    @Test
    public void blablaTest2() throws IOException { // <---- don't hide IOException !
        System.out.println("=============");
        Unthrow.<BlaBla, IOException>of(getBlaBlaList().stream())
                .filter( blaBla -> Unthrow.wrap(() -> blaBla.isSomething()) )
                .forEach(blaBla -> Unthrow.wrapProc(() -> blaBla.method()) );
    }

    public static void main(String[] args) {
        new Demo().blablaTest();
    }

}
