package calculus.functions;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;

public class ParametricFunction extends MathFunction {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;

    public ParametricFunction(PolynomialFunction yFunction, PolynomialFunction xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
    }

    public PolynomialFunction yFunction() {
        return yFunction;
    }

    public PolynomialFunction tFunction() {
        return xFunction;
    }

    @Override
    public double applyAsDouble(double x) {
        return yFunction.applyAsDouble(x);
    }

    @Override
    public MathFunction derive() {
        return new RationalFunction(yFunction, xFunction);
    }

    @Override
    public String toString() {
        return String.format("parametric: {\ny= %s\nt= %s\n}", yFunction, xFunction);
    }

    @Override
    public Linear linearOn(MathFunction derivative, double t) {
        return super.linearOn(derivative, xFunction.applyAsDouble(t));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParametricFunction && equals((ParametricFunction) obj);
    }

    public boolean equals(ParametricFunction other) {
        return xFunction.equals(other.tFunction()) && yFunction.equals(other.yFunction());
    }
}
