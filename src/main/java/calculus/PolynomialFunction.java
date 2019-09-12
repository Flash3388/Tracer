package calculus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public double at(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    public PolynomialFunction derivative() {
        return new PolynomialFunction(
                variables.stream()
                .map(Variable::derivative)
                .collect(Collectors.toList()));
    }

    public PolynomialFunction integral() {
        return new PolynomialFunction(variables.stream()
                .map(Variable::integral)
                .collect(Collectors.toList()));
    }

    public PolynomialFunction add(Variable variable) {
        List<Variable> sum = new ArrayList<>(variables);
        sum.add(variable);

        return new PolynomialFunction(sum);
    }

    public PolynomialFunction add(double num) {
        return add(new Variable(num, 0));
    }

    @Override
    public String toString() {
        return variables.toString();
    }
}
