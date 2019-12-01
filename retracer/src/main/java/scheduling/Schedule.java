package scheduling;

import java.util.function.Supplier;

public abstract class Schedule<T> implements Supplier<T> {
    public abstract void start();
}
