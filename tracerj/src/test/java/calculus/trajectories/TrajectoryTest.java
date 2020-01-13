package calculus.trajectories;

import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tracer.trajectories.Trajectory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrajectoryTest {
    private final static double DEF_DELTA = 0.001;

    @ParameterizedTest
    @MethodSource("provideArgumentsForTrajectoryTest")
    public void angleAtTrajectory_returnsSameAngle(final Trajectory trajectory, final Spline correspondingSpline, final double length) {
        assertEquals(trajectory.angleRadAt(length), correspondingSpline.angleRadAt(length), DEF_DELTA);
    }

    @Test
    public void angleAt_lengthOutsideOfBounds_throwsException() {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 1, Math.toRadians(90)));

        assertThrows(IllegalArgumentException.class, () -> trajectory.angleRadAt(2));
    }

    private static Stream<Arguments> provideArgumentsForTrajectoryTest() {
        SplineFactory factory = new SplineFactory();

        return Stream.of(
                Arguments.of(new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 1, Math.toRadians(90))), factory.create(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 1, Math.toRadians(90)), 0), 1),
                Arguments.of(new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 0, 0), new Waypoint(2, 1, Math.toRadians(90))), factory.create(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 1, Math.toRadians(90)), 1), 2),
                Arguments.of(new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 0, 0), new Waypoint(2, 1, Math.toRadians(90)), new Waypoint(2, 2, Math.toRadians(90))), factory.create(SplineType.CUBIC_HERMITE, new Waypoint(), new Waypoint(1, 1, Math.toRadians(90)), 1), 2)
        );
    }
}
