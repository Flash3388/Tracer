package tracer.motion.basic;

import tracer.motion.basic.units.DistanceUnit;
import tracer.motion.basic.units.UnitConversion;

import java.util.concurrent.TimeUnit;

public class Jerk {
    private final long value;
    private final DistanceUnit distanceUnit;
    private final TimeUnit firstTimeUnit;
    private final TimeUnit secondTimeUnit;
    private final TimeUnit thirdTimeUnit;

    public static Jerk metersPerSecondPerSecondPerSecond(double value) {
        return centimetersPerSecondPerSecondPerSecond(value * 100);
    }

    public static Jerk centimetersPerSecondPerSecondPerSecond(double value) {
        return millimetersPerSecondPerSecondPerSecond(value * 10);
    }

    public static Jerk millimetersPerSecondPerSecondPerSecond(double value) {
        return micrometersPerSecondPerSecondPerSecond((long) value * 1000);
    }

    public static Jerk micrometersPerSecondPerSecondPerSecond(long value) {
        return new Jerk(value, DistanceUnit.MICROMETER, TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Jerk millimetersPerSecondPerSecondPerSecond(long value) {
        return new Jerk(value, DistanceUnit.MILLIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Jerk centimetersPerSecondPerSecondPerSecond(long value) {
        return new Jerk(value, DistanceUnit.CENTIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Jerk metersPerSecondPerSecondPerSecond(long value) {
        return new Jerk(value, DistanceUnit.METERS, TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public Jerk(long value, DistanceUnit distanceUnit, TimeUnit firstTimeUnit, TimeUnit secondTimeUnit, TimeUnit thirdTimeUnit) {
        this.value = value;
        this.distanceUnit = distanceUnit;
        this.firstTimeUnit = firstTimeUnit;
        this.secondTimeUnit = secondTimeUnit;
        this.thirdTimeUnit = thirdTimeUnit;
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

    public TimeUnit thirdTimeUnit() {
        return thirdTimeUnit;
    }

    public Jerk add(Jerk velocity) {
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestFirstTimeUnit = UnitConversion.smallestTimeUnit(firstTimeUnit, velocity.firstTimeUnit());
        TimeUnit smallestSecondTimeUnit = UnitConversion.smallestTimeUnit(secondTimeUnit, velocity.secondTimeUnit());
        TimeUnit smallestThirdTimeUnit = UnitConversion.smallestTimeUnit(thirdTimeUnit, velocity.thirdTimeUnit());

        long sum = this.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit).value() +
                velocity.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit).value();

        return new Jerk(sum, smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit);
    }

    public Jerk sub(Jerk velocity) {
        DistanceUnit smallestDistanceUnit = UnitConversion.smallestDistanceUnit(distanceUnit, velocity.distanceUnit());
        TimeUnit smallestFirstTimeUnit = UnitConversion.smallestTimeUnit(firstTimeUnit, velocity.firstTimeUnit());
        TimeUnit smallestSecondTimeUnit = UnitConversion.smallestTimeUnit(secondTimeUnit, velocity.secondTimeUnit());
        TimeUnit smallestThirdTimeUnit = UnitConversion.smallestTimeUnit(thirdTimeUnit, velocity.thirdTimeUnit());

        long result = this.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit).value() -
                velocity.to(smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit).value();

        return new Jerk(result, smallestDistanceUnit, smallestFirstTimeUnit, smallestSecondTimeUnit, smallestThirdTimeUnit);
    }

    public Jerk to(DistanceUnit newDistanceUnit, TimeUnit newFirstTimeUnit, TimeUnit newSecondTimeUnit, TimeUnit newThirdTimeUnit) {
        long convertedToDistanceUnit = distanceUnit.convert(value, newDistanceUnit);
        long convertedToFirstTimeUnit = firstTimeUnit.convert(convertedToDistanceUnit, newFirstTimeUnit);
        long convertedToSecondTimeUnit = secondTimeUnit.convert(convertedToFirstTimeUnit, newSecondTimeUnit);

        return new Jerk(thirdTimeUnit.convert(convertedToSecondTimeUnit, newThirdTimeUnit), newDistanceUnit, newFirstTimeUnit, newSecondTimeUnit, newThirdTimeUnit);
    }

    public Jerk reverse() {
        return new Jerk(-value, distanceUnit, firstTimeUnit, secondTimeUnit, thirdTimeUnit);
    }

    public long valueAsMicrometersPerSecondPerSecondPerSecond() {
        return to(DistanceUnit.MILLIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.SECONDS).value();
    }

    @Override
    public String toString() {
        return value + " [" + distanceUnit + "] " + "PER " + "[" + firstTimeUnit + "]" + "PER " + "[" + secondTimeUnit + "]" + "PER " + "[" + thirdTimeUnit + "]";
    }
}
