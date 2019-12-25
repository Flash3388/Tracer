package calculus.functions;

import com.jmath.Integrals;

import java.util.function.DoubleUnaryOperator;

public abstract class MathFunction implements DoubleUnaryOperator {
    private final static int SLICES = 1000;

    public abstract MathFunction derive();

    public double integrate(double from, double to) {
        return Integrals.simpsonsRule(this::applyAsDouble, from, to, SLICES);
    }

    public double findIntegral(double from, double target, double accuracy) {
        double t = from;
        double sum = 0;
        long start = System.currentTimeMillis();
        do {
            double yStart = applyAsDouble(t);
            double yEnd = applyAsDouble(t+accuracy);
            sum += accuracy/2 * (yStart + yEnd);
            t += accuracy;
        } while (sum < target);
        long end = System.currentTimeMillis();

        return t;
    }
}
