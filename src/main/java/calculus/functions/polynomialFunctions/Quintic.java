package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Quintic extends PolynomialFunction {
    public static Quintic fromConstants(double a, double b, double c, double d, double e, double f) {
        return new Quintic(generateFunction(Arrays.asList(a, b, c, d, e, f)));
    }

    protected Quintic(List<Variable> variables) {
        super(variables, Quartic::new, Unsupported::new);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
