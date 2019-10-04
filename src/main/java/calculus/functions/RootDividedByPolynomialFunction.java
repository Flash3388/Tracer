package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.complex.Complex;

import java.util.List;

public class RootDividedByPolynomialFunction extends MathFunction {
    private final RootFunction numerator;
    private final PolynomialFunction denominator;

    public RootDividedByPolynomialFunction(RootFunction numerator, PolynomialFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        return numerator.at(x)/denominator.at(x); // check for 0
    }

    @Override
    public MathFunction derive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Complex> solutionsTo(double that) throws UnsupportedOperationException, UnsolveableFunctionParametersException {
        return numerator.normalize().sub(new PolynomialFunction(that).mul(denominator).pow(numerator.degree())).solutionsTo(0);
    }

    public RootDividedByPolynomialFunction mul(PolynomialFunction other) {
        return new RootDividedByPolynomialFunction(numerator.mul(other), denominator);
    }

    @Override
    public String toString() {
        return "(" + numerator + ") / " + "(" + denominator + " )";
    }
}
