package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.api.Test;
import tracer.motion.MotionParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcaveProfileTest {
    private final static double DEF_DELTA = 0.001;

    @Test
    public void velocityAt_forConcaveProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_VELOCITY + TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.velocityAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }

    @Test
    public void accelerationAt_forConcaveProfileAtValidTime_returnsCorrespondingAcceleration() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = TARGET.jerk() * RELATIVE_TIME_IN_SECONDS;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.accelerationAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
    }

    @Test
    public void jerkAt_forConcaveProfileAtValidTime_returnsTargetJerk() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.jerkAt(T);

        assertEquals(ACTUAL, TARGET.jerk(), DEF_DELTA);
    }

    @Test
    public void distanceAt_forConcaveProfileAtValidTime_returnsCorrespondingDistance() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 3)/6;

        final ConcaveProfile PROFILE = new ConcaveProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.distanceAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
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
