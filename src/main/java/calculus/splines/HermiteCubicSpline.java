package calculus.splines;

import calculus.functions.polynomialFunctions.Cubic;
import tracer.motion.Waypoint;
import tracer.motion.basic.Distance;
import tracer.motion.basic.units.UnitConversion;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Waypoint startWaypoint, Waypoint endWaypoint, Distance startLength) {
        super(calcYFunction(startWaypoint, endWaypoint), calcXFunction(startWaypoint, endWaypoint), startLength);
    }

    private static Cubic calcYFunction(Waypoint start, Waypoint end) {
        double m0y = Math.sin(start.heading());
        double m1y = Math.sin(end.heading());

        return calcFunction(UnitConversion.toCentimeters(start.y()), m0y, UnitConversion.toCentimeters(end.y()), m1y);
    }

    private static Cubic calcXFunction(Waypoint start, Waypoint end) {
        double m0x = Math.cos(start.heading());
        double m1x = Math.cos(end.heading());

        return calcFunction(UnitConversion.toCentimeters(start.x()), m0x, UnitConversion.toCentimeters(end.x()), m1x);
    }

    private static Cubic calcFunction(double startPosition, double startDerivative, double endPosition, double endDerivative) {
        return new Cubic(
                calcA(startPosition, startDerivative, endPosition, endDerivative),
                calcB(startPosition, startDerivative, endPosition, endDerivative),
                calcC(startDerivative),
                calcD(startPosition));
    }

    private static double calcA(double startPosition, double startDerivative, double endPosition, double endDerivative) {
        return 2*startPosition + startDerivative + endDerivative - 2*endPosition;
    }

    private static double calcB(double startPosition, double startDerivative, double endPosition, double endDerivative) {
        return -3*startPosition - endDerivative -2*startDerivative + 3*endPosition;
    }

    private static double calcC(double startDerivative) {
        return startDerivative;
    }

    private static double calcD(double startPosition) {
        return startPosition;
    }
}
