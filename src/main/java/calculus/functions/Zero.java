package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.List;

public class Zero extends PolynomialFunction{
    protected Zero(List<Variable> variables) {
        super(variables, Zero::new, Zero::new);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
