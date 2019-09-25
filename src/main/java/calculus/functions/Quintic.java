package calculus.functions;

import calculus.Variable;

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
    public List<Double> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
