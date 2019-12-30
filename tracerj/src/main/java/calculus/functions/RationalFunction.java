package calculus.functions;

import calculus.functions.polynomial.PolynomialFunction;

public class RationalFunction extends MathFunction {
    private final PolynomialFunction numerator;
    private final PolynomialFunction denominator;

    public RationalFunction(PolynomialFunction numerator, PolynomialFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public PolynomialFunction numerator() {
        return numerator;
    }

    public PolynomialFunction denominator() {
        return denominator;
    }

    @Override
    public double applyAsDouble(double x) {
        return numerator.applyAsDouble(x)/denominator.applyAsDouble(x);
    }

    @Override
    public RationalFunction derive() {
        return new RationalFunction(numerator.mul(denominator.derive()).sub(numerator.derive().mul(denominator)), denominator.pow(2));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RationalFunction && equals((RationalFunction) obj);
    }

    public boolean equals(RationalFunction other) {
        return numerator.equals(other.numerator()) && denominator.equals(other.denominator());
    }
}
