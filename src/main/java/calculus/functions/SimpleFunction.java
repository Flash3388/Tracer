package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
    public SimpleFunction mul(SimpleFunction function) {
        return sum(multipliedFunctions(function));
    }

    @Override
    public SimpleFunction add(SimpleFunction function) {
        List<Variable> sum = new ArrayList<>();
        sum.addAll(variables);
        sum.addAll(function.variables());

        return new SimpleFunction(sum);
    }

    @Override
    public SimpleFunction derive() {
        return new SimpleFunction(derivedVariables());
    }

    @Override
    public SimpleFunction integrate() {
        return new SimpleFunction(integrateVariables());
    }

    private SimpleFunction sum(List<SimpleFunction> functions) {
        AtomicReference<SimpleFunction> result = new AtomicReference<>(functions.get(0));

        functions.stream()
                .skip(1)
                .forEach(function -> result.set(result.get().add(function)));

        return result.get();
    }

    private List<SimpleFunction> multipliedFunctions(SimpleFunction function) {
        return variables.stream()
                .map(variable -> variable.mul(function))
                .collect(Collectors.toList());
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

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
