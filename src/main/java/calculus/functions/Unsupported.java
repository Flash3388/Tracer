package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.List;

public class Unsupported extends PolynomialFunction {
    protected Unsupported(List<Variable> variables) {
        super(variables, Unsupported::new, Unsupported::new);
    }

    @Override
    protected List<Complex> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
