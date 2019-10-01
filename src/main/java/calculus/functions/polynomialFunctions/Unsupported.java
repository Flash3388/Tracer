package calculus.functions.polynomialFunctions;

import calculus.functions.PolynomialFunction;
import calculus.variables.Variable;

import java.util.List;

public class Unsupported extends PolynomialFunction {
    protected Unsupported(List<Variable> variables) {
        super(variables, Unsupported::new, Unsupported::new);
    }
}
