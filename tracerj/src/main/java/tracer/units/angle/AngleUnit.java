package tracer.units.angle;

public enum AngleUnit {
    DEGREES(1),
    RADIANS(AngleUnitScale.RADIAN_TO_DEGREE_SCALE);

    private final double toDegreesScale;

    AngleUnit(double toDegreesScale) {
        this.toDegreesScale = toDegreesScale;
    }

    public double toUnit(AngleUnit unit, double val) {
        return val * conversionValue(unit);
    }

    private double conversionValue(AngleUnit unit) {
        return toDegreesScale/unit.toDegreesScale;
    }
}
