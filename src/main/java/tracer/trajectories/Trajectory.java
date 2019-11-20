package tracer.trajectories;

import calculus.splines.LengthOutsideOfFunctionBoundsException;
import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Trajectory {
    private final List<Spline> splines;
    private final SplineFactory hermiteFactory;

    public Trajectory(SplineType splineType, Waypoint... path) {
        this(splineType, Arrays.asList(path));
    }

    public Trajectory(SplineType splineType, List<Waypoint> path) {
        hermiteFactory = new SplineFactory();
        splines = generateTrajectory(path, splineType);
    }

    public double length() {
        return splines.get(splines.size()-1).absoluteLength();
    }

    public double angleAt(double length) {
        try {
            return getCorrespondingSpline(length).angleRadAt(length);
        } catch (LengthOutsideOfFunctionBoundsException e) {
            System.out.println(e.getMessage());
            return 0.0;
        }
    }

    private List<Spline> generateTrajectory(List<Waypoint> path, SplineType splineType) {
        List<Spline> result = new ArrayList<>();
        result.add(hermiteFactory.get(splineType, path.get(0), path.get(1), 0));

        IntStream.range(0, path.size())
                .skip(2)
                .forEach(positionIndex -> result.add(hermiteFactory.get(splineType, path.get(positionIndex-1), path.get(positionIndex), result.get(positionIndex-2).absoluteLength())));

        return result;
    }

    private Spline getCorrespondingSpline(double length) throws LengthOutsideOfFunctionBoundsException {
        for (Spline spline: splines)
            if(spline.absoluteLength() >= length && length >= spline.startLength())//might not need the second part since the list is sorted from start to finish
                return spline;

        throw new LengthOutsideOfFunctionBoundsException();
    }
}
