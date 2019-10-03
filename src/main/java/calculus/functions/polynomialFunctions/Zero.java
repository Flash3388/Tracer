package calculus.functions.polynomialFunctions;

import calculus.variables.Variable;

import java.util.List;

public class Zero extends PolynomialFunction {
    protected Zero(List<Variable> variables) {
        super(variables, Zero::new, Zero::new);
    }
}
