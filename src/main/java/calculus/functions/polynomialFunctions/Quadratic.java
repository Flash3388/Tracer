package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;
import com.jmath.complex.Complex;
import com.jmath.complex.ComplexMath;

import java.util.Arrays;
import java.util.List;

public class Quadratic extends PolynomialFunction {
    public static Quadratic fromConstants(double a, double b, double c) {
        return new Quadratic(generateFunction(Arrays.asList(a, b, c)));
    }

    protected Quadratic(List<Variable> variables) {
        super(variables, Linear::new, Cubic::new);
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
