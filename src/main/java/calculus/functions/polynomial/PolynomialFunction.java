package calculus.functions.polynomial;

import calculus.functions.MathFunction;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialFunction extends MathFunction {
    private final PolynomialFactory factory;
    private final List<Variable> variables;

    public PolynomialFunction(Double... constants) {
        this(Arrays.asList(constants));
    }

    public PolynomialFunction(List<Double> constants) {
        this(constants, constants.size()-1);
    }

    public PolynomialFunction(List<Double> constants, int polynomialDegree) {
        if(constants.size() != polynomialDegree+1)
            throw new IncorrectNumberOfConstantsException(polynomialDegree);
        variables = generateFunction(constants);
        factory = new PolynomialFactory();
    }

    @Override
    public double applyAsDouble(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    public Variable get(int index) {
        return variables.get(index);
    }

    @Override
    public String toString() {
        return variables.toString().replace('[',' ').replace(']',' ').replace(',','+');
    }

    @Override
    public PolynomialFunction derive() {
        return factory.get(deriveVariables());
    }

    @Override
    public Collection<Complex> solutionsTo(double result) throws IllegalArgumentException {
        if(get(0).modifier() == 0)
            return derivativeWithoutA().solutionsTo(result);
        else
            return trySolve(result);
    }

    public List<Double> modifiers() {
        List<Double> modifiers = new ArrayList<>();
        for (Variable var : variables) {
            modifiers.add(var.modifier());
        }

        return modifiers;
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

        return factory.getConverted(result);
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

        return factory.getConverted(result);
    }

    protected Collection<Complex> trySolve(double that) {
        throw new UnsupportedOperationException("solve method is not provided for a polynomial of degree " + (variables.size()-1));
    }

    private List<Variable> variables() {
        return variables;
    }

    private PolynomialFunction derivativeWithoutA() {
        return factory.getConverted(new ArrayList<>(variables.subList(1, variables.size())));
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

    private List<Double> deriveVariables() {
        return variables.stream()
                .limit(variables.size()-1)
                .map(variable -> variable.derive().modifier())
                .collect(Collectors.toList());
    }

    private List<Variable> zeroVariables(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Variable(0, 0))
                .collect(Collectors.toList());
    }
}