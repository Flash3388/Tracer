package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinearVelocityProfileTest {
    private final static double DEF_DELTA = 0.0001;

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.velocityAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.velocityAt(END_TIME.add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.distanceAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.distanceAt(END_TIME.add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.accelerationAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.accelerationAt(END_TIME.add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.jerkAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));

        PROFILE.jerkAt(END_TIME.add(Time.seconds(0.1)));
    }

    @Test
    public void velocityAt_forLinearVelocityProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_VELOCITY + RELATIVE_TIME_IN_SECONDS * INITIAL_ACCELERATION;

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.velocityAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void accelerationAt_forLinearVelocityProfileAtValidTime_returnsInitialAcceleration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final Double INITIAL_ACCELERATION = 2.5;

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));
        final Time END_TIME = Time.seconds(2);

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.accelerationAt(T);

        assertEquals(ACTUAL, INITIAL_ACCELERATION);
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
        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.jerkAt(T);

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
        final Double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + INITIAL_ACCELERATION * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final LinearVelocityProfile PROFILE = new LinearVelocityProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, INITIAL_TIME, END_TIME.sub(INITIAL_TIME));
        final Double ACTUAL = PROFILE.distanceAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }
}
