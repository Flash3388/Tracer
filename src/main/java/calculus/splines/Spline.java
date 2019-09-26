package calculus.splines;

import calculus.functions.PolynomialFunction;
import com.jmath.ExtendedMath;
import tracer.motion.Waypoint;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class Spline {
    private static final int SAMPLES_FAST = 1000;
    private static final int SAMPLES_LOW = SAMPLES_FAST * 10;
    private static final int SAMPLES_HIGH = SAMPLES_LOW * 10;

    private final PolynomialFunction function;
    private final Waypoint offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(PolynomialFunction function, Waypoint startWaypoint, Waypoint endWaypoint) {
        this.function = function;

        knotDistance = calcKnotDistance(startWaypoint, endWaypoint);
        offset = calcOffset(startWaypoint, endWaypoint);
        arcLength = calcArcLength();
    }

    public static double calcKnotDistance(Waypoint start, Waypoint end) {
        return Math.sqrt(Math.pow(end.x()-start.x(), 2) + Math.pow(end.y() - start.y(), 2));
    }

    public static Waypoint calcOffset(Waypoint start, Waypoint end) {
        return Waypoint.centimetersRadians(
                start.x(),
                start.y(),
                calcAngleOffset(start, end));
    }

    public double knotDistance() {
        return knotDistance;
    }

    public Waypoint offset() {
        return offset;
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);

        double derivative = Math.sqrt(Math.pow(length, 2) - 1);
        double percentage = filterSolutions(function.derive().solve(derivative), length);

        return Math.atan(function.at(percentage)/percentage) + offset.getHeadingDegrees();
    }

    private static double calcAngleOffset(Waypoint start, Waypoint end) {
        return Math.atan2(end.y() - start.y(), end.x() - start.x());
    }

    private double calcArcLength() {
        return IntStream.range(0, SAMPLES_HIGH)
                .mapToDouble(index -> calcCurrentArcLength(index/(double) SAMPLES_HIGH))
                .sum() * knotDistance;
    }

    private double calcCurrentArcLength(double percentage) {
        return getArcLengthPercentage(percentage)/ SAMPLES_HIGH;
    }

    private double getArcLengthPercentage(double percentage) {
        return getArcLengthAt(knotDistance * percentage);
    }

    private double getArcLengthAt(double x) {
        return Math.sqrt(Math.pow(function.derive().at(x), 2) + 1.0);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double filterSolutions(List<Double> solutions, double length) {
        return solutions.stream()
                .filter(solution -> ExtendedMath.constrained(solution, 0, 1))
                .min(Comparator.comparingDouble(solution -> Math.abs(length - getArcLengthAt(solution))))
                .orElseGet(() -> getPercentageAtLength(length));
    }

    private double getPercentageAtLength(double length) {
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);

        return IntStream.range(0, SAMPLES_HIGH)
                .filter(index -> {
                    sum.set(sum.get() + calcCurrentArcLength(index/(double)SAMPLES_HIGH) * knotDistance);
                    return sum.get() >= length;
                })
                .limit(1)
                .mapToDouble(index -> index/(double)SAMPLES_HIGH)
                .sum();
    }
}
