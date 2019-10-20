package calculus;

import calculus.splines.HermiteQuinticSpline;
import org.junit.Test;
import tracer.motion.Waypoint;
import tracer.motion.basic.Distance;

import static org.junit.Assert.assertEquals;

public class QuinticSplineTest {
    @Test
    public void arcLength_ofLineSpline_returnsCorrectArcLength() {
        final Waypoint start = new Waypoint(Distance.centimeters(0), Distance.centimeters(0), Math.PI/2);
        final Waypoint end = new Waypoint(Distance.centimeters(0), Distance.centimeters(1), Math.PI/2);

        final Distance EXPECTED = end.y().sub(start.y());
        final Distance ACTUAL = new HermiteQuinticSpline(start, end, Distance.centimeters(0)).length();

        assertEquals(ACTUAL, EXPECTED);
    }
}
