package calculus.functions;

import com.jmath.Integrals;
import util.MathUtil;

public abstract class BasicMathFunction implements MathFunction {
    private final static int SLICES = 100;

    public double integrate(double from, double to) {
        return integrate(from, to, SLICES);
    }

    public double integrate(double from, double to, int slices) {
        return Integrals.simpsonsRule(this::applyAsDouble, from, to, slices);
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for (;sum < length; x+= accuracy)
            sum += shortestDistance(x, x + accuracy);

        return x;
    }

    private double shortestDistance(double tStart, double tEnd) {
        return MathUtil.distance(xAt(tStart), applyAsDouble(tStart), xAt(tEnd), applyAsDouble(tEnd));
    }
}
