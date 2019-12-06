package scheduling;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;

public abstract class TimeSchedule<T> implements Schedule<T>, TimeBehavior<T> {
    private final Clock systemClock;
    private Time startTime;

    public TimeSchedule(Clock systemClock) {
        this.systemClock = systemClock;
    }

    @Override
    public void start() {
        startTime = systemClock.currentTime();
    }

    @Override
    public T behavior(Time input) {
        return schedule(systemClock.currentTime().sub(startTime));
    }

    @Override
    public T get() {
        return behavior(systemClock.currentTime());
    }

    protected abstract T schedule(Time relativeTime);
}
