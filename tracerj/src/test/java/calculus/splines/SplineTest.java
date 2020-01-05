package calculus.splines;

import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.math.Mathf;
import com.jmath.ExtendedMath;
import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplineTest {
    private final static double DEF_DELTA = 0.001;

    @ParameterizedTest
    @MethodSource("provideSplinesForArcLength")
    public void arcLengthSpline_returnsCorrectArcLength(Spline spline, final double expectedLength) {
        assertEquals(spline.length(), expectedLength, DEF_DELTA);
    }

    @ParameterizedTest
    @MethodSource("provideSplinesForAngle")
    public void angleAtSpline_returnsAngle(Spline spline, double angleAtMiddle) {
        assertEquals(Mathf.translateInRange(spline.angleRadAt(0.5), Math.toRadians(360), true), angleAtMiddle, Math.toRadians(1));
    }

    private static Stream<Arguments> provideSplinesForArcLength() {
        List<Arguments> splines = provideSplines();

        return Stream.of(
                Arguments.of(splines.get(0).get()[0], Math.sqrt(2)),
                Arguments.of(splines.get(1).get()[0], Math.sqrt(2)),
                Arguments.of(splines.get(2).get()[0], 1),
                Arguments.of(splines.get(3).get()[0], 1),
                Arguments.of(splines.get(4).get()[0], 1),
                Arguments.of(splines.get(5).get()[0], 1),
                Arguments.of(splines.get(6).get()[0], 1),
                Arguments.of(splines.get(7).get()[0], 1),
                Arguments.of(splines.get(8).get()[0], 1),
                Arguments.of(splines.get(9).get()[0], 1.486),
                Arguments.of(splines.get(10).get()[0], 1.5244)
        );
    }

    private static Stream<Arguments> provideSplinesForAngle() {
        List<Arguments> splines = provideSplines();

        return Stream.of(
                Arguments.of(splines.get(0).get()[0], Math.toRadians(45)),
                Arguments.of(splines.get(1).get()[0], Math.toRadians(45)),
                Arguments.of(splines.get(2).get()[0], 0),
                Arguments.of(splines.get(3).get()[0], 0),
                Arguments.of(splines.get(4).get()[0], Math.toRadians(90)),
                Arguments.of(splines.get(5).get()[0], Math.toRadians(90)),
                Arguments.of(splines.get(6).get()[0], Math.toRadians(90)),
                Arguments.of(splines.get(7).get()[0], Math.toRadians(90)),
                Arguments.of(splines.get(8).get()[0], Math.toRadians(180)),
                Arguments.of(splines.get(9).get()[0], Math.toRadians(38.42)),
                Arguments.of(splines.get(10).get()[0], Math.toRadians(36.8))
        );
    }

    private static List<Arguments> provideSplines() {
        SplineFactory factory = new SplineFactory();

        return List.of(
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(45)), new Waypoint(1, 1, Math.toRadians(45)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(45)), new Waypoint(1, 1, Math.toRadians(45)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 0, 0), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 0, 0), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(90)), new Waypoint(0, 1, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(90)), new Waypoint(0, 1, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(1,1, Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(1,1, Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(180)), new Waypoint(-1, 0, Math.toRadians(180)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 1, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 1, Math.toRadians(90)), 0))
        );
    }
}
