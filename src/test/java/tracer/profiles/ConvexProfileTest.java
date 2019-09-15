package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import org.junit.Test;
import tracer.motion.MotionParameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvexProfileTest {
    private final static double DEF_DELTA = 0.0001;

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.velocityAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.velocityAt(PROFILE.end().add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.distanceAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void distanceAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.distanceAt(PROFILE.end().add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.accelerationAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void accelerationAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.accelerationAt(PROFILE.end().add(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.jerkAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void jerkAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);

        PROFILE.jerkAt(PROFILE.end().add(Time.seconds(0.1)));
    }

    @Test
    public void velocityAt_forConvexProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_VELOCITY + TARGET.acceleration() * RELATIVE_TIME_IN_SECONDS + -TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.velocityAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void accelerationAt_forConvexProfileAtValidTime_returnsCorrespondingAcceleration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_ACCELERATION + -TARGET.jerk() * RELATIVE_TIME_IN_SECONDS;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.accelerationAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void jerkAt_forConvexProfileAtValidTime_returnsTargetJerk() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final Double ACTUAL = -PROFILE.jerkAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, TARGET.jerk(), DEF_DELTA));
    }

    @Test
    public void distanceAt_forConvexProfileAtValidTime_returnsCorrespondingDistance() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final Double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + TARGET.acceleration() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2 + -TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 3)/6;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final Double ACTUAL = PROFILE.distanceAt(T);

        assertTrue(ExtendedMath.equals(ACTUAL, EXPECTED, DEF_DELTA));
    }

    @Test
    public void duration_ofConvexProfile_returnsCorrectDuration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time EXPECTED = Time.seconds(TARGET.acceleration()/TARGET.jerk());

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final Time ACTUAL = PROFILE.duration();

        assertEquals(ACTUAL, EXPECTED);
    }
}
