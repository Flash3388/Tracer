package calculus.functions;

import calculus.Variable;

import java.util.List;

public class Unsupported extends PolynomialFunction {
    protected Unsupported(List<Variable> variables) {
        super(variables, Unsupported::new, Unsupported::new);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
