package calculus.splines;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HermiteQuinticSplineTest {
    @Test
    public void createInstance_ofLineSpline_returnsCorrectSpline() {
        PolynomialFunction LINEAR_FUNCTION = new Linear(1, 0);

        Spline LINEAR_SPLINE = new HermiteQuinticSpline(new Waypoint(0, 0, Math.PI/4),
                new Waypoint(1, 1, Math.PI/4),
                0);
        Spline EXPECTED = new Spline(LINEAR_FUNCTION, LINEAR_FUNCTION, 0);

        assertTrue(LINEAR_SPLINE.equals(EXPECTED));
    }
}
