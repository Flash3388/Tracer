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


    public RootFunction(PolynomialFunction function, int degree, PolynomialFunction multiplier) {
        this(function, degree, multiplier, new PolynomialFunction(0.0));
    }

    public RootFunction(PolynomialFunction function, int degree, PolynomialFunction multiplier, PolynomialFunction addition) {
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
    public MathFunction derive() {
        return new RationalFunction(function.derive(), new RootFunction(function, degree, new PolynomialFunction(2.0)));
    }

    @Override
    public MathFunction integrate() {
        return new RationalFunction(new RationalFunction(function, degree, function), function.derive().mul(1.0/degree+1));
    }

    @Override
    public List<Complex> solutionsTo(double result) throws UnsupportedOperationException, UnsolveableFunctionParametersException {
        return function.pow(degree).mul(multiplier).sub(new PolynomialFunction(result).sub(addition).pow(degree)).solutionsTo(0);
    }

    @Override
    public String toString() {
        return "( " + function + " ) ^" + degree;
    }
}
