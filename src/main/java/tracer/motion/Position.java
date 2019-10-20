package tracer.motion;

import com.flash3388.flashlib.time.Time;
import tracer.motion.basic.Distance;

public class Position {
    private final Time timing;
    private final Distance distance;
    private final double angle;

    public static Position start() {
        return new Position(Time.milliseconds(0), Distance.centimeters(0), 0);
    }

    public Position(Time timing, Distance distance, double angleDegrees) {
        this.timing = timing;
        this.distance = distance;
        this.angle = angleDegrees;
    }

    public Time getTiming() {
        return timing;
    }

    public Distance getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }
}
