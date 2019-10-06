package calculus.splines;

import calculus.functions.polynomialFunctions.Cubic;
import calculus.functions.polynomialFunctions.Linear;
import tracer.motion.Waypoint;
import util.Operations;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Waypoint startWaypoint, Waypoint endWaypoint) {
        super(calcFunctions(startWaypoint, endWaypoint), calcToXFunction(startWaypoint, endWaypoint));
    }

    private static Cubic calcFunctions(Waypoint start, Waypoint end) {
        double startTangent = Math.tan(Operations.boundRadiansForcePositive(start.getHeading()));
        double endTangent = Math.tan(Operations.boundRadiansForcePositive(end.getHeading()));
        double startY = start.y();
        double endY = end.y();

        return new Cubic(
                calcA(startTangent, endTangent, startY, endY),
                calcB(startTangent, endTangent, startY, endY),
                calcC(startTangent),
                calcD(startY));
    }

    private static double calcA(double startTangent, double endTangent, double startY, double endY) {
        return startTangent + 2*startY + endTangent -2*endY;
    }

    private static double calcB(double startTangent, double endTangent, double startY, double endY) {
        return 3*endY -endTangent -3*startY -2*startTangent;
    }

    private static double calcC(double startTangent) {
        return startTangent;
    }

    private static double calcD(double startY) {
        return startY;
    }

    private static Linear calcToXFunction(Waypoint start, Waypoint end) {
        double delta = end.x() - start.x();
        return new Linear(delta, start.x());
    }
}
