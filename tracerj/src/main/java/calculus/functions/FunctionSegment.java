package calculus.functions;

import calculus.segments.Segment;

public class FunctionSegment  implements MathFunction, Segment {
    private final MathFunction function;
    private final double start;
    private final double end;

    public FunctionSegment(MathFunction function, double start, double end) {
        this.function = function;
        this.start = start;
        this.end = end;
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
    public MathFunction derive() {
        return function.derive();
    }

    @Override
    public double applyAsDouble(double v) {
        return function.applyAsDouble(v);
    }
}
