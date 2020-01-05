package calculus.splines;

import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HermiteCubicSplineTest {
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
                Arguments.of(splines.get(4).get()[0], new Spline(new PolynomialFunction(0, 0), new PolynomialFunction(-1, 0), 0)),
                Arguments.of(splines.get(5).get()[0], new Spline(new PolynomialFunction(-1, 2, 0, 0), new PolynomialFunction(-1, 1, 1, 0), 0))
        );
    }

    private static List<Arguments> provideSplines() {
        SplineFactory factory = new SplineFactory();

        return List.of(
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(45)), new Waypoint(1, 1, Math.toRadians(45)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 0, 0), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(90)), new Waypoint(0, 1, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(1,1, Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, Math.toRadians(180)), new Waypoint(-1, 0, Math.toRadians(180)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, new Waypoint(0,0, 0), new Waypoint(1, 1, Math.toRadians(90)), 0))
        );
    }
}
