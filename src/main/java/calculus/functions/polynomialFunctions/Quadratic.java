package calculus.functions.polynomialFunctions;

import com.jmath.complex.Complex;
import com.jmath.complex.ComplexMath;

import java.util.Arrays;
import java.util.List;

public class Quadratic extends PolynomialFunction {
    public Quadratic(double a, double b, double c) {
        this(Arrays.asList(a, b, c));
    }

    public Quadratic(List<Double> constants) {
        super(constants, 2);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException{
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier() - result;

        return Arrays.asList(
                root(a, b, c, true),
                root(a, b, c, false));
    }

    private Complex root(double a, double b, double c, boolean addRoot) {
        return ComplexMath.complexRoot(Math.pow(b, 2) + 4*a*c, 2).multiply(addRoot ? 1 : -1).add(-b).div(2*a);
    }
}
