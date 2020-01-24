package tracer.units.time;

import static tracer.units.generic.GenericUnitScale.MILLI_SCALE;

public class TimeUnitScale {
    public static final int SECONDS_IN_A_MINUTE = 60;
    public static final int MINUTES_IN_AN_HOUR = SECONDS_IN_A_MINUTE;
    public static final int HOURS_IN_A_DAY = 24;
    public static final int DAYS_IN_A_WEEK = 7;

    public static final double MINUTES_TO_SECONDS_SCALE = SECONDS_IN_A_MINUTE;
    public static final double HOURS_TO_SECONDS_SCALE = MINUTES_TO_SECONDS_SCALE * MINUTES_IN_AN_HOUR;
    public static final double DAYS_TO_SECONDS_SCALE = HOURS_TO_SECONDS_SCALE * HOURS_IN_A_DAY;
    public static final double WEEKS_TO_SECONDS_SCALE = DAYS_TO_SECONDS_SCALE * DAYS_IN_A_WEEK;
    public static final double MILLISECONDS_TO_SECONDS_SCALE = 1.0 / MILLI_SCALE;
    public static final double MICROSECONDS_TO_SECONDS_SCALE = MILLISECONDS_TO_SECONDS_SCALE / MILLI_SCALE;
    public static final double NANOSECONDS_TO_SECONDS_SCALE = MICROSECONDS_TO_SECONDS_SCALE / MILLI_SCALE;
}
