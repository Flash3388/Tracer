package calculus.functions;

import calculus.variables.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleFunction extends MathFunction {
    private final List<Variable> variables;

    public SimpleFunction(List<Variable> variables) {
        this.variables = List.copyOf(variables);
    }

    @Override
    public String toString() {
        return variables.toString();
    }

    public Variable get(int index) {
        return variables.get(index);
    }

    public List<Variable> variables() {
        return variables;
    }

    public int length() {
        return variables.size();
    }

    @Override
    public double at(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    @Override
    public SimpleFunction derive() {
        return new SimpleFunction(derivedVariables());
    }

    @Override
    public SimpleFunction integrate() {
        return new SimpleFunction(integrateVariables());
    }

    private List<Variable> derivedVariables() {
        return variables.stream()
                .map(Variable::derive)
                .collect(Collectors.toList());
    }

    private List<Variable> integrateVariables() {
        return variables.stream()
                .map(Variable::integrate)
                .collect(Collectors.toList());
    }
}
