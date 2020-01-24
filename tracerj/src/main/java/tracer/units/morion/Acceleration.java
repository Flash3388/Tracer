package tracer.units.morion;

import tracer.units.distance.DistanceUnit;
import tracer.units.generic.NotMatchingUnitsException;
import tracer.units.generic.Unit;
import tracer.units.time.TimeUnit;

public class Acceleration implements Unit {
    private final double value;
    private final DistanceUnit distanceUnit;
    private final TimeUnit firstTimeUnit;
    private final TimeUnit secondTimeUnit;

    public Acceleration(double value, DistanceUnit distanceUnit, TimeUnit firstTimeUnit, TimeUnit secondTimeUnit) {
        this.value = value;
        this.distanceUnit = distanceUnit;
        this.firstTimeUnit = firstTimeUnit;
        this.secondTimeUnit = secondTimeUnit;
    }

    public static Acceleration milesPerHourSquared(double value) {
        return new Acceleration(value, DistanceUnit.MILES, TimeUnit.HOURS, TimeUnit.HOURS);
    }

    public static Acceleration kilometersPerHourSquared(double value) {
        return new Acceleration(value, DistanceUnit.KILOMETERS, TimeUnit.HOURS, TimeUnit.HOURS);
    }

    public static Acceleration metersPerSecondSquared(double value) {
        return new Acceleration(value, DistanceUnit.METERS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration feetPerSecondSquared(double value) {
        return new Acceleration(value, DistanceUnit.FEET, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration inchesPerSecondSquared(double value) {
        return new Acceleration(value, DistanceUnit.INCHES, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    public static Acceleration centimetersPerSecondSquared(double value) {
        return new Acceleration(value, DistanceUnit.CENTIMETERS, TimeUnit.SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public double value() {
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

    @Override
    public Acceleration add(Unit other) {
        if (other instanceof Acceleration)
            return add((Acceleration) other);
        else
            throw new NotMatchingUnitsException();
    }

    public Acceleration add(Acceleration other) {
        other = other.toUnit(this);
        return new Acceleration(value + value, distanceUnit, firstTimeUnit, secondTimeUnit);
    }

    @Override
    public Acceleration sub(Unit other) {
        if (other instanceof Acceleration)
            return sub((Acceleration) other);
        else
            throw new NotMatchingUnitsException();
    }

    public Acceleration sub(Acceleration other) {
        other = other.toUnit(this);
        return new Acceleration(value - value, distanceUnit, firstTimeUnit, secondTimeUnit);
    }

    public Acceleration toUnit(Acceleration other) {
        return toUnit(other.distanceUnit(), other.firstTimeUnit(), other.secondTimeUnit());
    }

    public Acceleration toUnit(DistanceUnit newDistanceUnit, TimeUnit newFirstTimeUnit, TimeUnit newSecondTimeUnit) {
        return new Acceleration(
                distanceUnit.toUnit(newDistanceUnit,
                        firstTimeUnit.toUnit(newFirstTimeUnit,
                                secondTimeUnit.toUnit(newSecondTimeUnit, value))), newDistanceUnit, newFirstTimeUnit, newSecondTimeUnit);
    }
}
