package tracer.motion;

import tracer.motion.basic.Distance;

public class Waypoint {
    private final Distance x;
    private final Distance y;
    private final double heading;

    public Waypoint(Distance x, Distance y, double headingRadians) {
        this.x = x;
        this.y = y;
        this.heading = headingRadians;
    }

    public Distance x() {
        return x;
    }

    public Distance y() {
        return y;
    }

    public double heading() {
        return heading;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Heading: " + heading;
    }
}
