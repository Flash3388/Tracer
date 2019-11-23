package calculus.splines;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SplineTest {
    private final static double DEF_DELTA = 0.001;
    private final static PolynomialFunction LINEAR_FUNCTION = new Linear(1, 0);
    private final static Spline LINEAR_SPLINE = new Spline(LINEAR_FUNCTION, LINEAR_FUNCTION, 0);

    @Test
    public void arcLength_ofLineSpline_returnsCorrectArcLength() {
        final double EXPECTED = LINEAR_FUNCTION.lengthBetween(0, 1, DEF_DELTA);
        final double ACTUAL = LINEAR_SPLINE.length();

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }

    @Test
    public void angleAt_ofLineSpline_returnsAngle() {
        final double EXPECTED = Math.PI/4;
        final double ACTUAL = LINEAR_SPLINE.angleRadAt(0.5);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }
}
