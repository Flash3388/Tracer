package simulation.physics.motors.parameters.segments;

import calculus.functions.MathFunction;
import calculus.segments.Segment;
import calculus.segments.SegmentSequence;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinearSequence extends MathFunction {
    private final SegmentSequence<FunctionSegment> sequence;

    public LinearSequence(FunctionSegment... segments) {
        this(Arrays.asList(segments));
    }

    public LinearSequence(List<FunctionSegment> segments) {
        sequence = new SegmentSequence<>(segments);
    }

    @Override
    public MathFunction derive() {
        return new LinearSequence();
    }

    @Override
    public double applyAsDouble(double operand) {
        return sequence.correspondingSegment(operand).at(operand);
    }

    private List<FunctionSegment> deriveList() {
        List<Double> starts = extractStarts();
        List<Double> ends = extractEnds();
        List<MathFunction> functions = extractFunctions();

        return IntStream.range(0, functions.size())
                .mapToObj(i -> new FunctionSegment(functions.get(i), starts.get(i), ends.get(i)))
                .collect(Collectors.toList());
    }

    private List<Double> extractStarts() {
        return sequence.stream()
                .map(Segment::start)
                .collect(Collectors.toList());
    }

    private List<Double> extractEnds() {
        return sequence.stream()
                .map(Segment::end)
                .collect(Collectors.toList());
    }

    private List<MathFunction> extractFunctions() {
        return sequence.stream()
                .map(Segment::get)
                .collect(Collectors.toList());
    }
}
