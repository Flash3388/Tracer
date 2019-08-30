package calculus;

import Tracer.Position;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Spline {
    public static final int SAMPLES_FAST = 1000;
    public static final int SAMPLES_LOW = SAMPLES_FAST * 10;
    public static final int SAMPLES_HIGH = SAMPLES_LOW * 10;

    private final PolynomialFunction function;
    private final Position offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(Position startPosition, Position endPosition) {
        knotDistance = calcKnotDistance(startPosition, endPosition);
        offset = calcOffset(startPosition, endPosition);

        function = PolynomialFunction.fromConstants(getFunctionConstants(startPosition, endPosition));

        arcLength = calcArcLength(SAMPLES_HIGH);
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

    private double calcArcLength(int sampleCount) {
        return IntStream.range(0, sampleCount)
                .mapToDouble(index -> calcCurrentArcLength(index/(double)sampleCount, sampleCount))
                .sum() * knotDistance;
    }

    private double calcCurrentArcLength(double percentage, int sampleCount) {
        return getArcLengthPercentage(percentage)/sampleCount;
    }

    private double getArcLengthPercentage(double percentage) {
        return getArcLengthAt(knotDistance * percentage);
    }

    private double getArcLengthAt(double x) {
        return Math.sqrt(function.derivative().square().at(x)+1.0);
    }

    public double getKnotDistance() {
        return knotDistance;
    }

    public Position getOffset() {
        return offset;
    }

    public double valueAt(double x) {
        return function.at(x);
    }

    public double getLength() {
        return arcLength;
    }
}
