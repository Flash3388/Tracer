package calculus.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tracer.units.angle.Angle;
import tracer.units.distance.Distance;

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
                Arguments.of(Arrays.asList(new Waypoint().shiftY(Distance.meters(width/2)), new Waypoint().shiftY(Distance.meters(width/2)).shiftX(Distance.meters(1))), Arrays.asList(new Waypoint().shiftY(Distance.meters(-width/2)), new Waypoint().shiftY(Distance.meters(-width/2)).shiftX(Distance.meters(1))), new TankTrajectory(SplineType.CUBIC_HERMITE, Distance.meters(width), new Waypoint(), new Waypoint().shiftX(Distance.meters(1)))),
                Arguments.of(Arrays.asList(new Waypoint().shiftY(Distance.meters(width/2)), new Waypoint(Distance.meters(1 - width/2), Distance.meters(1), Angle.degrees(90))), Arrays.asList(new Waypoint().shiftY(Distance.meters(-width/2)), new Waypoint(Distance.meters(1 + width/2), Distance.meters(1), Angle.degrees(90))), new TankTrajectory(SplineType.CUBIC_HERMITE, Distance.meters(width), new Waypoint(), new Waypoint().shiftXY(Distance.meters(1)).shiftHeading(Angle.degrees(90))))
        );
    }
}
