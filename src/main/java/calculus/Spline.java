package calculus;

import tracer.Position;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public abstract class Spline {
    public static final int SAMPLES_FAST = 1000;
    public static final int SAMPLES_LOW = SAMPLES_FAST * 10;
    public static final int SAMPLES_HIGH = SAMPLES_LOW * 10;

    private final PolynomialFunction function;
    private final Position offset;
    private final double knotDistance;
    private final double arcLength;

    private final int sampleCount;

    public Spline(Position startPosition, Position endPosition) {
        this(startPosition, endPosition, SAMPLES_HIGH);
    }

    public Spline(Position startPosition, Position endPosition, int sampleCount) {
        this.sampleCount = sampleCount;

        knotDistance = calcKnotDistance(startPosition, endPosition);
        offset = calcOffset(startPosition, endPosition);

        function = PolynomialFunction.fromConstants(getFunctionConstants(startPosition, endPosition));

        arcLength = calcArcLength(sampleCount);
    }

    protected abstract List<Double> getFunctionConstants(Position startPosition, Position endPosition);

    private double calcKnotDistance(Position startPosition, Position endPosition) {
        return Math.sqrt( Math.pow(endPosition.getX()-startPosition.getX(), 2) + Math.pow(endPosition.getY() - startPosition.getY(), 2));
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
        return Math.sqrt(Math.pow(function.derivative().at(x), 2) + 1.0);
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

    public double getAngleAt(double length) {
        double percentage = getPercentageAtLength(length);

        return Math.atan(function.at(percentage)/percentage) + offset.getHeadingDegrees();
    }

    private double getPercentageAtLength(double length) {//need to check it
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);

        return IntStream.range(0, sampleCount)
                .filter(index -> {
                    sum.set(sum.get() + calcCurrentArcLength(index/(double)sampleCount, sampleCount) * knotDistance);
                    return sum.get() >= length;
                })
                .limit(1)
                .mapToDouble(index -> index/(double)sampleCount)
                .sum();
    }
}
