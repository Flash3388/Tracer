package calculus.splines;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplineTest {
    private final static double DEF_DELTA = 0.001;

    @ParameterizedTest
    @MethodSource("provideSplinesForArcLength")
    public void arcLength_ofLineSpline_returnsCorrectArcLength(Spline spline, double expectedLength) {
        final double ACTUAL = spline.length();

        assertEquals(ACTUAL, expectedLength, DEF_DELTA);
    }

    @Test
    public void angleAt_ofLineSpline_returnsAngle() {
//        final double EXPECTED = Math.PI/4;
//        final double ACTUAL = LINEAR_SPLINE.angleRadAt(0.5);

//        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }

    private static Stream<Arguments> provideSplinesForArcLength() {
        SplineFactory factory = new SplineFactory();
        return Stream.of(
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(45)), new Waypoint(1, 1, Math.toRadians(45)), 0), Math.sqrt(2)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(45)), new Waypoint(1, 1, Math.toRadians(45)), 0), Math.sqrt(2)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(0)), new Waypoint(1, 0, Math.toRadians(0)), 0), 1),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(0)), new Waypoint(1, 0, Math.toRadians(0)), 0), 1),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(90)), new Waypoint(0, 1, Math.toRadians(90)), 0), 1),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(90)), new Waypoint(0, 1, Math.toRadians(90)), 0), 1),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(1,1, Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0), 1),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(1,1, Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0), 1),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(180)), new Waypoint(-1, 0, Math.toRadians(180)), 0), 1),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(0,0, Math.toRadians(180)), new Waypoint(-1, 0, Math.toRadians(180)), 0), 1)
        );
    }
}
