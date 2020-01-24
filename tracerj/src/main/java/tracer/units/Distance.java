package tracer.units;

import com.flash3388.flashlib.util.CompareResult;
import com.jmath.ExtendedMath;

public class Distance implements Comparable<Distance>{
    private final double value;
    private final DistanceUnit unit;

    public Distance(double value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Distance miles(double value) {
        return new Distance(value, DistanceUnit.MILES);
    }

    public static Distance kilometers(double value) {
        return new Distance(value, DistanceUnit.KILOMETERS);
    }

    public static Distance meters(double value) {
        return new Distance(value, DistanceUnit.METERS);
    }

    public static Distance yards(double value) {
        return new Distance(value, DistanceUnit.YARDS);
    }

    public static Distance inches(double value) {
        return new Distance(value, DistanceUnit.INCHES);
    }

    public static Distance centimeters(double value) {
        return new Distance(value, DistanceUnit.CENTIMETERS);
    }

    public static Distance millimeters(double value) {
        return new Distance(value, DistanceUnit.MILLIMETERS);
    }

    public double value() {
        return value;
    }

    public double valueAsMeters() {
        return unit.toUnit(DistanceUnit.METERS, value);
    }

    public DistanceUnit unit() {
        return unit;
    }

    public Distance add(Distance other) {
        other = other.toUnit(unit);

        return new Distance(value+other.value(), unit);
    }

    public Distance sub(Distance other) {
        other = other.toUnit(unit);

        return new Distance(value-other.value(), unit);
    }

    public Distance toUnit(DistanceUnit newUnit) {
        return new Distance(unit.toUnit(newUnit, value), newUnit);
    }

    public boolean equals(Distance other) {
        return sub(other).value() == 0;
    }

    @Override
    public int compareTo(Distance distance) {
        return (int) ExtendedMath.constrain(sub(distance).value(), CompareResult.SMALLER_THAN.value(), CompareResult.GREATER_THAN.value());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Distance && equals((Distance)o);
    }
}
