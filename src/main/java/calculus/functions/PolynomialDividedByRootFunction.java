package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.complex.Complex;

import java.util.List;

public class PolynomialDividedByRootFunction extends MathFunction{
    private final PolynomialFunction numerator;
    private final RootFunction denominator;

    public PolynomialDividedByRootFunction(PolynomialFunction numerator, RootFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        return numerator.at(x)/denominator.at(x); // check for 0
    }

    @Override
    public PolynomialDividedByRootFunction derive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Complex> solutionsTo(double that) throws UnsupportedOperationException, UnsolvableFunctionParametersException {
        return numerator.pow(denominator.degree()).sub(new PolynomialFunction(that).pow(denominator.degree()).mul(denominator.normalize())).solutionsTo(that);
    }

    public PolynomialDividedByRootFunction mul(PolynomialFunction other) {
        return new PolynomialDividedByRootFunction(numerator.mul(other), denominator);
    }

    @Override
    public String toString() {
        return "(" + numerator + ") / " + "(" + denominator + " )";
    }
}
