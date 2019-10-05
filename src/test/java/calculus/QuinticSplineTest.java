package calculus;

import calculus.splines.HermiteQuinticSpline;
import com.jmath.ExtendedMath;
import org.junit.Test;
import tracer.motion.Waypoint;

import static org.junit.Assert.assertTrue;

public class QuinticSplineTest {
    private final static double DEF_DELTA = 0.0001;

    @Test
    public void knotDistance_ofSpline_returnsCorrectKnotDistance() {
        final Waypoint start = Waypoint.centimetersRadians(0, 0, 0);
        final Waypoint end = Waypoint.centimetersRadians(1, 1, Math.PI);

        final Double EXPECTED = Math.sqrt( Math.pow(end.x()-start.x(), 2) + Math.pow(end.y() - start.y(), 2));
        final Double ACTUAL = new HermiteQuinticSpline(start, end).knotDistance();

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void arcLength_ofLineSpline_returnsCorrectArcLength() {
        final Waypoint start = Waypoint.centimetersRadians(0, 0, Math.PI/2);
        final Waypoint end = Waypoint.centimetersRadians(0, 1, Math.PI/2);

        final Double EXPECTED = end.y() - start.y();
        final Double ACTUAL = new HermiteQuinticSpline(start, end).length();

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }
}
