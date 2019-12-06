package scheduling;

import java.util.function.Supplier;

public interface Schedule<T> extends Supplier<T> {
    void start();
    boolean isDone();
}
