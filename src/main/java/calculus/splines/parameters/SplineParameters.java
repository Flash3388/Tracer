package calculus.splines.parameters;

public class SplineParameters {
    private final Waypoint start;
    private final Waypoint end;
    private final double startLength;

    public SplineParameters(Waypoint start, Waypoint end, double startLength) {
        this.start = start;
        this.end = end;
        this.startLength = startLength;
    }

    public Waypoint start() {
        return start;
    }

    public Waypoint end() {
        return end;
    }

    public double startLength() {
        return startLength;
    }
}
