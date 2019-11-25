package calculus.functions;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;

public class ParametricFunction extends MathFunction {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction tFunction;

    public ParametricFunction(PolynomialFunction yFunction, PolynomialFunction tFunction) {
        this.yFunction = yFunction;
        this.tFunction = tFunction;
    }

    public PolynomialFunction yFunction() {
        return yFunction;
    }

    public PolynomialFunction tFunction() {
        return tFunction;
    }

    @Override
    public double applyAsDouble(double x) {
        return yFunction.applyAsDouble(x);
    }

    @Override
    public MathFunction derive() {
        return new RationalFunction(yFunction, tFunction);
    }

    @Override
    public String toString() {
        return String.format("parametric: {\ny= %s\nt= %s\n}", yFunction, tFunction);
    }

    @Override
    public Linear linearOn(MathFunction derivative, double x) {
        return super.linearOn(derivative, tFunction.applyAsDouble(x));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParametricFunction && equals((ParametricFunction) obj);
    }

    public boolean equals(ParametricFunction other) {
        return tFunction.equals(other.tFunction()) && yFunction.equals(other.yFunction());
    }
}
