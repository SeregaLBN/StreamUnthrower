package utils.stream;

public class Unchecker {
    @SuppressWarnings("unchecked")
    static <R, E extends Exception> R rethrow(Exception ex) throws E {
        throw (E) ex;
    }

    ////////////////////////////////// interfaces ProcedureN //////////////////////////////////

    /** like as java.lang.Runnable */
    @FunctionalInterface
    public interface Procedure0_WithExceptions<E extends Exception> {
        void run() throws E;
    }

    /** like as java.util.function.Consumer<T> */
    @FunctionalInterface
    public interface Procedure1_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface Procedure2_WithExceptions<T1, T2, E extends Exception> {
        void accept(T1 t1, T2 t2) throws E;
    }

    ////////////////////////////////// interfaces FunctionN //////////////////////////////////

    /** like as java.util.concurrent.Callable<R> */
    @FunctionalInterface
    public interface Function0_WithExceptions<R, E extends Exception> {
        R call() throws E;
    }

    /** like as java.util.function.Function<T,R> */
    @FunctionalInterface
    public interface Function1_WithExceptions<R, T, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface Function2_WithExceptions<R, T1, T2, E extends Exception> {
        R apply(T1 t1, T2 t2) throws E;
    }

    ////////////////////////////////// uncheck Procedures //////////////////////////////////

    public static void uncheckProc(Procedure0_WithExceptions<Exception> proc) {
        try {
            proc.run();
        } catch (Exception ex) {
            rethrow(ex);
        }
    }

    public static <T> void uncheckProc(Procedure1_WithExceptions<T, Exception> proc, T t) {
        try {
            proc.accept(t);
        } catch (Exception ex) {
            rethrow(ex);
        }
    }

    public static <T1, T2> void uncheckProc(Procedure2_WithExceptions<T1, T2, Exception> proc, T1 t1, T2 t2) {
        try {
            proc.accept(t1, t2);
        } catch (Exception ex) {
            rethrow(ex);
        }
    }

    ////////////////////////////////// uncheck Functions //////////////////////////////////

    public static <R> R uncheck(Function0_WithExceptions<R, Exception> func) {
        try {
            return func.call();
        } catch (Exception ex) {
            return rethrow(ex);
        }
    }

    public static <R, T> R uncheck(Function1_WithExceptions<R, T, Exception> func, T t) {
        try {
            return func.apply(t);
        } catch (Exception ex) {
            return rethrow(ex);
        }
    }

    public static <R, T1, T2> R uncheck(Function2_WithExceptions<R, T1, T2, Exception> func, T1 t1, T2 t2) {
        try {
            return func.apply(t1, t2);
        } catch (Exception ex) {
            return rethrow(ex);
        }
    }

}
