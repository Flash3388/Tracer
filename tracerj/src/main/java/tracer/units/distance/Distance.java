package tracer.units.distance;

import tracer.units.exceptions.NotMatchingUnitsException;
import tracer.units.generic.Unit;

public class Distance implements Unit {
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

    public static Distance feet(double value) {
        return new Distance(value, DistanceUnit.FEET);
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

    @Override
    public double value() {
        return value;
    }

    public double valueAsMeters() {
        return unit.toUnit(DistanceUnit.METERS, value);
    }

    public DistanceUnit unit() {
        return unit;
    }

    public Distance add(Unit other) {
        if (other instanceof Distance)
            return add((Distance)other);
        else
            throw new NotMatchingUnitsException();
    }

    public Distance add(Distance other) {
        other = other.toUnit(unit);

        return new Distance(value + other.value(), unit);
    }

    public Distance sub(Unit other) {
        if (other instanceof Distance)
            return sub((Distance)other);
        else
            throw new NotMatchingUnitsException();
    }

    public Distance sub(Distance other) {
        other = other.toUnit(unit);

        return new Distance(value - other.value(), unit);
    }

    public Distance toUnit(DistanceUnit newUnit) {
        return new Distance(unit.toUnit(newUnit, value), newUnit);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Distance && equals((Distance)o);
    }

    @Override
    public String toString() {
        return String.format("%.4f [%s]", value, unit.name());
    }
}
