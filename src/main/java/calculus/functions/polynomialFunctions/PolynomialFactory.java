package calculus.functions.polynomialFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PolynomialFactory {
    private final Map<Integer, Function<List<Double>, PolynomialFunction>> polynomials;

    public PolynomialFactory() {
        polynomials = new HashMap<>();


    }

    public PolynomialFunction get(List<Double> constants) {
        int degree = constants.size()-1;

        if(degree < 1 || degree > 4)
            return new PolynomialFunction(constants);
        return polynomials.get(constants.size()).apply(constants);
    }
}
