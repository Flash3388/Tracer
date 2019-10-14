package calculus.splines;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import tracer.motion.Waypoint;

public class HermiteQuinticSpline extends Spline {
    public HermiteQuinticSpline(Waypoint startWaypoint, Waypoint endWaypoint, double startLength) {
        super(calcYFunction(startWaypoint, endWaypoint), calcXFunction(startWaypoint, endWaypoint), startLength);
    }

    private static PolynomialFunction calcYFunction(Waypoint start, Waypoint end) {
        double m0y = Math.sin(start.heading());
        double m1y = Math.sin(end.heading());

        return calcFunction(start.y(), m0y, start.heading(), end.y(), m1y, end.heading());
    }

    private static PolynomialFunction calcXFunction(Waypoint start, Waypoint end) {
        double m0x = Math.cos(start.heading());
        double m1x = Math.cos(end.heading());

        return calcFunction(start.x(), m0x, m0x, end.x(), m1x, m1x);
    }

    private static PolynomialFunction calcFunction(double startPosition, double startDerivative, double startSecondDerivative, double endPosition, double endDerivative, double endSecondDerivative) {
        return new PolynomialFunction(
                calcA(startPosition, startDerivative, startSecondDerivative, endPosition, endDerivative, endSecondDerivative),
                calcB(startPosition, startDerivative, startSecondDerivative, endPosition, endDerivative, endSecondDerivative),
                calcC(startPosition, startDerivative, startSecondDerivative, endPosition, endDerivative, endSecondDerivative),
                calcD(startSecondDerivative),
                calcE(startDerivative),
                calcF(startPosition));
    }

    private static double calcA(double startPosition, double startDerivative, double startSecondDerivative, double endPosition, double endDerivative, double endSecondDerivative) {
        return 6*endPosition -3*endDerivative +0.5*endSecondDerivative +6*startPosition -3*startDerivative -0.5*startSecondDerivative;
    }

    private static double calcB(double startPosition, double startDerivative, double startSecondDerivative, double endPosition, double endDerivative, double endSecondDerivative) {
        return -15*endPosition +7*endDerivative -endSecondDerivative +15*startPosition +8*startDerivative +1.5*startSecondDerivative;
    }

    private static double calcC(double startPosition, double startDerivative, double startSecondDerivative, double endPosition, double endDerivative, double endSecondDerivative) {
        return 10*endPosition -4*endDerivative +0.5*endSecondDerivative -10*startPosition -6*startDerivative -1.5*startSecondDerivative;
    }

    private static double calcD(double startSecondDerivative) {
        return startSecondDerivative/2;
    }

    private static double calcE(double startDerivative) {
        return startDerivative;
    }

    private static double calcF(double startPosition) {
        return startPosition;
    }
}
