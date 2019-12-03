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

    private double lastReachedPercentage;
    private double lastReachedLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, double startLength) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        this.startLength = startLength;

        actualFunction = new ParametricFunction(yFunction, xFunction);
        arcLength = calcArcLength();
        lastReachedPercentage = 0;
        lastReachedLength = 0;
    }

    public MathFunction parametricFunction() {
        return actualFunction;
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

    public double angleRadAt(double length) {
        checkLength(length);

        length -= startLength;
        double t = percentageAtLength(length - startLength);
        lastReachedPercentage = t;
        lastReachedLength = length;

        return Math.atan2(yFunction.derive().applyAsDouble(t), xFunction.derive().applyAsDouble(t));
    }

    private double percentageAtLength(double length) {
        double start = 0;
        if(length > lastReachedLength) {
            start = lastReachedPercentage;
            length =- lastReachedLength;
        }
        return actualFunction.pointAtLength(start, length, ACCURACY);
    }

    private void checkLength(double length) {
        if(!ExtendedMath.constrained(length, startLength, absoluteLength()))
            throw new IllegalArgumentException(String.format("Length %f is outside of this spline's length limit", length));
    }

    private double calcArcLength() {
        return actualFunction.lengthBetween(0, 1, ACCURACY/100);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Spline && equals((Spline) obj);
    }

    public boolean equals(Spline other) {
        return ExtendedMath.equals(xAt(0), other.xAt(0), ACCURACY) && ExtendedMath.equals(yAt(0), other.yAt(0), ACCURACY)
                && ExtendedMath.equals(xAt(1), other.xAt(1), ACCURACY) && ExtendedMath.equals(yAt(1), other.yAt(1), ACCURACY);
    }
}
