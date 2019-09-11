package tracer.motion;

import com.flash3388.flashlib.time.Time;

public class PhysicalPosition {
    private final Time timing;
    private final double distance;
    private final double angle;

    public PhysicalPosition(Time timing, double distanceCentimeters, double angleDegrees) {
        this.timing = timing;
        this.distance = distanceCentimeters;
        this.angle = angleDegrees;
    }

    public static PhysicalPosition start() {
        return new PhysicalPosition(Time.milliseconds(0), 0, 0);
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
