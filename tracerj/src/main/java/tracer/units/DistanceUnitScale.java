package tracer.units;

public class DistanceUnitScale {
    public static final int FEET_IN_A_YARD = 3;
    public static final int INCHES_IN_A_FEET = 12;
    public static final int CENTI_RATIO = 100;
    public static final int MILLI_RATIO = CENTI_RATIO * 10;

    public static final double MILES_TO_METERS_SCALE = 1609.344;
    public static final double KILOMETERS_TO_METERS_SCALE = 1000.0;
    public static final double YARDS_TO_METERS_SCALE = 0.9144;
    public static final double FEET_TO_METERS_SCALE = YARDS_TO_METERS_SCALE / FEET_IN_A_YARD;
    public static final double INCHES_TO_METERS_SCALE = FEET_TO_METERS_SCALE / INCHES_IN_A_FEET;
    public static final double CENTIMETERS_TO_METERS_SCALE = 1.0 / CENTI_RATIO;
    public static final double MILLIMETERS_TO_METERS_SCALE = 1.0 / MILLI_RATIO;
    public static final double MICROMETERS_TO_METERS_SCALE = MILLIMETERS_TO_METERS_SCALE / MILLI_RATIO;
    public static final double NANOMETERS_TO_METERS_SCALE = MICROMETERS_TO_METERS_SCALE / MILLI_RATIO;
}
