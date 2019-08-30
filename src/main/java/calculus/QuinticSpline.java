package calculus;

import Tracer.Position;

import java.util.ArrayList;
import java.util.List;

public class QuinticSpline extends Spline {
    public QuinticSpline(Position startPosition, Position endPosition) {
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
        double c = calcC(startDelta, endDelta);
        double d = calcD();
        double e = calcE(startDelta);
        double f = calcF();

        constants.add(a);
        constants.add(b);
        constants.add(c);
        constants.add(d);
        constants.add(e);
        constants.add(f);

        return constants;
    }

    private double calcA(double startDelta, double endDelta) {
        return -(3 * (startDelta + endDelta)) / Math.pow(getKnotDistance(), 4);
    }

    private double calcB(double startDelta, double endDelta) {
        return (8 * startDelta + 7 * endDelta) / Math.pow(getKnotDistance(), 3);
    }

    private double calcC(double startDelta, double endDelta) {
        return -(6 * startDelta + 4 * endDelta) / Math.pow(getKnotDistance(), 2);
    }

    private double calcD() {
        return 0.0;
    }

    private double calcE(double startDelta) {
        return startDelta;
    }

    private double calcF() {
        return 0.0;
    }
}
