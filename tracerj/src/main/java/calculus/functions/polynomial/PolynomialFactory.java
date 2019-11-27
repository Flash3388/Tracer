package calculus.functions.polynomial;

import calculus.variables.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PolynomialFactory {
    private final Map<Integer, Function<List<Double>, PolynomialFunction>> polynomials;

    public PolynomialFactory() {
        polynomials = new HashMap<>();
        polynomials.put(1, Linear::new);
        polynomials.put(2, Quadratic::new);
        polynomials.put(3, Cubic::new);
        polynomials.put(4, Quartic::new);
    }

    public PolynomialFunction fromVariableModifiers(List<Variable> variables) {
        return fromConstants(toConstants(variables));
    }

    public PolynomialFunction fromConstants(List<Double> constants) {
        int degree = constants.size()-1;
        checkConstants(constants);

        if(polynomials.containsKey(degree))
            return new PolynomialFunction(constants);
        return polynomials.get(degree).apply(constants);
    }

    private List<Double> toConstants(List<Variable> variables) {
        return variables.stream()
                .mapToDouble(Variable::modifier)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private void checkConstants(List<Double> constants) {
        if(constants.isEmpty())
            throw new IllegalArgumentException("function cannot be created with no constants");
    }
}
