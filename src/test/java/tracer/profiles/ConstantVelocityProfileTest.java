package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConstantVelocityProfileTest {
    private final static double DEF_DELTA = 0.0001;

    @Test
    public void velocityAt_forConstantVelocityProfileAtValidTime_returnsInitialVelocity() {
        final double INITIAL_DISTANCE = 10;
        final Double INITIAL_VELOCITY = 5.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final ConstantVelocityProfile PROFILE = new ConstantVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.velocityAt(T);

        assertEquals(ACTUAL, INITIAL_VELOCITY);
    }

    @Test
    public void accelerationAt_forConstantVelocityProfileAtValidTime_returnsZero() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5.0;
        final Double EXPECTED = 0.0;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final ConstantVelocityProfile PROFILE = new ConstantVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.accelerationAt(T);

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

        final ConstantVelocityProfile PROFILE = new ConstantVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.jerkAt(T);

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
        final Double EXPECTED = INITIAL_DISTANCE + RELATIVE_TIME_IN_SECONDS * INITIAL_VELOCITY;

        final ConstantVelocityProfile PROFILE = new ConstantVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.distanceAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }
}
