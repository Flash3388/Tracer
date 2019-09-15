package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.Test;

public class LinearVelocityProfileTest {

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
}
