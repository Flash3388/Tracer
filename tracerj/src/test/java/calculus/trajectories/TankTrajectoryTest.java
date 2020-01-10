package calculus.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tracer.trajectories.TankTrajectory;
import tracer.trajectories.Trajectory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankTrajectoryTest {
    @ParameterizedTest
    @MethodSource("provideExpectedAndActualWaypoints")
    public void generateTankTrajectory_returnsCorrectlyAdjustedTrajectory(final List<Waypoint> expectedLeft, final List<Waypoint> expectedRight, final TankTrajectory actual) {
        final Trajectory EXPECTED_LEFT = new Trajectory(SplineType.CUBIC_HERMITE, expectedLeft);
        final Trajectory EXPECTED_RIGHT = new Trajectory(SplineType.CUBIC_HERMITE, expectedRight);

        assertEquals(EXPECTED_LEFT, actual.left());
        assertEquals(EXPECTED_RIGHT, actual.right());
    }

    private static Stream<Arguments> provideExpectedAndActualWaypoints() {
        double width = 1;

        return Stream.of(
                Arguments.of(Arrays.asList(new Waypoint(0, width/2, 0), new Waypoint(1, width/2, 0)), Arrays.asList(new Waypoint(0, -width/2, 0), new Waypoint(1, -width/2, 0)), new TankTrajectory(SplineType.CUBIC_HERMITE, width, new Waypoint(0, 0, 0), new Waypoint(1, 0, 0)))
        );
    }
}
