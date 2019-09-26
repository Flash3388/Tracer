package calculus.splines;

import calculus.functions.Quintic;
import tracer.motion.Waypoint;
import util.Operations;

public class HermiteQuinticSpline extends Spline {
    public HermiteQuinticSpline(Waypoint startWaypoint, Waypoint endWaypoint) {
        super(calcFunctions(startWaypoint, endWaypoint), startWaypoint, endWaypoint);
    }

    private static Quintic calcFunctions(Waypoint start, Waypoint end) {
        double offsetAngle = calcOffset(start, end).getHeading();
        double startDelta = Math.tan(Operations.boundRadiansForcePositive(start.getHeading()) - Operations.boundRadiansForcePositive(offsetAngle));
        double endDelta = Math.tan(Operations.boundRadiansForcePositive(end.getHeading()) - Operations.boundRadiansForcePositive(offsetAngle));
        double knotDistance = calcKnotDistance(start, end);

        return Quintic.fromConstants(
                calcA(startDelta, endDelta, knotDistance),
                calcB(startDelta, endDelta, knotDistance),
                calcC(startDelta, endDelta, knotDistance),
                calcD(),
                calcE(startDelta),
                calcF());
    }

    private static double calcA(double startDelta, double endDelta, double knotDistance) {
        return -(3 * (startDelta + endDelta)) / Math.pow(knotDistance, 4);
    }

    private static double calcB(double startDelta, double endDelta, double knotDistance) {
        return (8 * startDelta + 7 * endDelta) / Math.pow(knotDistance, 3);
    }

    private static double calcC(double startDelta, double endDelta, double knotDistance) {
        return -(6 * startDelta + 4 * endDelta) / Math.pow(knotDistance, 2);
    }

    private static double calcD() {
        return 0.0;
    }

    private static double calcE(double startDelta) {
        return startDelta;
    }

    private static double calcF() {
        return 0.0;
    }
}
