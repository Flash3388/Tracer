package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Linear extends PolynomialFunction {
    public static Linear fromConstants(double a, double b) {
        if(a == 0)
            throw new IllegalArgumentException("a must not be equal to 0");
        return new Linear(generateFunction(Arrays.asList(a, b)));
    }

    protected Linear(List<Variable> variables) {
        super(variables, Constant::new, Quadratic::new);
    }

    @Override
    public List<Complex> solutions(double result) {
        return Arrays.asList(new Complex((result - get(1).modifier())/get(0).modifier(), 0));
    }
}
