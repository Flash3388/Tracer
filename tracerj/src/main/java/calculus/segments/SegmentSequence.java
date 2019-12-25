package calculus.segments;

import java.util.Deque;
import java.util.stream.Stream;

public class SegmentSequence<S extends Segment> implements Segment {
    private final Deque<S> segments;

    public SegmentSequence(Deque<S> segments) {
        this.segments = segments;

        if(segments.isEmpty())
            throw new IllegalArgumentException("deque of segments must contain at least one Segment");
    }

    public S correspondingSegment(double value) {
        for (S segment: segments)
            if(segment.end() >= value && value >= segment.start())
                return segment;

        throw new IllegalArgumentException(String.format("Value %f is outside of the segment sequence ", value));
    }

    @Override
    public double start() {
        //noinspection ConstantConditions
        return segments.peek().start();
    }

    @Override
    public double end() {
        //noinspection ConstantConditions
        return segments.peekLast().end();
    }

    public Stream<S> stream() {
        return segments.stream();
    }
}
