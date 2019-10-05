package calculus.splines;

import calculus.functions.MathFunction;
import calculus.functions.RootFunction;
import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.ExtendedMath;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Spline {
    private final PolynomialFunction function;
    private final Linear percentageToXFunction;
    private final MathFunction lengthFunction;
    private final double arcLength;

    public Spline(PolynomialFunction function, Linear percentageToXFunction) {
        this.function = function;
        this.percentageToXFunction = percentageToXFunction;

        lengthFunction = new RootFunction(function.derive().pow(2).add(1.0), 2).integrate();
        arcLength = lengthAt(1);

        System.out.println(function);
        System.out.println(arcLength);
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double x = xAt(length);

        return Math.atan2(function.at(x), x);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double filterSolutions(List<Double> solutions, double length) {
        return solutions.stream()
                .filter(solution -> ExtendedMath.constrained(solution, 0, 1))
                .min(Comparator.comparingDouble(solution -> Math.abs(length - lengthAt(solution))))
                .orElseGet(() -> 0.0);
    }

    private double lengthAt(double percentage) {
        return lengthFunction.difference(0, percentage);
    }

    private double xAt(double length) {
        double result;

        try {
            result = filterSolutions(lengthFunction.realSolutionsTo(length), length);
        } catch (UnsupportedOperationException e) {
            result = newtonMethod(length, 0.01);
        }

        return percentageToX(result);
    }

    private double percentageToX(double percentage) {
        return percentageToXFunction.at(percentage);
    }

    private double newtonMethod(double target, double accuracy) {
        MathFunction derived = new RootFunction(function.derive().pow(2).add(1.0), 2);
        double randomized = new Random().nextDouble();
        System.out.println("randomized " + randomized);

        if(isCorrect(randomized, target, accuracy))
            return randomized;
        else
            return nextGuess(derived, randomized, target, accuracy);
    }

    private double nextGuess(MathFunction derivative, double current, double target, double accuracy) {
        double m = derivative.at(current);
        double y = lengthFunction.at(current);
        Linear tangent = new Linear(m, current, y);
        double guess = tangent.realSolutionsTo(target).get(0);

        System.out.println(guess);

        if(isCorrect(guess, target, accuracy))
            return guess;
        else
            return nextGuess(derivative, guess, target, accuracy);
    }

    private boolean isCorrect(double result, double target, double accuracy) {
        return ExtendedMath.constrained(lengthAt(result), target-accuracy, target+accuracy);
    }
}
