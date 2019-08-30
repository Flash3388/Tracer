package calculus;

import Tracer.Position;

import java.util.List;

public abstract class Spline {
    private final PolynomialFunction function;
    private final Position offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(Position startPosition, Position endPosition) {
        knotDistance = calcKnotDistance(startPosition, endPosition);
        offset = calcOffset(startPosition, endPosition);

        function = PolynomialFunction.fromConstants(getFunctionConstants(startPosition, endPosition));

        arcLength = calcArcLength(startPosition, endPosition);
    }

    protected abstract List<Double> getFunctionConstants(Position startPosition, Position endPosition);

    private double calcKnotDistance(Position startPosition, Position endPosition) {
        return Math.sqrt( Math.pow(endPosition.getX()-startPosition.getX(), 2) + Math.pow(endPosition.getX() - startPosition.getX(), 2));
    }

    private Position calcOffset(Position startPosition, Position endPosition) {
        return new Position(startPosition.getX(),
                startPosition.getY(),
                calcAngleOffset(startPosition, endPosition));
    }

    private double calcAngleOffset(Position startPosition, Position endPosition) {
        return Math.atan2(endPosition.getY() - startPosition.getY(), endPosition.getX() - startPosition.getX());
    }

    public double calcArcLength(Position startPosition, Position endPosition) {
        return function.calcArcLength(startPosition.getX(), endPosition.getX());
    }

    public double getKnotDistance() {
        return knotDistance;
    }

    public Position getOffset() {
        return offset;
    }

    public double valueAt(double t) {
        return function.at(t);
    }

    public double getLength() {
        return arcLength;
    }
}
