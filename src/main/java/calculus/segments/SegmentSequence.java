package calculus.segments;

import java.util.ArrayList;
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
            if(value <= segment.end())
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

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SegmentSequence && equals((SegmentSequence)obj);
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(SegmentSequence sequence) {
        if(segments.size() != sequence.segments.size())
            return false;
        return new ArrayList<>(segments).equals(new ArrayList<>(sequence.segments));
    }

    @Override
    public String toString() {
        return segments.toString();
    }
}
