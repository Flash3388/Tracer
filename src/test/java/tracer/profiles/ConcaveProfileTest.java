package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import org.junit.Test;
import tracer.motion.MotionParameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcaveProfileTest {
    private final static double DEF_DELTA = 0.0001;

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.velocityAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.velocityAt(PROFILE.end().add(Time.seconds(0.1)));
    }


    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.distanceAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.distanceAt(PROFILE.end().add(Time.seconds(0.1)));
    }


    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.accelerationAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.accelerationAt(PROFILE.end().add(Time.seconds(0.1)));
    }


    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.jerkAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);

        PROFILE.jerkAt(PROFILE.end().add(Time.seconds(0.1)));
    }

    @Test
    public void velocityAt_forConcaveProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_VELOCITY + TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.velocityAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void accelerationAt_forConcaveProfileAtValidTime_returnsCorrespondingAcceleration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = TARGET.jerk() * RELATIVE_TIME_IN_SECONDS;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.accelerationAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void jerkAt_forConcaveProfileAtValidTime_returnsTargetJerk() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.jerkAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, TARGET.jerk(), DEF_DELTA));
    }

    @Test
    public void distanceAt_forConcaveProfileAtValidTime_returnsCorrespondingDistance() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 3)/6;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.accelerationAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void duration_ofConvexProfile_returnsCorrectDuration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time EXPECTED = Time.seconds(TARGET.acceleration()/TARGET.jerk());

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final Time ACTUAL = PROFILE.duration();

        assertEquals(ACTUAL, EXPECTED);
    }
}