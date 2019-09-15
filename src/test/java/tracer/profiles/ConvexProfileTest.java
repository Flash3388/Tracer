package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.Test;
import tracer.motion.MotionParameters;

public class ConvexProfileTest {

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
}
