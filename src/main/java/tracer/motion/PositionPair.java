package tracer.motion;

public class PositionPair {
    private final Waypoint first;
    private final Waypoint second;

    public PositionPair(Waypoint first, Waypoint second) {
        this.first = first;
        this.second = second;
    }

    public Waypoint getFirst() {
        return first;
    }

    public Waypoint getSecond() {
        return second;
    }
}
