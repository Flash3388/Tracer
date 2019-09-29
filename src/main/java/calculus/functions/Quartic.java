package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quartic extends PolynomialFunction {
    public static Quartic fromConstants(double a, double b, double c, double d, double e) {
        if(a == 0)
            throw new IllegalArgumentException("a must not be equal to 0");
        return new Quartic(generateFunction(Arrays.asList(a, b, c, d, e)));
    }

    protected Quartic(List<Variable> variables) {
        super(variables, Cubic::new, Quintic::new);
    }

    @Override
    public List<Complex> solutions(double result) throws UnsupportedOperationException {
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier();
        double d = get(3).modifier();
        double e = get(3).modifier() - result;

        return realRoots(a, b, c, d, e);
    }

    private List<Complex> realRoots(double a, double b, double c, double d, double e) {
        List<Complex> results = new ArrayList<>();
        b /= a;
        c /= a;
        d /= a;
        e /= a;

        Cubic cubic = findCubic(b, c, d, e);
//        p =

        return results;
    }

    private Cubic findCubic(double b, double c, double d, double e) {
        double f = c - 3*b*b/8;
        double g = d + Math.pow(b, 3)/8.0 - b*c/2;
        double h = e - 3*Math.pow(b, 4)/256 + b*b * c/16 - b*d/4;

        return Cubic.fromConstants(1, f/2, (f*f - 4*h)/16, -g*g/64);
    }
}
