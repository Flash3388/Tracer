package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.complex.Complex;

import java.util.List;

public class RootFunction extends MathFunction{
    private final PolynomialFunction function;
    private final int degree;
    private final PolynomialFunction multiplier;
    private final PolynomialFunction addition;

    public RootFunction(PolynomialFunction function, int degree) {
        this(function, degree, new PolynomialFunction(1.0), new PolynomialFunction(0.0));
    }

    private RootFunction(PolynomialFunction function, int degree, PolynomialFunction multiplier, PolynomialFunction addition) {
        this.function = function;
        this.degree = degree;
        this.multiplier = multiplier;
        this.addition = addition;
    }

    @Override
    public double at(double x) {
        return Math.pow(function.at(x), 1.0/degree) * multiplier.at(x) + addition.at(x);
    }

    @Override
    public PolynomialDividedByRootFunction derive() {
        return new PolynomialDividedByRootFunction(function.derive(), new RootFunction(function, degree).mul(new PolynomialFunction(2.0)));
    }

    @Override
    public RootDividedByPolynomialFunction integrate() {
        return new RootDividedByPolynomialFunction(new RootFunction(function, degree).mul(function), function.derive().mul(1.0/degree+1));
    }

    @Override
    public List<Complex> solutionsTo(double result) throws UnsupportedOperationException, UnsolvableFunctionParametersException {
        return function.mul(multiplier).sub(new PolynomialFunction(result).sub(addition).pow(degree)).solutionsTo(0);
    }

    @Override
    public String toString() {
        return "( " + function + " ) root" + degree;
    }

    public RootFunction mul(PolynomialFunction other) {
        return new RootFunction(function, degree, multiplier.mul(other), addition.mul(other));
    }

    public RootFunction add(PolynomialFunction other) {
        return new RootFunction(function, degree, multiplier, addition.add(other));
    }

    public int degree() {
        return degree;
    }

    public PolynomialFunction normalize() {
        return function.mul(multiplier.pow(degree)).add(addition.pow(degree));
    }
}
