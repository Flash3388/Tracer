package tracer.units.angle;

import tracer.units.exceptions.NotMatchingUnitsException;
import tracer.units.generic.Unit;

public class Angle implements Unit {
    private final double value;
    private final AngleUnit unit;

    public Angle(double value, AngleUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Angle degrees(double value) {
        return new Angle(value, AngleUnit.DEGREES);
    }

    public static Angle radians(double value) {
        return new Angle(value, AngleUnit.RADIANS);
    }

    @Override
    public double value() {
        return value;
    }

    public double valueAsDegrees() {
        return unit.toUnit(AngleUnit.DEGREES, value);
    }

    public double valueAsRadians() {
        return unit.toUnit(AngleUnit.RADIANS, value);
    }

    public AngleUnit unit() {
        return unit;
    }

    public Angle add(Unit other) {
        if (other instanceof Angle)
            return add((Angle)other);
        else
            throw new NotMatchingUnitsException();
    }

    public Angle add(Angle other) {
        other = other.toUnit(unit);
        return new Angle(value + other.value(), unit);
    }

    @Override
    public Unit sub(Unit other) {
        if (other instanceof Angle)
            return sub((Angle)other);
        else
            throw new NotMatchingUnitsException();
    }

    public Angle sub(Angle other) {
        other = other.toUnit(unit);
        return new Angle(value - other.value(), unit);
    }

    public Angle toUnit(AngleUnit newUnit) {
        return new Angle(unit.toUnit(newUnit, value), newUnit);
    }
}
