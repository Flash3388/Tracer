package calculus.functions;

import calculus.functions.polynomial.Linear;
import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

public abstract class MathFunction implements DoubleUnaryOperator {
    public abstract MathFunction derive();

    public Collection<Double> realSolutionsTo(double that) {
        return toReal(solutionsTo(that));
    }

    public Collection<Complex> solutionsTo(double that) {
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
        double m = derivative.applyAsDouble(x);
        double tangentPoint = applyAsDouble(x);

        return new Linear(m, x, tangentPoint);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MathFunction && equals((MathFunction) obj);
    }

    public boolean equals(MathFunction other) {
        return this.hashCode()==other.hashCode();
    }

    public double shortestLength(double xStart, double xEnd) {
        return MathUtil.distance(xStart, applyAsDouble(xStart), xEnd, applyAsDouble(xEnd));
    }

    private Collection<Double> toReal(Collection<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
