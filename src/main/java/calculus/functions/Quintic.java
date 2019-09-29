package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Quintic extends PolynomialFunction {
    public static Quintic fromConstants(double a, double b, double c, double d, double e, double f) {
        if(a == 0)
            throw new IllegalArgumentException("a must not be equal to 0");
        return new Quintic(generateFunction(Arrays.asList(a, b, c, d, e, f)));
    }

    protected Quintic(List<Variable> variables) {
        super(variables, Quartic::new, Unsupported::new);
    }

    @Override
    public List<Complex> solutions(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
