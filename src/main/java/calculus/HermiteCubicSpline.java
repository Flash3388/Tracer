package calculus;

import calculus.functions.Cubic;
import tracer.motion.Position;
import util.Operations;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Position startPosition, Position endPosition) {
        super(calcFunctions(startPosition, endPosition), startPosition, endPosition);
    }

    private static Cubic calcFunctions(Position start, Position end) {
        double offsetAngle = calcOffset(start, end).getHeading();
        double startDelta = Math.tan(Operations.boundRadiansForcePositive(start.getHeading()) - Operations.boundRadiansForcePositive(offsetAngle));
        double endDelta = Math.tan(Operations.boundRadiansForcePositive(end.getHeading()) - Operations.boundRadiansForcePositive(offsetAngle));
        double knotDistance = calcKnotDistance(start, end);

        return Cubic.fromConstants(
                calcA(startDelta, endDelta, knotDistance),
                calcB(startDelta, endDelta, knotDistance),
                calcC(startDelta),
                calcD());
    }

    private static double calcA(double startDelta, double endDelta, double knotDistance) {
        return (startDelta + endDelta) / Math.pow(knotDistance, 2);
    }

    private static double calcB(double startDelta, double endDelta, double knotDistance) {
        return -(2 * startDelta + endDelta) / knotDistance;
    }

    private static double calcC(double startDelta) {
        return startDelta;
    }

    private static double calcD() {
        return 0.0;
    }
}
