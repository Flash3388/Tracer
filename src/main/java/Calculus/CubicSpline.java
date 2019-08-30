package Calculus;

import Tracer.Position;

import java.util.ArrayList;
import java.util.List;

public class CubicSpline extends Spline {
    public CubicSpline(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    @Override
    protected List<Double> getFunctionConstants(Position startPosition, Position endPosition) {
        double startDelta = Math.tan(AngleOperations.boundRadians(startPosition.getHeading()) - AngleOperations.boundRadians(getOffset().getHeading()));
        double endDelta = Math.tan(AngleOperations.boundRadians(endPosition.getHeading()) - AngleOperations.boundRadians(getOffset().getHeading()));

        return calcFunctionConstants(startDelta, endDelta);
    }

    private List<Double> calcFunctionConstants(double startDelta, double endDelta) {
        List<Double> constants = new ArrayList<>();
        double a = calcA(startDelta, endDelta);
        double b = calcB(startDelta, endDelta);
        double c = calcC(startDelta);

        constants.add(a);
        constants.add(b);
        constants.add(c);

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
}
