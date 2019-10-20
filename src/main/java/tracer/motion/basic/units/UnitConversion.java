package tracer.motion.basic.units;

import com.flash3388.flashlib.time.Time;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;

import java.util.concurrent.TimeUnit;

public class UnitConversion {
    public static DistanceUnit smallestDistanceUnit(DistanceUnit first, DistanceUnit other) {
        return first.compareTo(other) < 0 ? first : other;
    }

    public static TimeUnit smallestTimeUnit(TimeUnit first, TimeUnit other) {
        return other.compareTo(first) < 0 ? other : first;
    }

    public static double toSeconds(Time t) {
        return t.valueAsMillis() / 1000.0;
    }

    public static double toCentimeters(Distance d) {
        return d.valueAsMicrometers() / 10000.0;
    }

    public static double toCentimetersPerSecond(Velocity v) {
        return v.valueAsMicrometersPerSecond() / 10000.0;
    }

    public static double toCentimetersPerSecondPerSecond(Acceleration a) {
        return a.valueAsMicrometersPerSecondPerSecond() / 10000.0;
    }

    public static double toCentimetersPerSecondPerSecondPerSecond(Jerk j) {
        return j.valueAsMicrometersPerSecondPerSecondPerSecond() / 10000.0;
    }
}
