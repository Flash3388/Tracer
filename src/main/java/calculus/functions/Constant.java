package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Constant extends PolynomialFunction {
    public static Constant fromConstants(double a) {
        return new Constant(generateFunction(Arrays.asList(a)));
    }

    protected Constant(List<Variable> variables) {
        super(variables, Zero::new, Linear::new);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
