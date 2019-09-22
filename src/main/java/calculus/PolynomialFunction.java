package calculus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class PolynomialFunction {
    private final List<Variable> variables;
    private final Function<List<Variable>, PolynomialFunction> deriveContructor;
    private final Function<List<Variable>, PolynomialFunction> integralConstructor;

    public PolynomialFunction(List<Variable> variables, Function<List<Variable>, PolynomialFunction> deriveContructor, Function<List<Variable>, PolynomialFunction> integralConstructor) {
        this.deriveContructor = deriveContructor;
        this.integralConstructor = integralConstructor;
        this.variables = new ArrayList<>();
        this.variables.addAll(variables);
    }

    public double at(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    public Variable get(int index) {
        return variables.get(index);
    }

    @Override
    public String toString() {
        return variables.toString();
    }

    public PolynomialFunction derive() {
        return deriveContructor.apply(deriveVariables());
    }

    public PolynomialFunction integral() {
        return integralConstructor.apply(integralVariables());
    }

    protected  List<Variable> deriveVariables() {
        return variables.stream()
                .map(Variable::derivative)
                .collect(Collectors.toList());
    }

    protected List<Variable> integralVariables() {
            return variables.stream()
                    .map(Variable::integral)
                    .collect(Collectors.toList());
    }

    protected static List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }
}
