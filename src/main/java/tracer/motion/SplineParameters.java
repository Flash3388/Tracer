package tracer.motion;

public class SplineParameters {
    private final Waypoint first;
    private final Waypoint second;
    private final double startLength;

    public SplineParameters(Waypoint first, Waypoint second, double startLength) {
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

    public double getStartLength() {
        return startLength;
    }
}
