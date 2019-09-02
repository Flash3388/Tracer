package Tracer;

import calculus.Spline;
import calculus.SplineFactory;
import calculus.SplineType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Trajectory {
    private final List<Spline> splines;
    private final SplineFactory hermiteFactory;
    private final double trajectoryLength;

    public Trajectory(SplineType splineType, List<Position> path) {
        hermiteFactory = new SplineFactory();
        splines = generateTrajectory(path, splineType);
        trajectoryLength = calcTrajectoryLength();
    }

    public Trajectory(SplineType splineType, Position... path) {
        this(splineType, Arrays.asList(path));
    }

    private List<Spline> generateTrajectory(List<Position> path, SplineType splineType) {
        return IntStream.range(0, path.size())
                .skip(1)
                .mapToObj(positionIndex -> hermiteFactory.getSpline(splineType, path.get(positionIndex), path.get(positionIndex)))
                .collect(Collectors.toList());
    }

    private double calcTrajectoryLength() {
        return splines.stream()
                .mapToDouble(Spline::getLength)
                .sum();
    }

    public double getLength() {
        return trajectoryLength;
    }
}
