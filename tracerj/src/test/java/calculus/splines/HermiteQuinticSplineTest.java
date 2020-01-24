package calculus.splines;

import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tracer.units.angle.Angle;
import tracer.units.distance.Distance;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HermiteQuinticSplineTest {
    @ParameterizedTest
    @MethodSource("provideSplinesForTest")
    public void createInstance_ofLineSpline_returnsCorrectSpline(final Spline actual, final Spline expected) {
        assertEquals(actual, expected);
    }

    private static Stream<Arguments> provideSplinesForTest() {
        List<Arguments> splines = provideSplines();

        return Stream.of(
                Arguments.of(splines.get(0).get()[0], new Spline(new PolynomialFunction(1, 0), new PolynomialFunction(1, 0), 0)),
                Arguments.of(splines.get(1).get()[0], new Spline(new PolynomialFunction(0, 0), new PolynomialFunction(1, 0), 0)),
                Arguments.of(splines.get(2).get()[0], new Spline(new PolynomialFunction(1, 0), new PolynomialFunction(0, 0), 0)),
                Arguments.of(splines.get(3).get()[0], new Spline(new PolynomialFunction(1, 1), new PolynomialFunction(0, 1), 0)),
                Arguments.of(splines.get(4).get()[0], new Spline(new PolynomialFunction(-1, 2, 0, 0), new PolynomialFunction(-1, 1, 1, 0), 0))
        );
    }

    private static List<Arguments> provideSplines() {
        SplineFactory factory = new SplineFactory();

        return List.of(
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(Distance.meters(0), Distance.meters(0), Angle.degrees(45)), new Waypoint(Distance.meters(1), Distance.meters(1), Angle.degrees(45)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(Distance.meters(0), Distance.meters(0), Angle.degrees(0)), new Waypoint(Distance.meters(1), Distance.meters(0), Angle.degrees(0)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(Distance.meters(0), Distance.meters(0), Angle.degrees(90)), new Waypoint(Distance.meters(0), Distance.meters(1), Angle.degrees(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(Distance.meters(1), Distance.meters(1), Angle.degrees(90)), new Waypoint(Distance.meters(1), Distance.meters(2), Angle.degrees(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, new Waypoint(Distance.meters(0), Distance.meters(0), Angle.degrees(0)), new Waypoint(Distance.meters(1), Distance.meters(1), Angle.degrees(90)), 0))
        );
    }
}
