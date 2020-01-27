package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.ParametricFunction;
import calculus.functions.SquareRootFunction;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.segments.Segment;
import com.jmath.ExtendedMath;
import com.jmath.interpolation.BarycentricRational;
import com.jmath.interpolation.Interpolation;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;

import java.util.*;

public class Spline implements Segment {
    private static final double ACCURACY = 0.001;

    private final PolynomialFunction yFunctionDerivative;
    private final PolynomialFunction xFunctionDerivative;
    private final MathFunction actualFunction;
    private final MathFunction lengthFunctionDerivative;
    private final Pair<double[], double[]> recordedDatapoints;

    private final double arcLength;
    private final double startLength;

    private double lastReachedPercentage;
    private double lastReachedLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction, double startLength) {
        this.yFunctionDerivative = yFunction.derive();
        this.xFunctionDerivative = xFunction.derive();
        this.startLength = startLength;

        actualFunction = new ParametricFunction(yFunction, xFunction);
        lengthFunctionDerivative = new SquareRootFunction(xFunctionDerivative.mul(xFunctionDerivative).add(yFunctionDerivative.mul(yFunctionDerivative)));
        arcLength = calcArcLength();
        recordedDatapoints = generateDatapoints();

        lastReachedPercentage = 0;
        lastReachedLength = 0;
    }

    public double length() {
        return arcLength;
    }

    public double angleRadAt(double length) {
        length = Math.abs(length);
        checkLength(length);

        double t = ExtendedMath.constrain(percentageAtLength(length - startLength), 0, 1);
        lastReachedPercentage = t;
        lastReachedLength = length;

        return Math.atan2(yFunctionDerivative.applyAsDouble(t), xFunctionDerivative.applyAsDouble(t));
    }

    @Override
    public double end() {
        return arcLength + startLength;
    }

    @Override
    public double start() {
        return startLength;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Spline && equals((Spline) obj);
    }

    public boolean equals(Spline other) {
        return ExtendedMath.equals(Math.atan2(yFunctionDerivative.applyAsDouble(0), xFunctionDerivative.applyAsDouble(0)), Math.atan2(other.yFunctionDerivative.applyAsDouble(0), other.xFunctionDerivative.applyAsDouble(0)), ACCURACY) &&
                ExtendedMath.equals(Math.atan2(yFunctionDerivative.applyAsDouble(1), xFunctionDerivative.applyAsDouble(1)), Math.atan2(other.yFunctionDerivative.applyAsDouble(1), other.xFunctionDerivative.applyAsDouble(1)), ACCURACY) &&
                ExtendedMath.equals(actualFunction.applyAsDouble(0), other.actualFunction.applyAsDouble(0), ACCURACY) &&
                ExtendedMath.equals(actualFunction.applyAsDouble(1), other.actualFunction.applyAsDouble(1), ACCURACY) &&
                ExtendedMath.equals(actualFunction.xAt(0), other.actualFunction.xAt(0), ACCURACY) &&
                ExtendedMath.equals(actualFunction.xAt(1), other.actualFunction.xAt(1), ACCURACY);
    }

    @Override
    public String toString() {
        return String.format("parametric: %s length: %.4f", actualFunction, arcLength);
    }

    private void checkLength(double length) {
        if(!ExtendedMath.constrained(length, startLength, end()))
            throw new IllegalArgumentException(String.format("Length %f is outside of this spline's length limit", length));
    }

    private double calcArcLength() {
        return lengthFunctionDerivative.integrate(0, 1);
    }

    private Pair<double[], double[]> generateDatapoints() {
        double[] lengths = new double[(int)(1/ACCURACY)];
        double[] percentages = new double[(int)(1/ACCURACY)];

        for (int i = 0; i < (int) (1/ACCURACY); ++i) {
            double percentage = i * ACCURACY;
            double length = lengthFunctionDerivative.integrate(0, percentage);
            lengths[i] = length;
            percentages[i] = percentage;
        }

        return new Pair<>(lengths, percentages);
    }

    private double percentageAtLength(double length) {
        double[] lengths = recordedDatapoints.getKey();
        double[] percentages = recordedDatapoints.getValue();

        for (int i = 0; i < lengths.length; i++) {
            double guess = lengths[i];
            if(guess >= length || ExtendedMath.equals(length, guess, ACCURACY))
                return percentages[i];
        }
        return 1;
    }
}
