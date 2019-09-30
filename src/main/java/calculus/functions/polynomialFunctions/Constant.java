package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Constant extends PolynomialFunction {
    public Constant (double a) {
        this(generateFunction(Arrays.asList(a)));
    }

    protected Constant(List<Variable> variables) {
        super(variables, Zero::new, Linear::new);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
