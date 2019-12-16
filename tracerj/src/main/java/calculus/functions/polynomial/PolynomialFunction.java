package calculus.functions.polynomial;

import calculus.functions.MathFunction;
import calculus.variables.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialFunction extends MathFunction {
    private final List<Variable> variables;

    public PolynomialFunction(Double... constants) {
        this(Arrays.asList(constants));
    }

    public PolynomialFunction(List<Double> constants) {
        this(constants, constants.size()-1);
    }

    public static PolynomialFunction fromVariableModifiers(List<Variable> variables) {
        return new PolynomialFunction(toConstants(variables));
    }

    public PolynomialFunction(List<Double> constants, int polynomialDegree) {
        if(constants.size() != polynomialDegree+1)
            throw new IllegalArgumentException("Number of constants is incorrect for polynomial degree of %d");
        variables = generateFunction(constants);
    }

    @Override
    public double applyAsDouble(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    @Override
    public String toString() {
        AtomicReference<String> result = new AtomicReference<>("");
        result.set(result.get() + variables.get(0));

        variables.stream()
                .skip(1)
                .forEach(variable -> result.set(result.get()+" +"+variable));

        return result.get();
    }

    @Override
    public PolynomialFunction derive() {
        return fromVariableModifiers(deriveVariables());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PolynomialFunction && equals((PolynomialFunction) obj);
    }

    public boolean equals(PolynomialFunction other) {
        return variables.equals(other.variables());
    }

    public PolynomialFunction pow(int degree) {
        AtomicReference<PolynomialFunction> result = new AtomicReference<>(new PolynomialFunction(1.0));

        IntStream.range(0, degree)
                .forEach(i -> result.set(result.get().mul(this)));

        return result.get();
    }

    public PolynomialFunction mul(PolynomialFunction other) {
        List<PolynomialFunction> products = other.variables().stream()
                .map(this::mul)
                .collect(Collectors.toList());


        return sum(products);
    }

    public PolynomialFunction mul(double scalar) {
        return mul(new Variable(scalar, 0));
    }

    public PolynomialFunction mul(Variable var) {
        List<Variable> result = variables.stream()
                .map(var::mul)
                .collect(Collectors.toList());
        result.addAll(zeroVariables(var.power()));

        return fromVariableModifiers(result);
    }

    public PolynomialFunction sub(PolynomialFunction other) {
        return add(other.mul(-1));
    }

    public PolynomialFunction add(double other) {
        return add(new PolynomialFunction(other));
    }

    public PolynomialFunction add(PolynomialFunction other) {
        List<Variable> result = other.variables().stream()
                .map(this::add)
                .collect(Collectors.toList());

        if(variables.size() > result.size()) {
            List<Variable> tmp = new ArrayList<>(variables.subList(0, variables.size() - result.size()));
            tmp.addAll(result);
            result = tmp;
        }

        return fromVariableModifiers(result);
    }

    private static List<Double> toConstants(List<Variable> variables) {
        return variables.stream()
                .mapToDouble(Variable::modifier)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private List<Variable> variables() {
        return variables;
    }

    private PolynomialFunction sum(List<PolynomialFunction> functions) {
        AtomicReference<PolynomialFunction> sum = new AtomicReference<>(functions.get(0));

        functions.stream()
                .skip(1)
                .forEach(product -> sum.set(sum.get().add(product)));

        return sum.get();
    }

    private Variable add(Variable var) {
        int index = variables.size() - var.power()-1;

        if(index >= 0 )
            return variables.get(index).add(var);
        else {
            return var;
        }
    }

    private List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    private List<Variable> deriveVariables() {
        return variables.stream()
                .limit(variables.size()-1)
                .map(Variable::derive)
                .collect(Collectors.toList());
    }

    private List<Variable> zeroVariables(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Variable(0, 0))
                .collect(Collectors.toList());
    }
}