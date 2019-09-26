package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;
import com.jmath.complex.ComplexMath;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Cubic extends PolynomialFunction{
    public static Cubic fromConstants(double a, double b, double c, double d) {
        return new Cubic(generateFunction(Arrays.asList(a, b, c, d)));
    }

    protected Cubic(List<Variable> variables) {
        super(variables, Quadratic::new, Quartic::new);
    }

    @Override
    public List<Complex> solve(double result) throws UnsupportedOperationException {
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier();
        double d = get(3).modifier() - result;

        return Arrays.asList(
                root(a, b, c, d, 1),
                root(a, b, c, d, 2),
                root(a, b, c, d, 3));
    }

    private Complex root(double a, double b, double c, double d, int rootNumber) {
        double spaghetti =  -2*Math.pow(b, 3) + 9*a*b*c - 27*a*a*d;
        Complex secondSpaghetti = new Complex(Math.pow(spaghetti, 2) - 4*Math.pow(b*b - 3*a*c, 3), 0)
                .roots(2).get(0);

        return C(rootNumber, true)
                .multiply(new Complex(spaghetti, 0)
                        .add(secondSpaghetti)
                        .multiply(4)
                        .roots(3).get(0))//I think the first result is always the "realest" one
                .add(-2 * b)
                .add(C(rootNumber, false)
                        .multiply(new Complex(spaghetti, 0)
                                .sub(secondSpaghetti)
                                .multiply(4)
                                .roots(3).get(0)))
                .div(6 * a);
    }

    private Complex C(int rootNumber, boolean isFirst) {
        return isFirst ?
                new Complex(-1, 0).add(power(ComplexMath.complexRoot(-3, 2), rootNumber)).div(2):
                new Complex(-1, 0).sub(power(ComplexMath.complexRoot(-3, 2), rootNumber)).div(2);
    }

    private Complex power(Complex complex, int power) {
        Complex result = new Complex(1, 0);
        IntStream.range(0, power)
                .forEach(i -> result.multiply(complex));

        return result;
    }
}
