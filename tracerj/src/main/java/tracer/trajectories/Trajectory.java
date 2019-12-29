package tracer.trajectories;

import calculus.segments.SegmentSequence;
import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.time.Time;
import util.TimeConversion;

import java.util.*;
import java.util.stream.IntStream;

public class Trajectory extends SegmentSequence<Spline> {
    public Trajectory(SplineType splineType, double maxVelocityMetersPerSecond, Time maxCycleTime, Waypoint... path) {
        this(splineType, maxVelocityMetersPerSecond, maxCycleTime, Arrays.asList(path));
    }

    public Trajectory(SplineType splineType, double maxVelocityMetersPerSecond, Time maxCycleTime, List<Waypoint> path) {
        super(new ArrayDeque<>(generateTrajectory(path, splineType, maxVelocityMetersPerSecond, maxCycleTime)));
    }

    public double angleRadAt(double length) {
        return correspondingSegment(length).angleRadAt(length);
    }

    private static List<Spline> generateTrajectory(List<Waypoint> path, SplineType splineType, double maxVelocityMetersPerSecond, Time maxCycleTime) {
        double maxDistancePassedPerCycle = maxVelocityMetersPerSecond * TimeConversion.toSeconds(maxCycleTime);
        List<Spline> result = new ArrayList<>();
        SplineFactory hermiteFactory = new SplineFactory();
        result.add(hermiteFactory.create(splineType, path.get(0), path.get(1), 0, maxDistancePassedPerCycle));

        IntStream.range(0, path.size())
                .skip(2)
                .forEach(positionIndex -> result.add(hermiteFactory.create(splineType, path.get(positionIndex-1), path.get(positionIndex), result.get(positionIndex-2).end(), maxDistancePassedPerCycle)));

        return result;
    }
}
