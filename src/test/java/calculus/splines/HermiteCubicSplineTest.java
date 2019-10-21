package calculus.splines;

import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import org.junit.Test;
import tracer.motion.Waypoint;

import static org.junit.Assert.assertEquals;

public class HermiteCubicSplineTest {
    @Test
    public void createInstance_ofLineSpline_returnsCorrectSpline() {
        PolynomialFunction LINEAR_FUNCTION = new Linear(1, 0);

        Spline LINEAR_SPLINE = new HermiteCubicSpline(Waypoint.centimetersRadians(0, 0, Math.PI/4),
                Waypoint.centimetersRadians(1, 1, Math.PI/4),
                0);
        Spline EXPECTED = new Spline(LINEAR_FUNCTION, new PolynomialFunction(1.0), 0);

        assertEquals(LINEAR_SPLINE, EXPECTED);
    }
}
