package calculus;

import calculus.functions.polynomial.*;
import com.jmath.complex.Complex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PolynomialFunctionTest {

    @RunWith(Parameterized.class)
    public static class solutionTest {
        @Parameterized.Parameter()
        public PolynomialFunction implementation;
        @Parameterized.Parameter(1)
        public double result;
        @Parameterized.Parameter(2)
        public List<Complex> expectedResult;

        @Parameterized.Parameters(name = "function({0}).solutionTo({1}) == {2}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new Linear(1, 1), 2, Collections.singletonList(new Complex(1, 0))},
                    {new Quadratic(1, 1, 1), 2, Arrays.asList(new Complex(0.6180339887498949, 0), new Complex(-1.618033988749895, 0))},
                    {new Cubic(1, 1, 1, 1), 2, Arrays.asList(new Complex(0.5436890126920766, 0), new Complex(-0.7718445063460383, 1.1151425080399373),
                            new Complex(-0.7718445063460383, -1.1151425080399373))},
                    {new Quartic(1, 1, 1, 1, 1), 2, Arrays.asList(new Complex(-0.11407063116458721, -1.2167460039743507), new Complex(0.5187900636758841, 0),
                            new Complex(-0.11407063116458738, 1.2167460039743507), new Complex(-1.2906488013467095, -0))}
            });
        }

        @Test
        public void solutionTo_forImplementationAndAnyResult_returnsCorrectResult() {
            assertEquals(expectedResult, implementation.solutionsTo(result));
        }
    }

    private final static PolynomialFunction EXAMPLE_FUNCTION = new PolynomialFunction(2.0, 2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_SAME_DEGREE = new PolynomialFunction(1.0, 2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_SMALLER_DEGREE = new PolynomialFunction(2.0);
    private final static PolynomialFunction EXAMPLE_FUNCTION_OF_HIGHER_DEGREE = new PolynomialFunction(1.0, 1.0, 2.0);

    @Test
    public void applyValue_returnsCorrectResult() {
        Double ACTUAL = EXAMPLE_FUNCTION.applyAsDouble(1);
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
