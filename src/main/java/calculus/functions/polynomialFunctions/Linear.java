package calculus.functions.polynomialFunctions;

import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Linear extends PolynomialFunction {
    public Linear(double m, double x, double y) {
        this(m, y-x*m);
    }

    public Linear(double a, double b) {
        this(Arrays.asList(a, b));
    }

    public Linear(List<Double> constants) {
        super(constants, 1);
    }

    @Override
    protected List<Complex> trySolve(double result) {
        double a = get(0).modifier();
        double b = get(1).modifier() - result;

        return Collections.singletonList(new Complex(-b / a, 0));
    }
}
