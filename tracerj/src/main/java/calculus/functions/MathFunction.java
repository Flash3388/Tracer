package calculus.functions;

import calculus.functions.polynomial.Linear;
import com.jmath.ExtendedMath;
import com.jmath.Function;
import com.jmath.Integrals;
import com.jmath.complex.Complex;
import util.MathUtil;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

public abstract class MathFunction implements DoubleUnaryOperator {
    private final static int SLICES = 1000;

    public abstract MathFunction derive();

    public double integrate(double from, double to) {
        return Integrals.simpsonsRule(this::applyAsDouble, from, to, SLICES);
    }

    public double findIntegral(double from, double target) {
        double h = 1.0/SLICES / SLICES;
        double s = applyAsDouble(from) + applyAsDouble(1.0/SLICES);
        double s1 = 0;
        double s2 = 0;

        int halfSlices = SLICES / 2;
        for(int i = 1; i <= halfSlices; i++) {
            s1 += applyAsDouble(from + (2 * i - 1) * h);

            if(i < halfSlices)
                s2 += applyAsDouble(from + 2 * i * h);

            double integral = (1 / 3.0) * h * (s + 4 * s1 + 2 * s2);
            if(integral >= target)
                return from + i * 1.0/SLICES;
            h += i/(double)SLICES/SLICES;
        }

        return (1 / 3.0) * h * (s + 4 * s1 + 2 * s2);
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for(; !ExtendedMath.constrained(sum, length-accuracy, length+accuracy) && sum < length; x+=accuracy)
            sum += shortestLength(x, x+accuracy);

        return x;
    }

    public Linear linearOn(MathFunction derivative, double x) {
        double m = derivative.applyAsDouble(x);
        double tangentPoint = applyAsDouble(x);

        return new Linear(m, x, tangentPoint);
    }

    private double shortestLength(double xStart, double xEnd) {
        return MathUtil.distance(xStart, applyAsDouble(xStart), xEnd, applyAsDouble(xEnd));
    }
}
