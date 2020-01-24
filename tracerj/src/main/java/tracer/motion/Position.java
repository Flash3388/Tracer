package tracer.motion;

import com.flash3388.flashlib.time.Time;
import tracer.units.distance.Distance;

public class Position {
    private final Time timestamp;
    private final Distance distance;
    private final double angle;

    public Position(Time timestamp, Distance distanceMeters, double angleDegrees) {
        this.timestamp = timestamp;
        this.distance = distanceMeters;
        this.angle = angleDegrees;
    }

    public Time timestamp() {
        return timestamp;
    }

    public Distance distance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }
}
