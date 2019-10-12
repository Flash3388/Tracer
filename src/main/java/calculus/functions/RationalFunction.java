package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;

public class RationalFunction extends MathFunction {
    private final PolynomialFunction numerator;
    private final PolynomialFunction denominator;

    public RationalFunction(PolynomialFunction numerator, PolynomialFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        System.out.println(x);
        return numerator.at(x)/denominator.at(x);
    }

    @Override
    public MathFunction derive() {
        return new RationalFunction(numerator.mul(denominator.derive()).sub(numerator.derive().mul(denominator)), denominator.pow(2));
    }
}