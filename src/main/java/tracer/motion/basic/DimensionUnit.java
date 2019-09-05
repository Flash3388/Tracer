package tracer.motion.basic;

public enum DimensionUnit {
    MILLIMETERS,
    CENTIMETERS,
    METERS;

    public long conversionMultiplier(DimensionUnit unit) {
        switch (this) {
            case MILLIMETERS: return toMillimeters(unit);
            case CENTIMETERS: return toCentimeters(unit);
            case METERS: return toMeters(unit);
            default: return -1;
        }
    }

    private long toMillimeters(DimensionUnit unit) {
        switch (unit) {
            case MILLIMETERS: return 1;
            case CENTIMETERS: return 0.1;
            case METERS: return 0.001;
            default: return -1;
        }
    }

    private long toCentimeters(DimensionUnit unit) {
        switch (unit) {
            case MILLIMETERS: return 10;
            case CENTIMETERS: return 1;
            case METERS: return 0.01;
            default: return -1;
        }
    }

    private long toMeters(DimensionUnit unit) {
        switch (unit) {
            case MILLIMETERS: return 1000;
            case CENTIMETERS: return 100;
            case METERS: return 1;
            default: return -1;
        }
    }
}
