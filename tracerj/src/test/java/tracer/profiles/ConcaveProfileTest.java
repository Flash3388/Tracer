package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.api.Test;
import tracer.motion.MotionState;
import tracer.units.distance.Distance;
import tracer.units.morion.Velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcaveProfileTest {
    private final static double DEF_DELTA = 0.001;

    @Test
    public void velocityAt_forConcaveProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionState TARGET = MotionState.meterUnits(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_VELOCITY + TARGET.jerk().valueAsMetersPerSecondCubed() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConcaveProfile PROFILE = new ConcaveProfile(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), TARGET);
        final double ACTUAL = PROFILE.state(T).velocity().valueAsMetersPerSecond();

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }

    @Test
    public void jerkAt_forConcaveProfileAtValidTime_returnsTargetJerk() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionState TARGET = MotionState.meterUnits(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_VELOCITY + TARGET.jerk().valueAsMetersPerSecondCubed() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConcaveProfile PROFILE = new ConcaveProfile(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), TARGET);
        final double ACTUAL = PROFILE.state(T).jerk().valueAsMetersPerSecondCubed();

        assertEquals(ACTUAL, TARGET.jerk().valueAsMetersPerSecondCubed(), DEF_DELTA);
    }

    @Test
    public void duration_ofConvexProfile_returnsCorrectDuration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionState TARGET = MotionState.meterUnits(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time EXPECTED = Time.seconds(TARGET.acceleration().valueAsMetersPerSecondSquared()/TARGET.jerk().valueAsMetersPerSecondCubed());

        final ConcaveProfile PROFILE = new ConcaveProfile(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), TARGET);
        final Time ACTUAL = PROFILE.duration();

        assertEquals(ACTUAL, EXPECTED);
    }
}
