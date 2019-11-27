import com.flash3388.flashlib.time.Clock;

import java.util.function.Supplier;

public abstract class Sensor<T> implements TimeBehavior<T>, Supplier<T> {
    private final Clock systemClock;

    public Sensor(Clock systemClock) {
        this.systemClock = systemClock;
    }

    @Override
    public T get() {
        return behavior(systemClock.currentTime());
    }
}
