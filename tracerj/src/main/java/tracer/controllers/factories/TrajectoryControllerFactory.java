package tracer.controllers.factories;

import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TrajectoryController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.profiles.*;
import tracer.profiles.base.Profile;

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

    public TrajectoryController create(Trajectory trajectory, MotionState max, double maxVoltage, Time idleTimeAtEnd, boolean isForward) {
        Profile trajectoryProfile = extendProfile(trajectory, idleTimeAtEnd, max);
        return new TrajectoryController(
                pidControllerFactory.create(trajectoryProfile, maxVoltage),
                profileMotionControllerFactory.create(trajectoryProfile),
                trajectoryOrientationControllerFactory.create(trajectory, trajectoryProfile, isForward),
                maxVoltage
        );
    }

    private Profile extendProfile(Trajectory trajectory, Time idleTime, MotionState max) {
        Profile trajectoryProfile = ProfileFactory.createTrajectoryProfile(max, trajectory);
        return trajectoryProfile.then(ConstantVelocityProfile.continuation(trajectoryProfile, idleTime));
    }
}
