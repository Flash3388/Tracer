package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.Test;
import tracer.motion.MotionParameters;

public class ConcaveProfileTest {

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
}
