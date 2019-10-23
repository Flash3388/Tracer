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

    @Test
    public void subtractFunctions_forFunctionsOfSameDegree_returnsDifferenceOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.sub(EXAMPLE_FUNCTION_OF_SAME_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(1.0, 0.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void subtractFunctions_forAFunctionsOfSmallerDegree_returnsDifferenceOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.sub(EXAMPLE_FUNCTION_OF_SMALLER_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(2.0, 0.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void subtractFunctions_forAFunctionsOfHigherDegree_returnsDifferenceOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.sub(EXAMPLE_FUNCTION_OF_HIGHER_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(-1.0, 1.0, 0.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void multiplicationFunctions_forAnyFunctions_returnsProductOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.mul(EXAMPLE_FUNCTION_OF_SAME_DEGREE);
        PolynomialFunction EXPECTED = new PolynomialFunction(2.0, 6.0, 4.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void powerOfFunctions_forAnyFunctions_returnsSquareOfFunctions() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.pow(2);
        PolynomialFunction EXPECTED = new PolynomialFunction(4.0, 8.0, 4.0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void derivativeFunction_forAnyFunctions_returnsDerivativeOfFunction() {
        PolynomialFunction ACTUAL = EXAMPLE_FUNCTION.derive();
        PolynomialFunction EXPECTED = new PolynomialFunction(2.0);

        assertEquals(EXPECTED, ACTUAL);
    }
}
