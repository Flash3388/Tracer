package calculus.segments;

import java.util.List;
import java.util.stream.Stream;

public class SegmentSequence<S extends Segment> implements Segment {
    private final List<S> segments;

    public SegmentSequence(List<S> segments) {
        this.segments = segments;
    }

    public S correspondingSegment(double value) {
        for (S segment: segments)
            if(segment.end() >= value && value >= segment.start())
                return segment;

        throw new IllegalArgumentException(String.format("Value %f is outside of the segment sequence ", value));
    }

    @Override
    public double start() {
        return segments.get(0).start();
    }

    @Override
    public double end() {
        return segments.get(segments.size()-1).end();
    }

    public Stream<S> stream() {
        return segments.stream();
    }
}
