package demo;

import utils.stream.Unthrow;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

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
                    log("classic way example1.0");
                }
            };
            Runnable proc1 = () -> { log("classic way example1.1"); };
            Runnable proc2 = () -> log("classic way example1.2");

            proc0.run();
            proc1.run();
            proc2.run();
        }
        { // unwrap way
            Callable<Boolean> func0 = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    log("unwrap way example1.x");
                    return true;
                }
            };
            Callable<Boolean> func1 = () -> { return func0.call(); };
            Callable<Boolean> func2 = () ->          func1.call();
            Callable<Boolean> func3 =                func2::call;

            Unthrow.wrap(func0::call); // equals => try { func0.call(); } catch (Exception ex) { throw ex; }
            Unthrow.wrap(func1::call); // equals => try { func1.call(); } catch (Exception ex) { throw ex; }
            Unthrow.wrap(func2::call); // equals => try { func2.call(); } catch (Exception ex) { throw ex; }
            Unthrow.wrap(func3::call); // equals => try { func3.call(); } catch (Exception ex) { throw ex; }
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
            Predicate<Integer>         func3 = v -> true;
            IntPredicate               func4 = v -> true;

            log("classic way example2.0: " + func0.apply(0));
            log("classic way example2.1: " + func1.apply(1));
            log("classic way example2.2: " + func2.apply(2));
            log("classic way example2.3: " + func3.test(2));
            log("classic way example2.4: " + func4.test(2));
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

            log("unwrap way example2.0: " + res0);
            log("unwrap way example2.1: " + res1);
            log("unwrap way example2.2: " + res2);
        }
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

}
