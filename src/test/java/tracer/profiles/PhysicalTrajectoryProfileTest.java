package tracer.profiles;

import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import org.junit.Test;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectory.FunctionalTrajectory;
import tracer.trajectory.PhysicalTrajectory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhysicalTrajectoryProfileTest {
    private final static double DEF_DELTA = 0.0001;

    @Test
    public void velocityAt_forPhysicalTrajectoryProfileAtValidTime_returnsInitialVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                new Position(0, 0, 0),
                new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        final Double EXPECTED = TRAJECTORY.closestStep(T.sub(INITIAL_TIME)).velocity();
        final Double ACTUAL = PROFILE.velocityAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void accelerationAt_forPhysicalTrajectoryProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                new Position(0, 0, 0),
                new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        final Double EXPECTED = TRAJECTORY.closestStep(T.sub(INITIAL_TIME)).acceleration();
        final Double ACTUAL = PROFILE.accelerationAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void jerkAt_forPhysicalTrajectoryProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                new Position(0, 0, 0),
                new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        final Double EXPECTED = TRAJECTORY.closestStep(T.sub(INITIAL_TIME)).jerk();
        final Double ACTUAL = PROFILE.jerkAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void distanceAt_forPhysicalTrajectoryProfileAtValidTime_returnsCorrespondingDistance() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                new Position(0, 0, 0),
                new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        final Double EXPECTED = TRAJECTORY.closestStep(T.sub(INITIAL_TIME)).distance();
        final Double ACTUAL = PROFILE.distanceAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }
}
