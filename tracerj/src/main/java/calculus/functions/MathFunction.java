package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.Integrals;
import util.MathUtil;

import java.util.function.DoubleUnaryOperator;

public abstract class MathFunction implements DoubleUnaryOperator {
    private final static int SLICES = 100;

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
        return binarySearchPercentageAtLength(lengthFunctionDerivative, start, length, maxPercentage, accuracy, length, 0);
    }

    private double binarySearchPercentageAtLength(MathFunction lengthFunctionDerivative, double start, double length, double maxPercentage, double accuracy, double originalLength, double sum) {
        if(sum >= originalLength || ExtendedMath.equals(sum, length, accuracy))
            return start + maxPercentage;

        double shortestLength = shortestDistance(start, start + maxPercentage);

        if(shortestLength/2 > length)
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start, length, maxPercentage/2, accuracy, originalLength, sum);

        double lengthPassedInHalfOfTheWay = lengthFunctionDerivative.integrate(start, start + maxPercentage/2, 20);

        if(ExtendedMath.equals(lengthPassedInHalfOfTheWay, length, accuracy))
            return maxPercentage/2 + start;
        else if(lengthPassedInHalfOfTheWay < length)
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start + maxPercentage / 2, length-lengthPassedInHalfOfTheWay, maxPercentage/2, accuracy, originalLength, sum + lengthPassedInHalfOfTheWay);
        else
            return binarySearchPercentageAtLength(lengthFunctionDerivative, start, length, maxPercentage/2, accuracy, originalLength, sum);
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for (;sum < length; x+= accuracy)
            sum += shortestDistance(x, x + accuracy);

        return x;
    }

    private double shortestDistance(double tStart, double tEnd) {
        return MathUtil.distance(xAt(tStart), applyAsDouble(tStart), xAt(tEnd), applyAsDouble(tEnd));
    }
}