package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.api.Test;
import tracer.motion.MotionParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvexProfileTest {
    private final static double DEF_DELTA = 0.001;

    @Test
    public void velocityAt_forConvexProfileAtValidTime_returnsCorrespondingVelocity() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;
        final double INITIAL_ACCELERATION = 2.5;
        final MotionParameters TARGET = new MotionParameters(10, 2.5, 1);

        final Time INITIAL_TIME = Time.seconds(1);
        final Time T = INITIAL_TIME.add(Time.seconds(0.1));

        final double RELATIVE_TIME_IN_SECONDS = T.sub(INITIAL_TIME).valueAsMillis() / 1000.0;
        final double EXPECTED = INITIAL_VELOCITY + TARGET.acceleration() * RELATIVE_TIME_IN_SECONDS + -TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.velocityAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
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
        final double EXPECTED = INITIAL_ACCELERATION + -TARGET.jerk() * RELATIVE_TIME_IN_SECONDS;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.accelerationAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
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
        final double ACTUAL = -PROFILE.jerkAt(T);

        assertEquals(ACTUAL, TARGET.jerk(), DEF_DELTA);
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
        final double EXPECTED = INITIAL_DISTANCE + INITIAL_VELOCITY * RELATIVE_TIME_IN_SECONDS + TARGET.acceleration() * Math.pow(RELATIVE_TIME_IN_SECONDS, 2)/2 + -TARGET.jerk() * Math.pow(RELATIVE_TIME_IN_SECONDS, 3)/6;

        final ConvexProfile PROFILE = new ConvexProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_ACCELERATION, TARGET, INITIAL_TIME);
        final double ACTUAL = PROFILE.distanceAt(T);

        assertEquals(ACTUAL, EXPECTED, DEF_DELTA);
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
