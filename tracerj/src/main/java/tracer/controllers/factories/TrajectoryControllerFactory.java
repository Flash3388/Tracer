package tracer.controllers.factories;

import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TrajectoryController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionParameters;
import tracer.profiles.ComplexProfile;
import tracer.profiles.LinearVelocityProfile;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;

import java.util.Arrays;

public class TrajectoryControllerFactory {
    private final ProfilePidControllerFactory pidControllerFactory;
    private final ProfileMotionControllerFactory profileMotionControllerFactory;
    private final TrajectoryOrientationControllerFactory trajectoryOrientationControllerFactory;

    private TrajectoryControllerFactory(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        pidControllerFactory = new ProfilePidControllerFactory(pidControllerParameters);
        profileMotionControllerFactory = new ProfileMotionControllerFactory(motionControllerParameters);
        trajectoryOrientationControllerFactory = new TrajectoryOrientationControllerFactory(gP);
    }

    public static TrajectoryControllerFactory left(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        return new TrajectoryControllerFactory(pidControllerParameters, motionControllerParameters, gP);
    }

    public static TrajectoryControllerFactory right(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        return new TrajectoryControllerFactory(pidControllerParameters, motionControllerParameters, -gP);
    }

    public static TrajectoryControllerFactory single(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters) {
        return new TrajectoryControllerFactory(pidControllerParameters, motionControllerParameters, 0);
    }

    public TrajectoryController create(Trajectory trajectory, MotionParameters max, double maxVoltage, Time idleTimeAtEnd, boolean isForward) {
        Profile trajectoryProfile = extendProfile(trajectory, idleTimeAtEnd, max);
        return new TrajectoryController(
                pidControllerFactory.create(trajectoryProfile, maxVoltage),
                profileMotionControllerFactory.create(trajectoryProfile),
                trajectoryOrientationControllerFactory.create(trajectory, trajectoryProfile, isForward),
                maxVoltage
        );
    }

    private Profile extendProfile(Trajectory trajectory, Time idleTime, MotionParameters max) {
        Profile standardTrajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        return new ComplexProfile(0, max, Time.milliseconds(0), Arrays.asList(standardTrajectoryProfile, new LinearVelocityProfile(standardTrajectoryProfile, idleTime)));
    }
}
