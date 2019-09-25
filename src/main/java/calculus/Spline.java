package calculus;

import calculus.functions.PolynomialFunction;
import com.jmath.ExtendedMath;
import tracer.motion.Position;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Spline {
    private static final int SAMPLES_FAST = 1000;
    private static final int SAMPLES_LOW = SAMPLES_FAST * 10;
    private static final int SAMPLES_HIGH = SAMPLES_LOW * 10;

    private final PolynomialFunction function;
    private final Position offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(PolynomialFunction function, Position startPosition, Position endPosition) {
        this.function = function;

        knotDistance = calcKnotDistance(startPosition, endPosition);
        offset = calcOffset(startPosition, endPosition);
        arcLength = calcArcLength(SAMPLES_HIGH);
    }

    public static double calcKnotDistance(Position start, Position end) {
        return Math.sqrt( Math.pow(end.x()-start.x(), 2) + Math.pow(end.y() - start.y(), 2));
    }

    public static Position calcOffset(Position start, Position end) {
        return new Position(start.x(),
                start.y(),
                calcAngleOffset(start, end));
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

    private static double calcAngleOffset(Position start, Position end) {
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

    public double angleAt(double length) {
        double derivative = Math.sqrt(Math.pow(length, 2) - 1);
        double percentage = filterSolutions(function.derive().solve(derivative), length);

        return Math.atan(function.at(percentage)/percentage) + offset.getHeadingDegrees();
    }

    private double filterSolutions(List<Double> solutions, double length) {
        return solutions.stream()
                .filter(solution -> ExtendedMath.constrained(solution, 0, 1))
                .findFirst().orElseGet(() -> length / arcLength);//may be do the longer thing in case there is no correct result ALSO if there is more then one, find the closest one !!!
    }
}
