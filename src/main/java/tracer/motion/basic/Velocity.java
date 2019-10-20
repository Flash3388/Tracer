package tracer.motion.basic;

import java.util.concurrent.TimeUnit;

public class Velocity {
    private final long value;
    private final DistanceUnit distanceUnit;
    private final TimeUnit timeUnit;

    public static Velocity metersPerSecond(double value) {
        return centimetersPerSecond(value * 100);
    }

    public static Velocity centimetersPerSecond(double value) {
        return millimetersPerSecond(value * 10);
    }

    public static Velocity millimetersPerSecond(double value) {
        return micrometersPerSecond((long) value * 1000);
    }

    public static Velocity micrometersPerSecond(long value) {
        return new Velocity(value, DistanceUnit.MICROMETER, TimeUnit.SECONDS);
    }

    public static Velocity millimetersPerSecond(long value) {
        return new Velocity(value, DistanceUnit.MILLIMETERS, TimeUnit.SECONDS);
    }

    public static Velocity centimetersPerSecond(long value) {
        return new Velocity(value, DistanceUnit.CENTIMETERS, TimeUnit.SECONDS);
    }

    public static Velocity metersPerSecond(long value) {
        return new Velocity(value, DistanceUnit.METERS, TimeUnit.SECONDS);
    }

    public static Velocity kilometersPerHour(long value) {
        return new Velocity(value, DistanceUnit.KILOMETERS, TimeUnit.HOURS);
    }

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
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestTimeUnit = UnitConversion.smallestTimeUnit(timeUnit, velocity.timeUnit());

        long sum = this.to(smallestDistanceUnit, smallestTimeUnit).value() + velocity.to(smallestDistanceUnit, smallestTimeUnit).value();

        return new Velocity(sum, smallestDistanceUnit, smallestTimeUnit);
    }

    public Velocity sub(Velocity velocity) {
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestTimeUnit = UnitConversion.smallestTimeUnit(timeUnit, velocity.timeUnit());

        long sum = this.to(smallestDistanceUnit, smallestTimeUnit).value() - velocity.to(smallestDistanceUnit, smallestTimeUnit).value();

        return new Velocity(sum, smallestDistanceUnit, smallestTimeUnit);
    }

    public Velocity to(DistanceUnit newDistanceUnit, TimeUnit newTimeUnit) {
        long convertedToDistanceUnit = distanceUnit.convert(value, newDistanceUnit);

        return new Velocity(timeUnit.convert(convertedToDistanceUnit, newTimeUnit), newDistanceUnit, newTimeUnit);
    }
}
