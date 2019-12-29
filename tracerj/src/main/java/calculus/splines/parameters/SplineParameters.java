package calculus.splines.parameters;

public class SplineParameters {
    private final Waypoint start;
    private final Waypoint end;
    private final double startLength;
    private final double maxDistancePassedPerCycle;

    public SplineParameters(Waypoint start, Waypoint end, double startLength, double maxDistancePassedPerCycle) {
        this.start = start;
        this.end = end;
        this.startLength = startLength;
        this.maxDistancePassedPerCycle = maxDistancePassedPerCycle;
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

    public double maxDistancePassedPerCycle() {
        return maxDistancePassedPerCycle;
    }
}
