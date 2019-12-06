package simulation.physics.motors.parameters.segments;

import calculus.functions.polynomial.Linear;
import calculus.segments.Segment;

public class LineSegment implements Segment {
    private final Linear linear;
    private final double start;
    private final double end;

    public LineSegment(Linear linear, double start, double end) {
        this.linear = linear;
        this.start = start;
        this.end = end;
    }

    public double at(double value) {
        return linear.applyAsDouble(value);
    }

    @Override
    public double start() {
        return start;
    }

    @Override
    public double end() {
        return end;
    }
}
