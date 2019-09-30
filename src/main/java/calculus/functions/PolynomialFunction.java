package calculus.functions;

import calculus.variables.Variable;
import com.jmath.complex.Complex;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class PolynomialFunction extends SimpleFunction {
    private final Function<List<Variable>, PolynomialFunction> deriveConstructor;
    private final Function<List<Variable>, PolynomialFunction> integralConstructor;

    public PolynomialFunction(List<Variable> variables, Function<List<Variable>, PolynomialFunction> deriveConstructor, Function<List<Variable>, PolynomialFunction> integralConstructor) {
        super(variables);
        this.deriveConstructor = deriveConstructor;
        this.integralConstructor = integralConstructor;
    }

    public List<Double> realSolutions(double result) throws UnsupportedOperationException {
        return solutions(result).stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }

    public List<Complex> solutions(double result) throws UnsupportedOperationException {
        try {
            return solve(result);
        } catch (FirstConstantException e) {
            List<Variable> withoutA = variables().subList(1, length());
            return deriveConstructor.apply(withoutA).solutions(result);
        }
    }

    protected static List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    private List<Complex> solve(double result) throws UnsupportedOperationException, FirstConstantException {
        if(get(0).modifier() == 0)
            throw new FirstConstantException();
        return trySolve(result);
    }
}
