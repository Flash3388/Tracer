package tracer.motion.basic;

import java.util.concurrent.TimeUnit;

public class Velocity {
    private final long value;
    private final DistanceUnit distanceUnit;
    private final TimeUnit timeUnit;

    public Velocity(long value, DistanceUnit distanceUnit, TimeUnit timeUnit) {
        this.value = value;
        this.distanceUnit = distanceUnit;
        this.timeUnit = timeUnit;
    }

    public long value() {
        return value;
    }

    public DistanceUnit distanceUnit() {
        return distanceUnit;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

    public Velocity add(Velocity velocity) {
        DistanceUnit smallestDistanceUnit = distanceUnit.smallestUnit(velocity.distanceUnit());
        TimeUnit smallestTimeUnit = smallestTimeUnit(velocity.timeUnit);

        long sum = this.to(smallestDistanceUnit, smallestTimeUnit).value() + velocity.to(smallestDistanceUnit, smallestTimeUnit).value();

        return new Velocity(sum, smallestDistanceUnit, smallestTimeUnit);
    }

    public Velocity sub(Velocity velocity) {
        DistanceUnit smallestDistanceUnit = distanceUnit.smallestUnit(velocity.distanceUnit());
        TimeUnit smallestTimeUnit = smallestTimeUnit(velocity.timeUnit);

        long sum = this.to(smallestDistanceUnit, smallestTimeUnit).value() - velocity.to(smallestDistanceUnit, smallestTimeUnit).value();

        return new Velocity(sum, smallestDistanceUnit, smallestTimeUnit);
    }

    public Velocity to(DistanceUnit newDistanceUnit, TimeUnit newTimeUnit) {
        long convertedToDistance = distanceUnit.convert(value, newDistanceUnit);

        return new Velocity(timeUnit.convert(convertedToDistance, newTimeUnit), newDistanceUnit, newTimeUnit);
    }

    private TimeUnit smallestTimeUnit(TimeUnit other) {
        return other.compareTo(timeUnit) < 0 ? other : timeUnit;
    }
}
