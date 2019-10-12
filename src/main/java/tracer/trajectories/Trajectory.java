package tracer.trajectories;

import calculus.splines.LengthOutsideOfFunctionBoundsException;
import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import tracer.motion.Waypoint;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Trajectory {
    private final List<Spline> splines;
    private final SplineFactory hermiteFactory;
    private final double trajectoryLength;

    public Trajectory(SplineType splineType, Waypoint... path) {
        this(splineType, Arrays.asList(path));
    }

    public Trajectory(SplineType splineType, List<Waypoint> path) {
        hermiteFactory = new SplineFactory();
        splines = generateTrajectory(path, splineType);
        trajectoryLength = calcTrajectoryLength();
    }

    private List<Spline> generateTrajectory(List<Waypoint> path, SplineType splineType) {
        return IntStream.range(0, path.size())
                .skip(1)
                .mapToObj(positionIndex -> hermiteFactory.get(splineType, path.get(positionIndex - 1), path.get(positionIndex)))
                .collect(Collectors.toList());
    }

    private double calcTrajectoryLength() {
        return splines.stream()
                .mapToDouble(Spline::length)
                .sum();
    }

    public double length() {
        return trajectoryLength;
    }

    public double angleAt(double length) {
        try {
            return getCorrespondingSpline(length).angleAt(length);
        } catch (LengthOutsideOfFunctionBoundsException e) {
            System.out.println(e.getMessage());
            return 0.0;
        }
    }

    private Spline getCorrespondingSpline(double length) throws LengthOutsideOfFunctionBoundsException {
        Optional<Spline> result = splines.stream()
                .filter(spline ->  distanceUntil(spline) >= length)
                .findFirst();
        if(!result.isPresent())
            throw new LengthOutsideOfFunctionBoundsException();
        return result.get();
    }

    private double distanceUntil(Spline targetSpline) {
        return splines.stream()
                .limit(splines.indexOf(targetSpline) + 1)
                .mapToDouble(Spline::length)
                .sum();
    }
}
