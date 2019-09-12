package calculus;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableTest {

    @Test
    public void calcDerivative_forNumber_ReturnsZero() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = Variable.zero();
        final Variable DERIVATIVE = Variable.modifier(MODIFIER).derivative();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcDerivative_forXOnlyWithModifier_ReturnsModifier() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = Variable.modifier(MODIFIER);
        final Variable DERIVATIVE = new Variable(MODIFIER,1).derivative();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcDerivative_forXWithModifierAndPower_ReturnsXWithModifierAndReducedPower() {
        final double MODIFIER = 2.0;
        final double POWER = 2.0;

        final Variable EXPECTED_RESULT = new Variable(MODIFIER * POWER, POWER - 1);
        final Variable DERIVATIVE = new Variable(MODIFIER, POWER).derivative();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcIntegral_forNumber_ReturnsXWithModifier() {
        final double MODIFIER = 2.0;
        final Variable EXPECTED_RESULT = new Variable(MODIFIER, 1);
        final Variable DERIVATIVE = Variable.modifier(MODIFIER).integral();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }

    @Test
    public void calcIntegral_forXWithModifierAndPower_ReturnsXWithReducedModifierAndIncreasedPower() {
        final double MODIFIER = 2.0;
        final double POWER = 2.0;

        final Variable EXPECTED_RESULT = new Variable(MODIFIER / (POWER + 1), POWER + 1);
        final Variable DERIVATIVE = new Variable(MODIFIER, POWER).integral();

        assertEquals(DERIVATIVE, EXPECTED_RESULT);
    }
}
