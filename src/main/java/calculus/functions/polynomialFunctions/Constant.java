package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;

import java.util.Arrays;
import java.util.List;

public class Constant extends PolynomialFunction {
    public Constant (double a) {
        this(generateFunction(Arrays.asList(a)));
    }

    protected Constant(List<Variable> variables) {
        super(variables, Zero::new, Linear::new);
    }
}
