package calculus;

import calculus.functions.PolynomialFunction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PolynomialFunctionTest {

    @Test
    public void constructor_forTwoVariables_returnsFunctionWithTheseVariables() {
        final Variable FIRST_VARIABLE = new Variable(2, 2);
        final Variable SECOND_VARIABLE = new Variable(3, 3);

        final PolynomialFunction ACTUAL = new PolynomialFunction(FIRST_VARIABLE, SECOND_VARIABLE);

        assertEquals(Arrays.asList(FIRST_VARIABLE, SECOND_VARIABLE), Arrays.asList(ACTUAL.get(0), ACTUAL.get(1)));
    }

    @Test
    public void fromConstants_forTwoConstants_returnsFunctionWithCorrectVariables() {
        final double FIRST_CONSTANT = 3.0;
        final double SECOND_CONSTANT = 2.0;

        final Variable FIRST_VARIABLE = new Variable(FIRST_CONSTANT, 1);
        final Variable SECOND_VARIABLE = new Variable(SECOND_CONSTANT, 0);
        final PolynomialFunction EXPECTED = new PolynomialFunction(FIRST_VARIABLE, SECOND_VARIABLE);

        final PolynomialFunction ACTUAL = PolynomialFunction.fromConstants(FIRST_CONSTANT, SECOND_CONSTANT);

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void derivative_forFunction_returnsDerivativeOfVariables() {
        final Variable FIRST_VARIABLE = new Variable(2, 2);
        final Variable SECOND_VARIABLE = new Variable(3, 3);
        final Variable FIRST_VARIABLE_DERIVATIVE = FIRST_VARIABLE.derivative();
        final Variable SECOND_VARIABLE_DERIVATIVE = SECOND_VARIABLE.derivative();

        final PolynomialFunction EXPECTED = new PolynomialFunction(FIRST_VARIABLE_DERIVATIVE, SECOND_VARIABLE_DERIVATIVE);
        final PolynomialFunction ACTUAL = new PolynomialFunction(FIRST_VARIABLE, SECOND_VARIABLE).derive();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void integral_forFunction_returnsIntegralOfVariables() {
        final Variable FIRST_VARIABLE = new Variable(2, 2);
        final Variable SECOND_VARIABLE = new Variable(3, 3);
        final Variable FIRST_VARIABLE_DERIVATIVE = FIRST_VARIABLE.integral();
        final Variable SECOND_VARIABLE_DERIVATIVE = SECOND_VARIABLE.integral();

        final PolynomialFunction EXPECTED = new PolynomialFunction(FIRST_VARIABLE_DERIVATIVE, SECOND_VARIABLE_DERIVATIVE);
        final PolynomialFunction ACTUAL = new PolynomialFunction(FIRST_VARIABLE, SECOND_VARIABLE).integral();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void add_forTwoFunctions_returnsFunctionThatIncludesBothFunctions() {
        final Variable FIRST_FUNCTION_FIRST_VARIABLE = new Variable(2, 2);
        final Variable FIRST_FUNCTION_SECOND_VARIABLE = new Variable(3, 3);
        final Variable SECOND_FUNCTION_FIRST_VARIABLE = new Variable(1, 1);
        final Variable SECOND_FUNCTION_SECOND_VARIABLE = new Variable(4, 2);

        final PolynomialFunction EXPECTED = new PolynomialFunction(FIRST_FUNCTION_FIRST_VARIABLE,
                FIRST_FUNCTION_SECOND_VARIABLE,
                SECOND_FUNCTION_FIRST_VARIABLE,
                SECOND_FUNCTION_SECOND_VARIABLE);

        final PolynomialFunction FIRST_FUNCTION = new PolynomialFunction(FIRST_FUNCTION_FIRST_VARIABLE, FIRST_FUNCTION_SECOND_VARIABLE);
        final PolynomialFunction SECOND_FUNCTION = new PolynomialFunction(SECOND_FUNCTION_FIRST_VARIABLE, SECOND_FUNCTION_SECOND_VARIABLE);
        final PolynomialFunction ACTUAL = FIRST_FUNCTION.add(SECOND_FUNCTION);

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void atValue_forFunction_returnsResultAt() {
        final Variable FIRST_VARIABLE = new Variable(2, 2);
        final Variable SECOND_VARIABLE = new Variable(3, 3);
        final double VALUE = 1;

        final Double EXPECTED = FIRST_VARIABLE.at(VALUE) + SECOND_VARIABLE.at(VALUE);
        final Double ACTUAL = new PolynomialFunction(FIRST_VARIABLE, SECOND_VARIABLE).at(VALUE);

        assertEquals(ACTUAL, EXPECTED);
    }
}
