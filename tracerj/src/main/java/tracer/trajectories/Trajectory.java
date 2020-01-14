package tracer.trajectories;

import calculus.segments.SegmentSequence;
import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;

import java.util.*;
import java.util.stream.IntStream;

public class Trajectory extends SegmentSequence<Spline> {
    public Trajectory(SplineType splineType, Waypoint... path) {
        this(splineType, Arrays.asList(path));
    }

    public Trajectory(SplineType splineType, List<Waypoint> path) {
        super(generateTrajectory(path, splineType));
    }

    public double angleRadAt(double length) {
        length = Math.abs(length);
        return correspondingSegment(length).angleRadAt(length);
    }

    private static Deque<Spline> generateTrajectory(List<Waypoint> path, SplineType splineType) {
        List<Spline> result = new ArrayList<>();
        SplineFactory hermiteFactory = new SplineFactory();
        result.add(hermiteFactory.create(splineType, path.get(0), path.get(1), 0));

        IntStream.range(0, path.size())
                .skip(2)
                .forEach(positionIndex -> result.add(hermiteFactory.create(splineType, path.get(positionIndex-1), path.get(positionIndex), result.get(positionIndex-2).end())));

        return new ArrayDeque<>(result);
    }
}
