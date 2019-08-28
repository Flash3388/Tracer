package Calculus;

import Tracer.Position;

import java.util.ArrayList;
import java.util.List;

public class QuinticSpline extends Spline {
    private final static double TWO = 2;
    private final static double THREE = 3;
    private final static double FOUR = 4;
    private final static double SIX = 6;
    private final static double SEVEN = 7;
    private final static double EIGHT = 8;

    public QuinticSpline(Position startPosition, Position endPosition) {
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
        double c = calcC(startDelta, endDelta);
        double d = calcD();
        double e = calcE(startDelta);

        constants.add(a);
        constants.add(b);
        constants.add(c);
        constants.add(d);
        constants.add(e);

        return constants;
    }

    private double calcA(double startDelta, double endDelta) {
        return -(THREE * (startDelta + endDelta)) / Math.pow(getKnotDistance(), FOUR);
    }

    private double calcB(double startDelta, double endDelta) {
        return (EIGHT * startDelta + SEVEN * endDelta) / Math.pow(getKnotDistance(), THREE);
    }

    private double calcC(double startDelta, double endDelta) {
        return -(SIX * startDelta + FOUR * endDelta) / Math.pow(getKnotDistance(), TWO);
    }

    private double calcD() {
        return 0;
    }

    private double calcE(double startDelta) {
        return startDelta;
    }
}
