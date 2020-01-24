package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.api.Test;
import tracer.motion.MotionState;
import tracer.units.distance.Distance;
import tracer.units.morion.Velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantVelocityProfileTest {
    private final static double DEF_DELTA = 0.001;

    @Test
    public void velocityAt_forConstantVelocityProfileAtValidTime_returnsInitialVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final ConstantVelocityProfile PROFILE = ConstantVelocityProfile.continuation(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).velocity().valueAsMetersPerSecond();

        assertEquals(ACTUAL, INITIAL_VELOCITY, DEF_DELTA);
    }

    @Test
    public void accelerationAt_forConstantVelocityProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5.0;
        final Double EXPECTED = 0.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final ConstantVelocityProfile PROFILE = ConstantVelocityProfile.continuation(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).acceleration().valueAsMetersPerSecondSquared();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void jerkAt_forConstantVelocityProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5.0;
        final Double EXPECTED = 0.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final ConstantVelocityProfile PROFILE = ConstantVelocityProfile.continuation(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).jerk().valueAsMetersPerSecondCubed();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void distanceAt_forConstantVelocityProfileAtValidTime_returnsCorrespondingDistance() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_DISTANCE + RELATIVE_TIME_IN_SECONDS * INITIAL_VELOCITY;

        final ConstantVelocityProfile PROFILE = ConstantVelocityProfile.continuation(new ProfileState(Distance.meters(INITIAL_DISTANCE), MotionState.constantVelocity(Velocity.metersPerSecond(INITIAL_VELOCITY)), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).distance().valueAsMeters();

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }
}
