package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.SquareRootFunction;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.segments.Segment;
import com.jmath.ExtendedMath;

public class Spline implements Segment {
    private static final double ACCURACY = 0.0001;

    private final PolynomialFunction yFunctionDerivative;
    private final PolynomialFunction xFunctionDerivative;
    private final MathFunction actualFunction;

    private final double arcLength;
    private final double startLength;

    private double lastReachedPercentage;
    private double lastReachedLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, double startLength) {
        this.yFunctionDerivative = yFunction.derive();
        this.xFunctionDerivative = xFunction.derive();
        this.startLength = startLength;

        actualFunction = new ParametricFunction(yFunction, xFunction);
        arcLength = calcArcLength();
        lastReachedPercentage = 0;
        lastReachedLength = 0;
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
        double t = ExtendedMath.constrain(percentageAtLength(length - startLength), 0, 1);
        lastReachedPercentage = t;
        lastReachedLength = length;

        return Math.atan2(yFunctionDerivative.applyAsDouble(t), xFunctionDerivative.applyAsDouble(t));
    }

    private double percentageAtLength(double length) {
        double start = 0;

        if(length > lastReachedLength) {
            start = lastReachedPercentage;
            length -= lastReachedLength;
        }
        
        return actualFunction.pointAtLength(start, length, length/200);
    }

    private void checkLength(double length) {
        if(!ExtendedMath.constrained(length, startLength, end()))
            throw new IllegalArgumentException(String.format("Length %f is outside of this spline's length limit", length));
    }

    private double calcArcLength() {
        return new SquareRootFunction(xFunctionDerivative.mul(xFunctionDerivative).add(yFunctionDerivative.mul(yFunctionDerivative))).integrate(0, 1);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Spline && equals((Spline) obj);
    }

    public boolean equals(Spline other) {
        return ExtendedMath.equals(yFunctionDerivative.applyAsDouble(0), other.yFunctionDerivative.applyAsDouble(0), ACCURACY) &&
                ExtendedMath.equals(yFunctionDerivative.applyAsDouble(1), other.yFunctionDerivative.applyAsDouble(1), ACCURACY) &&
                ExtendedMath.equals(xFunctionDerivative.applyAsDouble(0), other.xFunctionDerivative.applyAsDouble(0), ACCURACY) &&
                ExtendedMath.equals(xFunctionDerivative.applyAsDouble(1), other.xFunctionDerivative.applyAsDouble(1), ACCURACY) &&
                ExtendedMath.equals(actualFunction.applyAsDouble(0), other.actualFunction.applyAsDouble(0), ACCURACY) &&
                ExtendedMath.equals(actualFunction.applyAsDouble(1), other.actualFunction.applyAsDouble(1), ACCURACY) &&
                ExtendedMath.equals(actualFunction.xAt(0), other.actualFunction.xAt(0), ACCURACY) &&
                ExtendedMath.equals(actualFunction.xAt(1), other.actualFunction.xAt(1), ACCURACY);
    }
}
