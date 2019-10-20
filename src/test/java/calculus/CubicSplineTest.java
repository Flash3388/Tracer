package calculus;

import calculus.splines.HermiteCubicSpline;
import com.jmath.ExtendedMath;
import org.junit.Test;
import tracer.motion.Waypoint;

import static org.junit.Assert.assertTrue;

public class CubicSplineTest {
    private final static double DEF_DELTA = 0.0001;

    @Test
    public void arcLength_ofLineSpline_returnsCorrectArcLength() {
        final Waypoint start = Waypoint.centimetersRadians(0, 0, Math.PI/2);
            final Waypoint end = Waypoint.centimetersRadians(0, 1, Math.PI/2);

        final Double EXPECTED = end.y() - start.y();
        final Double ACTUAL = new HermiteCubicSpline(start, end, 0).length();

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }
}
