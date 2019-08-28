package Calculus;

import Tracer.Position;

import java.util.List;

public abstract class Spline {
    private final SimpleFunction function;
    private final Position offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(Position startPosition, Position endPosition) {
        knotDistance = calcKnotDistance(startPosition, endPosition);
        offset = calcOffset(startPosition, endPosition);
        function = new SimpleFunction(getFunctionConstants(startPosition, endPosition));
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

    private double calcArcLength(Position startPosition, Position endPosition) {
        double startLength = getArcLengthAt(startPosition.getX());
        double endLength = getArcLengthAt(endPosition.getX());

        return endLength - startLength;
    }

    private double getArcLengthAt(double t) {
        return Math.sqrt(1 + Math.pow(function.derivative().at(t), 2));
    }

    public double getKnotDistance() {
        return knotDistance;
    }

    public Position getOffset() {
        return offset;
    }

    public double getLength() {
        return arcLength;
    }
}
