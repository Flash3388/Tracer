package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.RootFunction;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.ExtendedMath;
import tracer.motion.Waypoint;

import java.util.Comparator;
import java.util.List;

public class Spline {
    private final PolynomialFunction function;
    private final MathFunction arcFunction;
    private final Waypoint offset;
    private final double knotDistance;
    private final double arcLength;

    public Spline(PolynomialFunction function, Waypoint startWaypoint, Waypoint endWaypoint) {
        this.function = function;
        arcFunction = new RootFunction(function.derive().pow(2).add(1.0), 2).integrate();

        knotDistance = calcKnotDistance(startWaypoint, endWaypoint);
        offset = calcOffset(startWaypoint, endWaypoint);
        arcLength = arcLengthAt(1);

        System.out.println(function);
        System.out.println(arcFunction);
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
        length += arcFunction.at(0);
        double percentage = filterSolutions(arcFunction.realSolutionsTo(length), length) / knotDistance;
        System.out.println(percentage);

        return Math.atan2(function.at(percentage), percentage) + offset.getHeadingDegrees();
    }

    private static double calcAngleOffset(Waypoint start, Waypoint end) {
        return Math.atan2(end.y() - start.y(), end.x() - start.x());
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double filterSolutions(List<Double> solutions, double length) {
        return solutions.stream()
                .filter(solution -> ExtendedMath.constrained(solution, 0, 1))
                .min(Comparator.comparingDouble(solution -> Math.abs(length - arcLengthAt(solution))))
                .orElseGet(() -> 0.0);
    }

    private double arcLengthAt(double t) {
        return arcFunction.difference(0, t*knotDistance);
    }
}
