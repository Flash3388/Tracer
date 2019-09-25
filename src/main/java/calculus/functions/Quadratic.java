package calculus.functions;

import calculus.Variable;

import java.util.Arrays;
import java.util.List;

public class Quadratic extends PolynomialFunction {
    public static Quadratic fromConstants(double a, double b, double c) {
        return new Quadratic(generateFunction(Arrays.asList(a, b, c)));
    }

    protected Quadratic(List<Variable> variables) {
        super(variables, Linear::new, Cubic::new);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException{
        return Arrays.asList(root(get(0).modifier(), get(1).modifier(), get(2).modifier() - result, true),
                root(get(0).modifier(), get(1).modifier(), get(2).modifier() - result, false));
    }

    private double root(double a, double b, double c, boolean addRoot) {
        return (-b + (addRoot ? 1 : -1) * Math.sqrt(Math.pow(b, 2) + 4*a*c)) / 2*a;
    }
}
