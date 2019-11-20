package calculus.functions;

import calculus.functions.polynomial.Linear;
import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.List;
import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;

public abstract class MathFunction implements DoubleFunction<Double> {
    public abstract MathFunction derive();

    public List<Double> realSolutionsTo(double that) {
        return toReal(solutionsTo(that));
    }

    public List<Complex> solutionsTo(double that) {
        throw new UnsupportedOperationException();
    }

    public double lengthBetween(double from, double to, double step) {
        double sum = 0;

        for(double x=from; x <= to; x+=step)
            sum += shortestLength(x, x+step);

        return sum;
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for(; !ExtendedMath.constrained(sum, length-accuracy, length+accuracy) && sum < length; x+=accuracy)
            sum += shortestLength(x, x+accuracy);

        return x;
    }

    public Linear linearOn(MathFunction derivative, double x) {
        double m = derivative.apply(x);
        double tangentPoint = apply(x);

        return new Linear(m, x, tangentPoint);
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }

    public double shortestLength(double xStart, double xEnd) {
        return MathUtil.distance(xStart, apply(xStart), xEnd, apply(xEnd));
    }

    private List<Double> toReal(List<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
