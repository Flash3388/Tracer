package calculus.functions;

import calculus.variables.Variable;

import java.util.List;

public class Zero extends PolynomialFunction{
    protected Zero(List<Variable> variables) {
        super(variables, Zero::new, Zero::new);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
