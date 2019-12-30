package calculus.functions;

import calculus.functions.polynomial.PolynomialFunction;

public class SquareRootFunction extends MathFunction {
    private final PolynomialFunction inner;

    public SquareRootFunction(PolynomialFunction inner) {
        this.inner = inner;
    }

    @Override
    public RationalFunction derive() {
        return new RationalFunction(inner.derive(), inner.mul(2));
    }

    @Override
    public double applyAsDouble(double operand) {
        return Math.sqrt(inner.applyAsDouble(operand));
    }
}
