package calculus;

import calculus.variables.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariableTest {
    private final static double DEFAULT_DELTA = 0.001;

    @Test
    public void calcDerivative_forNumber_returnsZero() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = Variable.zero();
        final Variable DERIVATIVE = Variable.modifier(MODIFIER).derive();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcDerivative_forXOnlyWithModifier_returnsModifier() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = Variable.modifier(MODIFIER);
        final Variable DERIVATIVE = new Variable(MODIFIER,1).derive();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcDerivative_forXWithModifierAndPower_returnsXWithModifierAndReducedPower() {
        final double MODIFIER = 2.0;
        final int POWER = 2;

        final Variable EXPECTED_RESULT = new Variable(MODIFIER * POWER, POWER - 1);
        final Variable DERIVATIVE = new Variable(MODIFIER, POWER).derive();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcIntegral_forNumber_returnsXWithModifier() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = new Variable(MODIFIER, 1);
        final Variable DERIVATIVE = Variable.modifier(MODIFIER).integrate();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcIntegral_forXWithModifierAndPower_returnsXWithReducedModifierAndIncreasedPower() {
        final double MODIFIER = 2.0;
        final int POWER = 2;

        final Variable EXPECTED_RESULT = new Variable(MODIFIER / (POWER + 1), POWER + 1);
        final Variable DERIVATIVE = new Variable(MODIFIER, POWER).integrate();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void atValue_forXWithModifierAndPower_returnsPowerOfTheValueMultipliedByTheModifier() {
        final double MODIFIER = 2.0;
        final int POWER = 2;
        final double VALUE = 1.0;

        final Double EXPECTED_RESULT = Math.pow(VALUE, POWER) * MODIFIER;
        final Double ACTUAL = new Variable(MODIFIER, POWER).at(VALUE);

        assertEquals(ACTUAL, EXPECTED_RESULT);
    }

    @Test
    public void atValue_forXWithZeroPower_returnsModifier() {
        final double MODIFIER = 2.0;
        final double VALUE = 1.0;

        final double ACTUAL = Variable.modifier(MODIFIER).at(VALUE);

        assertEquals(ACTUAL, MODIFIER, DEFAULT_DELTA);
    }

    @Test
    public void atValue_forXWithNoModifier_returnsZero() {
        final double VALUE = 1.0;
        final int POWER = 2;

        final double ZERO = 0.0;
        final double ACTUAL = new Variable(ZERO, POWER).at(VALUE);

        assertEquals(ACTUAL, ZERO, DEFAULT_DELTA);
    }

    @Test
    public void mul_forTwoVariables_returnsAddedSquaresAndMultipliedModifiers() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 3.0;
        final int FIRST_POWER = 1;
        final int SECOND_POWER = 2;

        final double EXPECTED_MODIFIER = FIRST_MODIFIER * SECOND_MODIFIER;
        final int EXPECTED_POWER = FIRST_POWER + SECOND_POWER;

        final Variable EXPECTED_VARIABLE = new Variable(EXPECTED_MODIFIER, EXPECTED_POWER);

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, FIRST_POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, SECOND_POWER);
        final Variable ACTUAL_VARIABLE = FIRST_VARIABLE.mul(SECOND_VARIABLE);

        assertEquals(ACTUAL_VARIABLE, EXPECTED_VARIABLE);
    }

    @Test
    public void mul_forVariableByNumber_returnVariableWithModifierMultipliedByNumber() {
        final double INITIAL_MODIFIER = 1.0;
        final double MULTIPLICATION_MODIFIER = 2.0;
        final double EXPECTED_MODIFIER = INITIAL_MODIFIER * MULTIPLICATION_MODIFIER;

        final int INITIAL_POWER = 2;

        final Variable EXPECTED_VARIABLE = new Variable(EXPECTED_MODIFIER, INITIAL_POWER);

        final Variable INITIAL_VARIABLE = new Variable(INITIAL_MODIFIER, INITIAL_POWER);
        final Variable ACTUAL_VARIABLE = INITIAL_VARIABLE.mul(MULTIPLICATION_MODIFIER);

        assertEquals(ACTUAL_VARIABLE, EXPECTED_VARIABLE);
    }

    @Test
    public void add_forVariablesWithSamePowers_returnsVariableWithAddedModifiers() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int POWER = 2;
        final double EXPECTED_MODIFIER = FIRST_MODIFIER + SECOND_MODIFIER;

        final Variable EXPECTED_VARIABLE = new Variable(EXPECTED_MODIFIER, POWER);

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, POWER);
        final Variable ACTUAL_VARIABLE = FIRST_VARIABLE.add(SECOND_VARIABLE);

        assertEquals(ACTUAL_VARIABLE, EXPECTED_VARIABLE);
    }

    @Test
    public void add_forVariablesWithDifferentPowers_throwsException() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int FIRST_POWER = 2;
        final int SECOND_POWER = 1;

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, FIRST_POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, SECOND_POWER);

        assertThrows(IllegalArgumentException.class, () -> FIRST_VARIABLE.add(SECOND_VARIABLE));
    }

    @Test
    public void add_forVariableWithIncorrectPower_throwsCorrectException() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int FIRST_POWER = 2;
        final int SECOND_POWER = 1;

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, FIRST_POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, SECOND_POWER);

        assertThrows(IllegalArgumentException.class, () -> FIRST_VARIABLE.add(SECOND_VARIABLE));
    }

    @Test
    public void sub_forVariablesWithSamePowers_returnsVariableWithSubtractedModifiers() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int POWER = 2;
        final double EXPECTED_MODIFIER = FIRST_MODIFIER - SECOND_MODIFIER;

        final Variable EXPECTED_VARIABLE = new Variable(EXPECTED_MODIFIER, POWER);

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, POWER);
        final Variable ACTUAL_VARIABLE = FIRST_VARIABLE.subtract(SECOND_VARIABLE);

        assertEquals(ACTUAL_VARIABLE, EXPECTED_VARIABLE);
    }

    @Test
    public void sub_forVariablesWithDifferentPowers_throwsException() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int FIRST_POWER = 2;
        final int SECOND_POWER = 1;

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, FIRST_POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, SECOND_POWER);

        assertThrows(IllegalArgumentException.class, () -> FIRST_VARIABLE.subtract(SECOND_VARIABLE));
    }

    @Test
    public void sub_forVariableWithIncorrectPower_throwsCorrectException() {
        final double FIRST_MODIFIER = 2.0;
        final double SECOND_MODIFIER = 1.0;
        final int FIRST_POWER = 2;
        final int SECOND_POWER = 1;

        final Variable FIRST_VARIABLE = new Variable(FIRST_MODIFIER, FIRST_POWER);
        final Variable SECOND_VARIABLE = new Variable(SECOND_MODIFIER, SECOND_POWER);

        assertThrows(IllegalArgumentException.class, () -> FIRST_VARIABLE.subtract(SECOND_VARIABLE));
    }

    @Test
    public void square_forVariable_returnSquareOfVariable() {
        final double MODIFIER = 2.0;
        final int POWER = 2;

        final Variable EXPECTED = new Variable(MODIFIER * MODIFIER, POWER * POWER);
        final Variable ACTUAL = new Variable(MODIFIER, POWER).square();

        assertEquals(ACTUAL, EXPECTED);
    }
}
