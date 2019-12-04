package calculus.segments;

import java.util.List;

public class SegmentSequence<s extends Segment> implements Segment {
    private final List<s> segments;

    public SegmentSequence(List<s> segments) {
        this.segments = segments;
    }

    protected s correspondingSegment(double value) {
        for (s segment: segments)
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
}
