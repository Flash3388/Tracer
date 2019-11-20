package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.polynomial.PolynomialFunction;
import com.jmath.ExtendedMath;

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
    }

    public MathFunction parametricFunction() {
        return actualFunction;
    }

    public PolynomialFunction yFunction() {
        return yFunction;
    }

    public PolynomialFunction xFunction() {
        return xFunction;
    }

    public double yAt(double t) {
        return yFunction.applyAsDouble(t);
    }

    public double xAt(double t) {
        return xFunction.applyAsDouble(t);
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

    public double angleRadAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double t = percentageAtLength(length - startLength);

        return Math.atan2(yFunction.derive().applyAsDouble(t), xFunction.derive().applyAsDouble(t));
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
        return actualFunction.lengthBetween(0, 1, ACCURACY/100);
    }

    public boolean equals(Spline other) {
        return ExtendedMath.equals(xAt(0), other.xAt(0), ACCURACY) && ExtendedMath.equals(yAt(0), other.yAt(0), ACCURACY)
                && ExtendedMath.equals(xAt(1), other.xAt(1), ACCURACY) && ExtendedMath.equals(yAt(1), other.yAt(1), ACCURACY);
    }
}
