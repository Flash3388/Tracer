package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import tracer.motion.basic.Distance;
import tracer.motion.basic.units.UnitConversion;

public class Spline {
    private final static double ACCURACY = 0.001;

    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;

    private final MathFunction actualFunction;
    private final Distance arcLength;
    private final Distance startLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, Distance startLength) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        this.startLength = startLength;

        actualFunction = new ParametricFunction(yFunction, xFunction);
        arcLength = calcArcLength();

        System.out.println(actualFunction);
        System.out.println(arcLength);
    }

    public Distance length() {
        return arcLength;
    }

    public Distance absoluteLength() {
        return arcLength.add(startLength);
    }

    public Distance startLength() {
        return startLength;
    }

    public double angleAt(Distance length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double t = percentageAtLength(length.sub(startLength));

        return Math.atan2(yFunction.derive().apply(t), xFunction.derive().apply(t));
    }

    private double percentageAtLength(Distance length) {
        return actualFunction.pointAtLength(0, UnitConversion.toCentimeters(length), ACCURACY);
    }

    private void checkLength(Distance length) throws LengthOutsideOfFunctionBoundsException {
        length = length.sub(startLength);
        if(length.smallerThen(Distance.centimeters(0)) || length.largerThen(arcLength))
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private Distance calcArcLength() {
        return Distance.centimeters(actualFunction.lengthBetween(0, 1));
    }
}
