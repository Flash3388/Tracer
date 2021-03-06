package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.api.Test;
import tracer.motion.MotionState;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearVelocityProfileTest {
    private final static double DEFAULT_DELTA = 0.0001;

    @Test
    public void velocityAt_forLinearVelocityProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_VELOCITY + RELATIVE_TIME_IN_SECONDS * INITIAL_ACCELERATION;

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(new ProfileState(INITIAL_DISTANCE, MotionState.linearVelocity(INITIAL_VELOCITY, INITIAL_ACCELERATION), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).velocity();

        assertEquals(ACTUAL, EXPECTED, DEFAULT_DELTA);
    }

    @Test
    public void accelerationAt_forLinearVelocityProfileAtValidTime_returnsInitialAcceleration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(new ProfileState(INITIAL_DISTANCE, MotionState.linearVelocity(INITIAL_VELOCITY, INITIAL_ACCELERATION), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).acceleration();

        assertEquals(ACTUAL, INITIAL_ACCELERATION, DEFAULT_DELTA);
    }

    @Test
    public void jerkAt_forLinearVelocityProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final Double EXPECTED = 0.0;
        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(new ProfileState(INITIAL_DISTANCE, MotionState.linearVelocity(INITIAL_VELOCITY, INITIAL_ACCELERATION), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.state(T).jerk();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void distanceAt_forLinearVelocityProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + INITIAL_ACCELERATION * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(new ProfileState(INITIAL_DISTANCE, MotionState.linearVelocity(INITIAL_VELOCITY, INITIAL_ACCELERATION), INITIAL_TIME), END_TIME.sub(INITIAL_TIME));
        final double ACTUAL = PROFILE.state(T).distance();

        assertEquals(ACTUAL, EXPECTED, DEFAULT_DELTA);
    }
}
