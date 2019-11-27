package calculus.splines;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HermiteCubicSplineTest {
    @Test
    public void createInstance_ofLineSpline_returnsCorrectSpline() {
        final PolynomialFunction LINEAR_FUNCTION = new Linear(1, 0);

        Spline LINEAR_SPLINE = new HermiteCubicSpline(new Waypoint(0, 0, Math.PI/4),
                new Waypoint(1, 1, Math.PI/4),
                0);
        Spline EXPECTED = new Spline(LINEAR_FUNCTION, LINEAR_FUNCTION, 0);

        assertEquals(LINEAR_SPLINE, EXPECTED);
    }
}
