package tracer.motion;

import com.flash3388.flashlib.time.Time;

public class Position {
    private final Time timing;
    private final double distance;
    private final double angle;

    public static Position start() {
        return new Position(Time.milliseconds(0), 0, 0);
    }

    public Position(Time timing, double distanceCentimeters, double angleDegrees) {
        this.timing = timing;
        this.distance = distanceCentimeters;
        this.angle = angleDegrees;
    }

    public Time getTiming() {
        return timing;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }
}
