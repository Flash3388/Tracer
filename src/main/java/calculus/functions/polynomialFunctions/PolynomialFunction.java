package calculus.functions.polynomialFunctions;

import calculus.functions.MathFunction;
import calculus.functions.UnsolveableFunctionParametersException;
import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialFunction extends MathFunction {
    private final List<Variable> variables;

    public PolynomialFunction(List<Double> constants) {
        variables = generateFunction(constants);
    }

    public PolynomialFunction(List<Double> constants, int polynomialDegree) {
        if(constants.size() != polynomialDegree+1)
            throw new IncorrectNumberOfConstantsException(polynomialDegree);
        variables = generateFunction(constants);
    }

    public PolynomialFunction derivativeWithoutA() {
        List<Variable> withoutA = variables.subList(1, variables.size());
        return deriveConstructor.apply(withoutA);
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
        return deriveConstructor.apply(deriveVariables());
    }

    @Override
    public PolynomialFunction integrate() {
        return integralConstructor.apply(integralVariables());
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

    private List<Variable> deriveVariables() {
        return variables.stream()
                .map(Variable::derive)
                .collect(Collectors.toList());
    }

    private List<Variable> integralVariables() {
        return variables.stream()
                .map(Variable::integrate)
                .collect(Collectors.toList());
    }

    private static List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }
}