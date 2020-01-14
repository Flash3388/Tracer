package calculus.splines;

import calculus.functions.polynomial.PolynomialFunction;
import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.math.Mathf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SplineTest {
    private final static double DEF_DELTA = 0.001;

    @ParameterizedTest
    @MethodSource("provideSplinesForArcLength")
    public void arcLengthSpline_returnsCorrectArcLength(final Spline spline, final double expectedLength) {
        assertEquals(spline.length(), expectedLength, DEF_DELTA);
    }

    @ParameterizedTest
    @MethodSource("provideSplinesForAngle")
    public void angleAtSpline_returnsAngle(final Spline spline, final double angleAtMiddle) {
        assertEquals(Mathf.translateInRange(spline.angleRadAt(spline.length()/2), Math.toRadians(360), true), angleAtMiddle, Math.toRadians(0.1));
    }

    @Test
    public void angleAtSpline_forLengthOutsideOfSpline_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Spline(new PolynomialFunction(0, 0), new PolynomialFunction(1, 0), 0).angleRadAt(2));
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
                Arguments.of(splines.get(9).get()[0], Math.toRadians(45)),
                Arguments.of(splines.get(10).get()[0], Math.toRadians(45.7))
        );
    }

    private static List<Arguments> provideSplines() {
        SplineFactory factory = new SplineFactory();
        Waypoint base = new Waypoint();

        return List.of(
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base.shiftHeading(Math.toRadians(45)), base.shiftHeading(Math.toRadians(45)).shiftXY(1), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, base.shiftHeading(Math.toRadians(45)), base.shiftHeading(Math.toRadians(45)).shiftXY(1), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base, base.shiftX(1), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, base, base.shiftX(1), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base.shiftHeading(Math.toRadians(90)), base.shiftHeading(Math.toRadians(90)).shiftY(1), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, base.shiftHeading(Math.toRadians(90)), base.shiftHeading(Math.toRadians(90)).shiftY(1), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base.shiftXY(1).shiftHeading(Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, base.shiftXY(1).shiftHeading(Math.toRadians(90)), new Waypoint(1, 2, Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base.shiftHeading(Math.toRadians(180)), base.shiftX(-1).shiftHeading(Math.toRadians(180)), 0)),
                Arguments.of(factory.create(SplineType.CUBIC_HERMITE, base, base.shiftXY(1).shiftHeading(Math.toRadians(90)), 0)),
                Arguments.of(factory.create(SplineType.QUINTIC_HERMITE, base, base.shiftXY(1).shiftHeading(Math.toRadians(90)), 0))
        );
    }
}
