package tracer.motion;

import tracer.motion.basic.Distance;

public class SplineParameters {
    private final Waypoint first;
    private final Waypoint second;
    private final Distance startLength;

    public SplineParameters(Waypoint first, Waypoint second, Distance startLength) {
        this.first = first;
        this.second = second;
        this.startLength = startLength;
    }

    public Waypoint getFirst() {
        return first;
    }

    public Waypoint getSecond() {
        return second;
    }

    public Distance getStartLength() {
        return startLength;
    }
}
