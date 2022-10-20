package com.flash3388.tracer.calculus.trajectories;

import com.flash3388.tracer.calculus.segments.SegmentSequence;
import com.flash3388.tracer.calculus.splines.parameters.Waypoint;
import com.flash3388.tracer.calculus.splines.Spline;
import com.flash3388.tracer.calculus.splines.SplineFactory;
import com.flash3388.tracer.calculus.splines.SplineType;

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
