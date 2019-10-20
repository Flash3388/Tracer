package tracer.motion.basic.units;

import java.util.concurrent.TimeUnit;

public class UnitConversion {
    public static DistanceUnit smallestDistanceUnit(DistanceUnit first, DistanceUnit other) {
        return first.compareTo(other) < 0 ? first : other;
    }

    public static TimeUnit smallestTimeUnit(TimeUnit first, TimeUnit other) {
        return other.compareTo(first) < 0 ? other : first;
    }
}
