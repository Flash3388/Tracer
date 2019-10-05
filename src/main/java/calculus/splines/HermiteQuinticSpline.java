package calculus.splines;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import tracer.motion.Waypoint;

@Deprecated
public class HermiteQuinticSpline extends Spline {
    public HermiteQuinticSpline(Waypoint startWaypoint, Waypoint endWaypoint) {
        super(null, null);
    }

    private static PolynomialFunction calcFunctions(Waypoint start, Waypoint end) {
        return null;
    }

    private static double calcA(double startDelta, double endDelta, double knotDistance) {
        return 0;
    }

    private static double calcB(double startDelta, double endDelta, double knotDistance) {
        return 0;
    }

    private static double calcC(double startDelta, double endDelta, double knotDistance) {
        return 0;
    }

    private static double calcD() {
        return 0.0;
    }

    private static double calcE(double startDelta) {
        return 0;
    }

    private static double calcF() {
        return 0.0;
    }
}
