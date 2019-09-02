package tracer;

public class PositionPair {
    private final Position first;
    private final Position second;

    public PositionPair(Position first, Position second) {
        this.first = first;
        this.second = second;
    }

    public Position getFirst() {
        return first;
    }

    public Position getSecond() {
        return second;
    }
}
