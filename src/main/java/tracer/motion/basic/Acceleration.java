package tracer.motion.basic;

import tracer.motion.basic.units.DistanceUnit;
import tracer.motion.basic.units.UnitConversion;

import java.util.concurrent.TimeUnit;

public class Acceleration {
    private final long value;
    private final DistanceUnit distanceUnit;
    private final TimeUnit firstTimeUnit;
    private final TimeUnit secondTimeUnit;

    public static Acceleration metersPerSecondPerSecond(double value) {
        return centimetersPerSecondPerSecond(value * 100);
    }

    public static Acceleration centimetersPerSecondPerSecond(double value) {
        return millimetersPerSecondPerSecond(value * 10);
    }

    public static Acceleration millimetersPerSecondPerSecond(double value) {
        return micrometersPerSecondPerSecond((long) value * 1000);
    }

    public static Acceleration micrometersPerSecondPerSecond(long value) {
        return new Acceleration(value, DistanceUnit.MICROMETER, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration millimetersPerSecondPerSecond(long value) {
        return new Acceleration(value, DistanceUnit.MILLIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration centimetersPerSecondPerSecond(long value) {
        return new Acceleration(value, DistanceUnit.CENTIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration metersPerSecondPerSecond(long value) {
        return new Acceleration(value, DistanceUnit.METERS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public Acceleration(long value, DistanceUnit distanceUnit, TimeUnit firstTimeUnit, TimeUnit secondTimeUnit) {
        this.value = value;
        this.distanceUnit = distanceUnit;
        this.firstTimeUnit = firstTimeUnit;
        this.secondTimeUnit = secondTimeUnit;
    }

    public long value() {
        return value;
    }

    public DistanceUnit distanceUnit() {
        return distanceUnit;
    }

    public TimeUnit firstTimeUnit() {
        return firstTimeUnit;
    }

    public TimeUnit secondTimeUnit() {
        return secondTimeUnit;
    }

    public Acceleration add(Acceleration velocity) {
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestFirstTimeUnit = UnitConversion.smallestTimeUnit(firstTimeUnit, velocity.firstTimeUnit());
        TimeUnit smallestSecondTimeUnit = UnitConversion.smallestTimeUnit(secondTimeUnit, velocity.secondTimeUnit());

        long sum = this.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit).value() +
                velocity.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit).value();

        return new Acceleration(sum, smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit);
    }

    public Acceleration sub(Acceleration velocity) {
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestFirstTimeUnit = UnitConversion.smallestTimeUnit(firstTimeUnit, velocity.firstTimeUnit());
        TimeUnit smallestSecondTimeUnit = UnitConversion.smallestTimeUnit(secondTimeUnit, velocity.secondTimeUnit());

        long result = this.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit).value() - velocity.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit).value();

        return new Acceleration(result, smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit);
    }

    public Acceleration to(DistanceUnit newDistanceUnit, TimeUnit newFirstTimeUnit, TimeUnit newSecondTimeUnit) {
        long convertedToDistanceUnit = distanceUnit.convert(value, newDistanceUnit);
        long convertedToFirstTimeUnit = firstTimeUnit.convert(convertedToDistanceUnit, newFirstTimeUnit);

        return new Acceleration(secondTimeUnit.convert(convertedToFirstTimeUnit, newSecondTimeUnit), newDistanceUnit, newFirstTimeUnit, newSecondTimeUnit);
    }
}
