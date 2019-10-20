package tracer.motion.basic;

import tracer.motion.basic.units.DistanceUnit;
import tracer.motion.basic.units.UnitConversion;

public class Distance {
    private final long value;
    private final DistanceUnit unit;

    public static Distance meters(double value) {
        return centimeters(value * 100);
    }

    public static Distance centimeters(double value) {
        return millimeters(value * 10);
    }

    public static Distance millimeters(double value) {
        return microMeters((long) value * 1000);
    }

    public static Distance microMeters(long value) {
        return new Distance(value, DistanceUnit.MICROMETER);
    }

    public static Distance millimeters(long value) {
        return new Distance(value, DistanceUnit.MILLIMETERS);
    }

    public static Distance centimeters(long value) {
        return new Distance(value, DistanceUnit.CENTIMETERS);
    }

    public static Distance meters(long value) {
        return new Distance(value, DistanceUnit.METERS);
    }

    public static Distance kilometers(long value) {
        return new Distance(value, DistanceUnit.KILOMETERS);
    }

    public Distance(long value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public long value() {
        return value;
    }

    public DistanceUnit unit() {
        return unit;
    }

    public Distance add(Distance distance) {
        DistanceUnit smallestUnit = UnitConversion.smallestDistanceUnit(unit, distance.unit());
        long sum = this.to(smallestUnit).value() + distance.to(smallestUnit).value();

        return new Distance(sum, smallestUnit);
    }

    public Distance sub(Distance distance) {
        DistanceUnit smallestUnit = UnitConversion.smallestDistanceUnit(unit, distance.unit());
        long result = this.to(smallestUnit).value() - distance.to(smallestUnit).value();

        return new Distance(result, smallestUnit);
    }

    public Distance to(DistanceUnit newUnit) {
        if(newUnit.equals(unit))
            return this;
        return new Distance(unit.convert(value, newUnit), newUnit);
    }

    public long valueAsMillimeters() {
        return to(DistanceUnit.MILLIMETERS).value();
    }

    @Override
    public String toString() {
        return value + " [" + unit + "]";
    }
}
