package calculus.functions;

import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;

public class ParametricFunction extends MathFunction {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;

    public ParametricFunction(PolynomialFunction yFunction, PolynomialFunction xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
    }

    @Override
    public double at(double x) {
        return yFunction.at(x);
    }

    @Override
    public MathFunction derive() {
        return new RationalFunction(yFunction, xFunction);
    }

    @Override
    protected double shortestLength(double tStart, double tEnd) {
        return shortestLength(xFunction.at(tStart), at(tStart), xFunction.at(tEnd), at(tEnd));
    }

    @Override
    public String toString() {
        return "y= "+yFunction+"\nx= "+xFunction;
    }

    @Override
    public Linear linearOn(MathFunction derivative, double x) {
        return super.linearOn(derivative, xFunction.at(x));
    }
}
