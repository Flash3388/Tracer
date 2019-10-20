package tracer.motion.basic.units;

import java.util.HashMap;
import java.util.Map;

public enum DistanceUnit {
    NANOMETERS,
    MICROMETER,
    MILLIMETERS,
    CENTIMETERS,
    METERS,
    KILOMETERS;

    private static final long NANO_SCALE   = 1L;
    private static final long MICRO_SCALE  = 1000L * NANO_SCALE;
    private static final long MILLI_SCALE  = 1000L * MICRO_SCALE;
    private static final long CENTI_SCALE  = 10L * MILLI_SCALE;
    private static final long METERS_SCALE  = 100L * CENTI_SCALE;
    private static final long KILO_SCALE  = 1000L * METERS_SCALE;

    private static final Map<DistanceUnit, Long> CONVERSIONS = new HashMap<>();

    public long convert(long distance, DistanceUnit unit) {
        putConversions();

        return (long) (distance * ((double) CONVERSIONS.get(this) / CONVERSIONS.get(unit)));
    }

    private void putConversions() {
        CONVERSIONS.put(NANOMETERS, NANO_SCALE);
        CONVERSIONS.put(MICROMETER, MICRO_SCALE);
        CONVERSIONS.put(MILLIMETERS, MILLI_SCALE);
        CONVERSIONS.put(CENTIMETERS, CENTI_SCALE);
        CONVERSIONS.put(METERS, METERS_SCALE);
        CONVERSIONS.put(KILOMETERS, KILO_SCALE);
    }
}
