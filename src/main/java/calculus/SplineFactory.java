package calculus;

import tracer.Position;
import tracer.PositionPair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SplineFactory {
    private final Map<SplineType, Function<PositionPair, Spline>> splineConstructors;

    public SplineFactory() {
        splineConstructors = new HashMap<>();

        splineConstructors.put(SplineType.CUBIC_HERMITE, positions -> createCubicSpline(positions.getFirst(),positions.getSecond()));
        splineConstructors.put(SplineType.QUINTIC_HERMITE, positions -> createQuinticSpline(positions.getFirst(),positions.getSecond()));
    }

    private HermiteCubicSpline createCubicSpline(Position start, Position end) {
        return new HermiteCubicSpline(start, end);
    }

    private HermiteQuinticSpline createQuinticSpline(Position start, Position end) {
        return new HermiteQuinticSpline(start, end);
    }

    public Spline getSpline(SplineType type, Position start, Position end) {
        return splineConstructors.get(type).apply(new PositionPair(start, end));
    }
}
