import com.flash3388.flashlib.time.Clock;

import java.util.function.DoubleSupplier;

public abstract class Motor implements DoubleSupplier, TimeBehavior<Double> {
    private final Clock systemClock;

    public Motor(Clock systemClock) {
        this.systemClock = systemClock;
    }

    @Override
    public double getAsDouble() {
        return behavior(systemClock.currentTime());
    }
}
