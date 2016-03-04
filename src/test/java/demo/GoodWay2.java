package demo;

import org.junit.Test;
import utils.stream.Unthrow;

import java.util.concurrent.Callable;
import java.util.function.Function;


/**
 * examples of using
 * @see Unthrow
 */
public class GoodWay2 {

    @Test
    public void exampleNoParam() {
        { // classic way
            Runnable proc0 = new Runnable() {
                @Override
                public void run() {
                    System.out.println("classic way example1.0");
                }
            };
            Runnable proc1 = () -> { System.out.println("classic way example1.1"); };
            Runnable proc2 = () -> System.out.println("classic way example1.2");

            proc0.run();
            proc1.run();
            proc2.run();
        }
        { // unwrap way
            Callable<Boolean> func0 = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    System.out.println("unwrap way example1.x");
                    return true;
                }
            };
            Callable<Boolean> func1 = () -> { return func0.call(); };
            Callable<Boolean> func2 = () -> func1.call();

            Unthrow.wrap(func0::call); // equals => try { func0.call(); } catch (Exception ex) { throw ex; }
            Unthrow.wrap(func1::call); // equals => try { func1.call(); } catch (Exception ex) { throw ex; }
            Unthrow.wrap(func2::call); // equals => try { func2.call(); } catch (Exception ex) { throw ex; }
        }
    }

    @Test
    public void exampleWithParam() {
        { // classic way
            Function<Integer, Boolean> func0 = new Function<Integer, Boolean>() {
                @Override
                public Boolean apply(Integer v) {
                    return true;
                }
            };
            Function<Integer, Boolean> func1 = v -> { return true; };
            Function<Integer, Boolean> func2 = v -> true;

            System.out.println("classic way example2.0: " + func0.apply(0));
            System.out.println("classic way example2.1: " + func1.apply(1));
            System.out.println("classic way example2.2: " + func2.apply(2));
        }
        { // unwrap way
            Unthrow.IFunc1<Boolean, Integer> func0 = new Unthrow.IFunc1<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer v) {
                    return true;
                }
            };
            Unthrow.IFunc1<Boolean, Integer> func1 = (v) -> { return true; };
            Unthrow.IFunc1<Boolean, Integer> func2 = (v) -> true;

            Boolean res0 = Unthrow.wrap(v -> func0.apply(v), 0); // equals => try { func0.apply(0); } catch (Exception ex) { throw ex; }
            Boolean res1 = Unthrow.wrap(v -> func1.apply(v), 1); // equals => try { func1.apply(1); } catch (Exception ex) { throw ex; }
            Boolean res2 = Unthrow.wrap(v -> func2.apply(v), 2); // equals => try { func2.apply(2); } catch (Exception ex) { throw ex; }

            System.out.println("unwrap way example2.0: " + res0);
            System.out.println("unwrap way example2.1: " + res1);
            System.out.println("unwrap way example2.2: " + res2);
        }
    }

}
