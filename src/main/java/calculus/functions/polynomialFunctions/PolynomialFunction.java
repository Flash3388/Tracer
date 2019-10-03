package calculus.functions.polynomialFunctions;

import calculus.functions.MathFunction;
import calculus.functions.UnsolveableFunctionParametersException;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialFunction extends MathFunction {
    private final PolynomialFactory factory;
    private final List<Variable> variables;

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
        List<Double> constants = toConstants();
        return  factory.get(constants.subList(1, constants.size()));
    }

    @Override
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

    protected List<Complex> trySolve(double result) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
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

    private List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    private List<Double> toConstants() {
        return variables.stream()
                .mapToDouble(Variable::modifier)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}