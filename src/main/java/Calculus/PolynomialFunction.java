package Calculus;

import Tracer.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialFunction {
    private final List<Variable> variables;

    public PolynomialFunction(List<Variable> variables) {
        this.variables = new ArrayList<>();
        this.variables.addAll(variables);
    }

    public PolynomialFunction(Variable... variables) {
        this(Arrays.asList(variables));
    }

    public static PolynomialFunction fromConstants(List<Double> multipliers) {
        return new PolynomialFunction(generateFunction(multipliers));
    }

    private static List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    public double at(double t) {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);

        variables.forEach(variable -> sum.updateAndGet(v -> v + variable.calcValue(t)));
        return sum.get();
    }

    public PolynomialFunction derivative() {
        return new PolynomialFunction(variables.stream()
                .limit(variables.size()-1)
                .map(Variable::derivative)
                .collect(Collectors.toList()));
    }

    public PolynomialFunction integral() {
        return new PolynomialFunction(variables.stream()
                .map(Variable::integral)
                .collect(Collectors.toList()));
    }

    public PolynomialFunction pow(int power) {
        return new PolynomialFunction(variables.stream()
                .map(variable -> new Variable(variable.getMultiplier(), variable.getPower() * power))
                .collect(Collectors.toList()));
    }

    public double calcArcLength(double start, double end) {
        double startLength = getArcLengthAt(start);
        double endLength = getArcLengthAt(end);

        return endLength - startLength;
    }

    private double getArcLengthAt(double t) {
        return Math.sqrt(t + derivative().pow(2).integral().at(t));
    }
}
