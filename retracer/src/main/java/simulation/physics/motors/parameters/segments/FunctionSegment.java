package simulation.physics.motors.parameters.segments;

import calculus.functions.MathFunction;
import calculus.segments.Segment;

public class FunctionSegment implements Segment<MathFunction> {
    private final MathFunction linear;
    private final double start;
    private final double end;

    public FunctionSegment(MathFunction linear, double start, double end) {
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

    @Override
    public MathFunction get() {
        return linear;
    }
}
