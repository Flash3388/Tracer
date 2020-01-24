package tracer.units.time;

public enum TimeUnit {
    NANOSECONDS(TimeUnitScale.NANOSECONDS_TO_SECONDS_SCALE),
    MICROSECONDS(TimeUnitScale.MICROSECONDS_TO_SECONDS_SCALE),
    MILLISECONDS(TimeUnitScale.MILLISECONDS_TO_SECONDS_SCALE),
    SECONDS(1),
    MINUTES(TimeUnitScale.MINUTES_TO_SECONDS_SCALE),
    HOURS(TimeUnitScale.HOURS_TO_SECONDS_SCALE),
    DAYS(TimeUnitScale.DAYS_TO_SECONDS_SCALE),
    WEEKS(TimeUnitScale.WEEKS_TO_SECONDS_SCALE);

    private final double toSecondsScale;

    TimeUnit(double toSecondsScale) {
        this.toSecondsScale = toSecondsScale;
    }

    public double toUnit(TimeUnit unit, double val) {
        return val * conversionValue(unit);
    }

    private double conversionValue(TimeUnit unit) {
        return toSecondsScale/unit.toSecondsScale;
    }
}
