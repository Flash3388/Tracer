package calculus.functions;

import calculus.functions.polynomial.PolynomialFunction;
import util.MathUtil;

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
    protected double shortestLength(double xStart, double xEnd) {
        return MathUtil.distance(xFunction.applyAsDouble(xStart), applyAsDouble(xStart), xFunction.applyAsDouble(xEnd), applyAsDouble(xEnd));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParametricFunction && equals((ParametricFunction) obj);
    }

    public boolean equals(ParametricFunction other) {
        return xFunction.equals(other.tFunction()) && yFunction.equals(other.yFunction());
    }
}