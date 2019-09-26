package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;

public class Quartic extends PolynomialFunction {
    public static Quartic fromConstants(double a, double b, double c, double d, double e) {
        return new Quartic(generateFunction(Arrays.asList(a, b, c, d, e)));
    }

    protected Quartic(List<Variable> variables) {
        super(variables, Cubic::new, Quintic::new);
    }

    @Override
    protected List<Complex> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
