package tracer.units.distance;

public enum DistanceUnit {
    NANOMETERS(DistanceUnitScale.NANOMETERS_TO_METERS_SCALE),
    MICROMETERS(DistanceUnitScale.MICROMETERS_TO_METERS_SCALE),
    MILLIMETERS(DistanceUnitScale.MILLIMETERS_TO_METERS_SCALE),
    CENTIMETERS(DistanceUnitScale.CENTIMETERS_TO_METERS_SCALE),
    INCHES(DistanceUnitScale.INCHES_TO_METERS_SCALE),
    FEET(DistanceUnitScale.FEET_TO_METERS_SCALE),
    YARDS(DistanceUnitScale.YARDS_TO_METERS_SCALE),
    METERS(1),
    KILOMETERS(DistanceUnitScale.KILOMETERS_TO_METERS_SCALE),
    MILES(DistanceUnitScale.MILES_TO_METERS_SCALE);

    private final double toMetersScale;

    DistanceUnit(double toMetersScale) {
        this.toMetersScale = toMetersScale;
    }

    public double toUnit(DistanceUnit unit, double val) {
        return val * conversionValue(unit);
    }

    private double conversionValue(DistanceUnit unit) {
        return toMetersScale/unit.toMetersScale;
    }
}
