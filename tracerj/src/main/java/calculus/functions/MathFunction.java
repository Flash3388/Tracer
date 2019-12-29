package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.Integrals;
import util.MathUtil;

import java.util.function.DoubleUnaryOperator;

public abstract class MathFunction implements DoubleUnaryOperator {
    private final static int SLICES = 1000;

    public abstract MathFunction derive();

    public double xAt(double t) {
        return t;
    }

    public double integrate(double from, double to) {
        return integrate(from, to, SLICES);
    }

    public double integrate(double from, double to, int slices) {
        return Integrals.simpsonsRule(this::applyAsDouble, from, to, slices);
    }

    public double binarySearchPercentageAtLength(MathFunction lengthFunctionDerivative, double start, double length, double maxPercentage, double accuracy) {
        double shortestLength = shortestDistance(start, start + maxPercentage);

        if(shortestLength/2 > length)
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start, length, maxPercentage/2, accuracy);

        double lengthPassedInHalfOfTheWay = lengthFunctionDerivative.integrate(start, start + maxPercentage/2, 10);

        if(ExtendedMath.constrained(lengthPassedInHalfOfTheWay, length-accuracy, length+accuracy))
            return maxPercentage/2 + start;
        else if(lengthPassedInHalfOfTheWay < length)
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start + maxPercentage / 2, length-lengthPassedInHalfOfTheWay, maxPercentage/2, accuracy);
        else
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start, length, maxPercentage/2, accuracy);
    }

    private double shortestDistance(double tStart, double tEnd) {
        return MathUtil.distance(xAt(tStart), applyAsDouble(tStart), xAt(tEnd), applyAsDouble(tEnd));
    }
}