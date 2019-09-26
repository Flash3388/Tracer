package tracer.motion;

import com.flash3388.flashlib.time.Time;

public class Position {
    private final Time timing;
    private final double distance;
    private final double angle;

    public static Position centimetersDegrees(Time timing, double distance, double angle) {
        return new Position(timing, distance, angle);
    }

    private Position(Time timing, double distanceCentimeters, double angleDegrees) {
        this.timing = timing;
        this.distance = distanceCentimeters;
        this.angle = angleDegrees;
    }

    public static Position start() {
        return new Position(Time.milliseconds(0), 0, 0);
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
