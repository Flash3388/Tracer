package scheduling;

import com.flash3388.flashlib.time.Clock;

import java.util.function.DoubleSupplier;

public abstract class MotorSchedule implements DoubleSupplier, TimeBehavior<Double> {
    private final Clock systemClock;

    public MotorSchedule(Clock systemClock) {
        this.systemClock = systemClock;
    }

    @Override
    public double getAsDouble() {
        return behavior(systemClock.currentTime());
    }
}
