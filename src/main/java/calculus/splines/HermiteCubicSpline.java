package calculus.splines;

import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Waypoint startWaypoint, Waypoint endWaypoint, double startLength) {
        super(calcYFunction(startWaypoint, endWaypoint), calcXFunction(startWaypoint, endWaypoint), startLength);
    }

    private static PolynomialFunction calcYFunction(Waypoint start, Waypoint end) {
        double m0y = Math.sin(start.heading());
        double m1y = Math.sin(end.heading());

        return calcFunction(start.y(), m0y, end.y(), m1y);
    }

    private static PolynomialFunction calcXFunction(Waypoint start, Waypoint end) {
        double m0x = Math.cos(start.heading());
        double m1x = Math.cos(end.heading());

        return calcFunction(start.x(), m0x, end.x(), m1x);
    }

    private static PolynomialFunction calcFunction(double startPosition, double startDerivative, double endPosition, double endDerivative) {
        return new PolynomialFunction(
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
