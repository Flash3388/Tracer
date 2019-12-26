package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.Integrals;
import util.MathUtil;

import java.util.function.DoubleUnaryOperator;

public abstract class MathFunction implements DoubleUnaryOperator {
    private final static int SLICES = 1000;

    public abstract MathFunction derive();

    public double integrate(double from, double to) {
        return Integrals.simpsonsRule(this::applyAsDouble, from, to, SLICES);
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for (;sum < length; x+= accuracy)
            sum += shortestLength(x, x + accuracy);

        return x;
    }

    protected double shortestLength(double xStart, double xEnd) {
        return MathUtil.distance(xStart, applyAsDouble(xStart), xEnd, applyAsDouble(xEnd));
    }
}