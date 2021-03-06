package calculus.splines;

import calculus.splines.parameters.SplineParameters;
import calculus.splines.parameters.Waypoint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SplineFactory {
    private final Map<SplineType, Function<SplineParameters, Spline>> splineConstructors;

    public SplineFactory() {
        splineConstructors = new HashMap<>();

        splineConstructors.put(SplineType.CUBIC_HERMITE, parameters -> createCubicSpline(parameters.start(),parameters.end(), parameters.startLength()));
        splineConstructors.put(SplineType.QUINTIC_HERMITE, parameters -> createQuinticSpline(parameters.start(), parameters.end(), parameters.startLength()));
    }

    public Spline create(SplineType type, Waypoint start, Waypoint end, double startLength) {
        return splineConstructors.get(type).apply(new SplineParameters(start, end, startLength));
    }

    private HermiteCubicSpline createCubicSpline(Waypoint start, Waypoint end, double startLength) {
        return new HermiteCubicSpline(start, end, startLength);
    }

    private HermiteQuinticSpline createQuinticSpline(Waypoint start, Waypoint end, double startLength) {
        return new HermiteQuinticSpline(start, end, startLength);
    }
}
