package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Linear extends PolynomialFunction {
    public static Linear fromConstants(double a, double b) {
        return new Linear(generateFunction(Arrays.asList(a, b)));
    }

    protected Linear(List<Variable> variables) {
        super(variables, Constant::new, Quadratic::new);
    }

    @Override
    protected List<Complex> trySolve(double result) {
        double a = get(0).modifier();
        double b = get(1).modifier() - result;

        return Arrays.asList(new Complex(-b/a, 0));
    }
}
