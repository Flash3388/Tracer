package calculus.functions;

import calculus.Variable;

import java.util.Arrays;
import java.util.List;

public class Cubic extends PolynomialFunction{
    public static Cubic fromConstants(double a, double b, double c, double d) {
        return new Cubic(generateFunction(Arrays.asList(a, b, c, d)));
    }

    protected Cubic(List<Variable> variables) {
        super(variables, Quadratic::new, null);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
