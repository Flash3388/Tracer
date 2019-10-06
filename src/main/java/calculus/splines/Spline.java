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
    private final PolynomialFunction yFunction;
    private final Linear xFunction;
    private final MathFunction lengthFunction;
    private final double arcLength;

    public Spline(PolynomialFunction yFunction, Linear xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;

        lengthFunction = findLengthFunction(yFunction, xFunction);
        arcLength = lengthAt(1);

        System.out.println(xFunction);
        System.out.println(yFunction);
        System.out.println(lengthFunction);
        System.out.println(arcLength);
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double x = xAtLength(length);

        return Math.atan2(yFunction.at(x), x);
    }

    private MathFunction findLengthFunction(PolynomialFunction yFunction, Linear xFunction) {//I'm tired and I hate this method
        int actual = yFunction.actualDegree();

        if(actual == 0)
            return xFunction;
        else if(actual == 1)
            return yFunction;
        else
            return new RootFunction(yFunction.derive().pow(2).add(xFunction.derive().pow(2)), 2).integrate();
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

    private double xAtLength(double length) {
        double result;

        try {
            result = filterSolutions(lengthFunction.realSolutionsTo(length), length);
        } catch (UnsupportedOperationException e) {
            result = newtonMethod(length, 0.01);
        }

        return xFunction.at(result);
    }

    private double newtonMethod(double target, double accuracy) {
        MathFunction derived = new RootFunction(yFunction.derive().pow(2).add(xFunction.derive().pow(2)), 2);
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
