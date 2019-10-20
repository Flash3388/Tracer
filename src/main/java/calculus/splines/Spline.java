package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.polynomialFunctions.PolynomialFunction;

public class Spline {
    private final static double ACCURACY = 0.001;

    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;

    private final MathFunction actualFunction;
    private final double arcLength;
    private final double startLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, double startLength) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        this.startLength = startLength;

        actualFunction = new ParametricFunction(yFunction, xFunction);
        arcLength = calcArcLength();

        System.out.println(actualFunction);
        System.out.println(arcLength);
    }

    public double length() {
        return arcLength;
    }

    public double absoluteLength() {
        return arcLength + startLength;
    }

    public double startLength() {
        return startLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {//in radians
        checkLength(length);
        double t = percentageAtLength(length - startLength);

        return Math.atan2(yFunction.derive().apply(t), xFunction.derive().apply(t));
    }

    private double percentageAtLength(double length) {
        return actualFunction.pointAtLength(0, length, ACCURACY);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        length -= startLength;
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double calcArcLength() {
        return actualFunction.lengthAt(0, 1);
    }
}
