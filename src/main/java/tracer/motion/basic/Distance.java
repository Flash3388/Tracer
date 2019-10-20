package tracer.motion.basic;

public class Distance {
    private final long value;
    private final DistanceUnit unit;

    public static Distance millimeters(long value) {
        return new Distance(value, DistanceUnit.MILLIMETERS);
    }

    public static Distance centimeters(long value) {
        return new Distance(value, DistanceUnit.CENTIMETERS);
    }

    public static Distance meters(long value) {
        return new Distance(value, DistanceUnit.METERS);
    }

    public static Distance kilometers(long value) {
        return new Distance(value, DistanceUnit.KILOMETERS);
    }

    public Distance(long value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public long value() {
        return value;
    }

    public DistanceUnit unit() {
        return unit;
    }

    public Distance to(DistanceUnit newUnit) {
        if(newUnit.equals(unit))
            return this;
        return new Distance(unit.convert(value, newUnit), newUnit);
    }

    public Distance add(Distance distance) {
        DistanceUnit smallestUnit = smallestUnit(distance);
        long sum = this.to(smallestUnit).value() + distance.to(smallestUnit).value();

        return new Distance(sum, smallestUnit);
    }

    public Distance sub(Distance distance) {
        DistanceUnit smallestUnit = smallestUnit(distance);
        long result = this.to(smallestUnit).value() - distance.to(smallestUnit).value();

        return new Distance(result, smallestUnit);
    }

    private DistanceUnit smallestUnit(Distance distance) {
        return distance.unit().compareTo(unit) < 0 ? distance.unit() : unit;
    }
}
