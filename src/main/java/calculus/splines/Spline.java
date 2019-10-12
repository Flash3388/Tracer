package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.polynomialFunctions.PolynomialFunction;

public class Spline {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;
    private final MathFunction actualFunction;
    private final double arcLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        actualFunction = new ParametricFunction(yFunction, xFunction);

        arcLength = calcArcLength();

        System.out.println(actualFunction);
        System.out.println(arcLength);
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double x = xAtLength(length);

        return Math.atan2(yFunction.at(xFunction.realSolutionsTo(x).get(0)), x);
    }

    private double xAtLength(double length) {
        return actualFunction.pointAtLength(0, length, 0.01);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double calcArcLength() {
        return actualFunction.lengthAt(0, 1);
    }
}
