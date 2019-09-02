package calculus;

import tracer.Position;

import java.util.ArrayList;
import java.util.List;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    @Override
    protected List<Double> getFunctionConstants(Position startPosition, Position endPosition) {
        double startDelta = Math.tan(Operations.boundRadians(startPosition.getHeading()) - Operations.boundRadians(getOffset().getHeading()));
        double endDelta = Math.tan(Operations.boundRadians(endPosition.getHeading()) - Operations.boundRadians(getOffset().getHeading()));

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
        return (startDelta + endDelta) / Math.pow(getKnotDistance(), 2);
    }

    private double calcB(double startDelta, double endDelta) {
        return -(2 * startDelta + endDelta) / getKnotDistance();
    }

    private double calcC(double startDelta) {
        return startDelta;
    }

    private double calcD() {
        return 0.0;
    }
}
