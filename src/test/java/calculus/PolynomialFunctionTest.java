package calculus;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolynomialFunctionTest {
    private final static PolynomialFunction EXAMPLE_FUNCTION = new PolynomialFunction(2.0, 2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_SAME_DEGREE = new PolynomialFunction(1.0, 2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_SMALLER_DEGREE = new PolynomialFunction(2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_HIGHER_DEGREE = new PolynomialFunction(1.0, 1.0, 2.0);

    @Test
    public void applyValue_returnsCorrectResult() {
        Double ACTUAL = EXAMPLE_FUNCTION.apply(1);
        Double EXPECTED = 4.0;

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void addFunctions_forFunctionsOfSameDegree_returnsSumOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.add(EXAMPLE_FUNCTION_OF_SAME_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(3.0, 4.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void addFunctions_forAFunctionsOfSmallerDegree_returnsSumOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.add(EXAMPLE_FUNCTION_OF_SMALLER_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(2.0, 4.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void addFunctions_forAFunctionsOfHigherDegree_returnsSumOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.add(EXAMPLE_FUNCTION_OF_HIGHER_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(1.0, 3.0, 4.0);

        assertEquals(EXPECTED, ACTUAL);
    }
}
