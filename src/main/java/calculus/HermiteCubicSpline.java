package calculus;

import tracer.motion.Position;
import util.Operations;

import java.util.ArrayList;
import java.util.List;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    @Override
    protected List<Double> getFunctionConstants(Position startPosition, Position endPosition) {
        double startDelta = Math.tan(Operations.boundRadiansForcePositive(startPosition.getHeading()) - Operations.boundRadiansForcePositive(offset().getHeading()));
        double endDelta = Math.tan(Operations.boundRadiansForcePositive(endPosition.getHeading()) - Operations.boundRadiansForcePositive(offset().getHeading()));

        return calcFunctionConstants(startDelta, endDelta);
    }

    private List<Double> calcFunctionConstants(double startDelta, double endDelta) {
        List<Double> constants = new ArrayList<>();
        double a = calcA(startDelta, endDelta);
        double b = calcB(startDelta, endDelta);
        double c = calcC(startDelta);
        double d = calcD();

        constants.add(a);
        constants.add(b);
        constants.add(c);
        constants.add(d);

        return constants;
    }

    private double calcA(double startDelta, double endDelta) {
        return (startDelta + endDelta) / Math.pow(knotDistance(), 2);
    }

    private double calcB(double startDelta, double endDelta) {
        return -(2 * startDelta + endDelta) / knotDistance();
    }

    private double calcC(double startDelta) {
        return startDelta;
    }

    private double calcD() {
        return 0.0;
    }
}
