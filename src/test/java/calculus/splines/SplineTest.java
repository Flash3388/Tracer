package calculus.splines;

import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.ExtendedMath;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SplineTest {
    private final static double DEF_DELTA = 0.0001;
    private final static PolynomialFunction LINEAR_FUNCTION = new Linear(1, 0);
    private final static Spline LINEAR_SPLINE = new Spline(LINEAR_FUNCTION, new PolynomialFunction(1.0), 0);

    @Test
    public void arcLength_ofLineSpline_returnsCorrectArcLength() {
        final double EXPECTED = LINEAR_FUNCTION.lengthBetween(0, 1);
        final double ACTUAL = LINEAR_SPLINE.length();

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void angleAt_ofLineSpline_returnsAngle() throws LengthOutsideOfFunctionBoundsException {
        final double EXPECTED = 45;
        final double ACTUAL = LINEAR_SPLINE.angleAt(0.5);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test(expected = LengthOutsideOfFunctionBoundsException.class)
    public void angleAtLengthOutsideOfBounds_ofLineSpline_throwsException() throws LengthOutsideOfFunctionBoundsException {
        LINEAR_SPLINE.angleAt(-1);
    }
}
