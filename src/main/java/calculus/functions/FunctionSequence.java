package calculus.functions;

import calculus.segments.SegmentSequence;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class FunctionSequence extends SegmentSequence<FunctionSegment> implements DoubleUnaryOperator {
    public FunctionSequence(FunctionSegment... segments) {
        this(Arrays.asList(segments));
    }

    public FunctionSequence(List<FunctionSegment> segments) {
        super(new ArrayDeque<>(segments));
    }

    @Override
    public double applyAsDouble(double operand) {
        return correspondingSegment(operand).applyAsDouble(operand);
    }
}
