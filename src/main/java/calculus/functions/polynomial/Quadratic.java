package calculus.functions.polynomial;

import com.jmath.complex.Complex;
import com.jmath.complex.ComplexMath;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Quadratic extends PolynomialFunction {
    public Quadratic(double a, double b, double c) {
        this(Arrays.asList(a, b, c));
    }

    public Quadratic(List<Double> constants) {
        super(constants, 2);
    }

    @Override
    protected Collection<Complex> trySolve(double result) {
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier() - result;

        return Arrays.asList(
                root(a, b, c, true),
                root(a, b, c, false));
    }

    private Complex root(double a, double b, double c, boolean addRoot) {
        return ComplexMath.complexRoot(b*b - 4*a*c, 2).multiply(addRoot ? 1 : -1).add(new Complex(-b, 0)).div(2*a);
    }
}
