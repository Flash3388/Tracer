package calculus.functions.polynomialFunctions;

import calculus.functions.MathFunction;
import calculus.functions.UnsolveableFunctionParametersException;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.Arrays;
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
        this(constants, constants.size()+1);
    }

    public PolynomialFunction(List<Double> constants, int polynomialDegree) {
        if(constants.size() != polynomialDegree+1)
            throw new IncorrectNumberOfConstantsException(polynomialDegree);
        variables = generateFunction(constants);
        factory = new PolynomialFactory();
    }

    public PolynomialFunction derivativeWithoutA() {
        return factory.getConverted(variables.subList(1, variables.size()));
    }

    @Override
    public double at(double x) {
        return variables.stream()
                .mapToDouble(variable -> variable.at(x))
                .sum();
    }

    public List<Variable> variables() {
        return variables;
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
    public PolynomialFunction integrate() {
        return factory.get(integralVariables());
    }

    @Override
    public List<Complex> solutionsTo(double result) throws UnsupportedOperationException, UnsolveableFunctionParametersException {
        if(get(0).modifier() == 0)
            return derivativeWithoutA().solutionsTo(result);
        else
            return trySolve(result);
    }

    public PolynomialFunction pow(int degree) {
        AtomicReference<PolynomialFunction> result = new AtomicReference<>(this);

        IntStream.range(0, degree)
                .skip(1)
                .forEach(i -> result.set(result.get().mul(result.get())));

        return result.get();
    }

    public PolynomialFunction mul(PolynomialFunction other) {
        List<PolynomialFunction> products = other.variables().stream()
                .map(this::mul)
                .collect(Collectors.toList());
        AtomicReference<PolynomialFunction> sum = new AtomicReference<>(products.get(0));

        products.stream()
                .skip(1)
                .forEach(product -> sum.set(sum.get().add(product)));

        return sum.get();
    }

    public PolynomialFunction mul(double scalar) {
        return mul(new Variable(scalar, 0));
    }

    public PolynomialFunction mul(Variable var) {
        return factory.getConverted(
                variables.stream()
                        .map(var::mul)
                        .collect(Collectors.toList()));
    }

    public PolynomialFunction sub(PolynomialFunction other) {
        return add(other.mul(-1));
    }

    public PolynomialFunction add(PolynomialFunction other) {
        List<Variable> result = other.variables().stream()
                .map(this::add)
                .collect(Collectors.toList());

        if(variables.size() - result.size() > 0) {
            List<Variable> tmp = variables.subList(0, variables.size() - result.size());
            tmp.addAll(result);
            result = tmp;
        }

        return factory.getConverted(result);
    }

    private Variable add(Variable var) {
        int index = variables.size() - var.power()-1;

        if(index > 0 )
            return variables.get(index).add(var);
        else {
            return var;
        }
    }

    protected List<Complex> trySolve(double result) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
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

    private List<Double> integralVariables() {
        List<Double> result = variables.stream()
                .map(variable -> variable.integrate().modifier())
                .collect(Collectors.toList());
        result.remove(result.size()-1);
        return result;
    }

    private List<Variable> zeroVariables(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Variable(0, 0))
                .collect(Collectors.toList());
    }
}