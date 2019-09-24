package calculus;

import calculus.functions.PolynomialFunction;
import tracer.motion.Position;

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

    public double knotDistance() {
        return knotDistance;
    }

    public Position offset() {
        return offset;
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) {
        double percentage = getPercentageAtLength(length);

        return Math.atan(function.at(percentage)/percentage) + offset.getHeadingDegrees();
    }

    protected abstract List<Double> getFunctionConstants(Position startPosition, Position endPosition);

    private double calcKnotDistance(Position start, Position end) {
        return Math.sqrt( Math.pow(end.x()-start.x(), 2) + Math.pow(end.y() - start.y(), 2));
    }

    private Position calcOffset(Position start, Position end) {
        return new Position(start.x(),
                start.y(),
                calcAngleOffset(start, end));
    }

    private double calcAngleOffset(Position start, Position end) {
        return Math.atan2(end.y() - start.y(), end.x() - start.x());
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
        return Math.sqrt(Math.pow(function.derive().at(x), 2) + 1.0);
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
