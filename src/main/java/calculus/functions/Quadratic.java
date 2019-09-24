package calculus.functions;

import calculus.Variable;

import java.util.Arrays;
import java.util.List;

public class Quadratic extends PolynomialFunction {
    public static Quadratic fromConstants(double a, double b, double c) {
        return new Quadratic(generateFunction(Arrays.asList(a, b, c)));
    }

    protected Quadratic(List<Variable> variables) {
        super(variables, Linear::new, Quadratic::new);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException{
        return null;
    }
}
