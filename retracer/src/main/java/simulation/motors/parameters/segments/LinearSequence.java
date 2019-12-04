package simulation.motors.parameters.segments;

import calculus.segments.SegmentSequence;

import java.util.List;

public class LinearSequence extends SegmentSequence<LineSegment> {
    public LinearSequence(List<LineSegment> segments) {
        super(segments);
    }

    public double at(double x) {
        return correspondingSegment(x).at(x);
    }
}
