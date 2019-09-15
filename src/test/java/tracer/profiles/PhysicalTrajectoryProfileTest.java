package tracer.profiles;

import calculus.SplineType;
import com.flash3388.flashlib.time.Time;
import org.junit.Test;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectory.FunctionalTrajectory;
import tracer.trajectory.PhysicalTrajectory;

import java.util.Arrays;

public class PhysicalTrajectoryProfileTest {

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeBeforeStart_throwsException()    {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                                                                                    new Position(0, 0, 0),
                                                                                    new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        PROFILE.velocityAt(INITIAL_TIME.sub(Time.seconds(0.1)));
    }

    @Test(expected = OutsideOfTimeBoundsException.class)
    public void velocityAt_forTimeAfterEnd_throwsException() {
        final double INITIAL_DISTANCE = 10;
        final double INITIAL_VELOCITY = 5;

        final Time INITIAL_TIME = Time.seconds(1);

        final FunctionalTrajectory FUNCTIONAL = new FunctionalTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(
                new Position(0, 0, 0),
                new Position(1, 0, 0)));
        final MotionParameters MAX = new MotionParameters(10, 5, 1);
        final PhysicalTrajectory TRAJECTORY = PhysicalTrajectory.fromFunctional(FUNCTIONAL, MAX, Time.seconds(0.02));

        final PhysicalTrajectoryProfile PROFILE = new PhysicalTrajectoryProfile(INITIAL_DISTANCE, INITIAL_VELOCITY, INITIAL_TIME, TRAJECTORY);

        PROFILE.velocityAt(PROFILE.end().add(Time.seconds(0.1)));
    }
}
