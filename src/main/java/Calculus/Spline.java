package Calculus;

import Tracer.Position;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Spline {
    private final List<Variable> function;
    private final double knotDistance;
    private final double angleOffset;

    protected Spline(Position startPosition, Position endPosition) {
        function = generateFunction(startPosition, endPosition);
        knotDistance = calcKnotDistance(startPosition, endPosition);
        angleOffset = calcAngleOffset(startPosition, endPosition);
    }

    private List<Variable> generateFunction(Position startPosition, Position endPosition) {
        List<Double> functionConstants = getFunctionConstants(startPosition, endPosition);

        return IntStream.range(0, functionConstants.size())
                .mapToObj(constantIndex -> new Variable(functionConstants.get(constantIndex), functionConstants.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    protected abstract List<Double> getFunctionConstants(Position startPosition, Position endPosition);

    private double calcKnotDistance(Position startPosition, Position endPosition) {
        return Math.sqrt( Math.pow(endPosition.getX()-startPosition.getX(), 2) + Math.pow(endPosition.getX() - startPosition.getX(), 2));
    }

    private double calcAngleOffset(Position startPosition, Position endPosition) {
        return Math.atan2(endPosition.getY() - startPosition.getY(), endPosition.getX() - startPosition.getX());
    }

    public double getValue(double s) {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);

        function.forEach(variable -> sum.updateAndGet(v -> v + variable.calcValue(s)));
        return sum.get();
    }

    public double getKnotDistance() {
        return knotDistance;
    }

    public double getAngleOffset() {
        return angleOffset;
    }
}
