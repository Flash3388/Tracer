package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.SquareRootFunction;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.segments.Segment;
import com.jmath.ExtendedMath;

public class Spline implements Segment<Spline> {
    private static final double ACCURACY = 0.00001;

    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;
    private final MathFunction lengthFunctionDerivative;

    private final double arcLength;
    private final double startLength;

    private double lastReachedPercentage;
    private double lastReachedLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, double startLength) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        this.startLength = startLength;

        lengthFunctionDerivative = new SquareRootFunction(xFunction.derive().mul(xFunction.derive()).add(yFunction.derive().mul(yFunction.derive())));
        arcLength = calcArcLength();
        lastReachedPercentage = 0;
        lastReachedLength = 0;
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

    @Override
    public double end() {
        return arcLength + startLength;
    }

    @Override
    public double start() {
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
            length -= lastReachedLength;
        }
        return lengthFunctionDerivative.findIntegral(start, length, ACCURACY);
    }

    private void checkLength(double length) {
        if(!ExtendedMath.constrained(length, startLength, end()))
            throw new IllegalArgumentException(String.format("Length %f is outside of this spline's length limit", length));
    }

    private double calcArcLength() {
        return lengthFunctionDerivative.integrate(0, 1);
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
