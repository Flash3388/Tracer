package tracer.controllers.factories;

import calculus.trajectories.TankTrajectory;
import com.flash3388.flashlib.robot.motion.Direction;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.profiles.ConstantVelocityProfile;
import tracer.profiles.ProfileFactory;
import tracer.profiles.base.Profile;

public class TankTrajectoryControllerFactory {
    private final ProfilePidControllerFactory pidControllerFactory;
    private final ProfileMotionControllerFactory motionControllerFactory;
    private final TrajectoryOrientationControllerFactory orientationControllerFactory;

    public TankTrajectoryControllerFactory(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        pidControllerFactory = new ProfilePidControllerFactory(pidControllerParameters);
        motionControllerFactory = new ProfileMotionControllerFactory(motionControllerParameters);
        orientationControllerFactory = new TrajectoryOrientationControllerFactory(gP);
    }

    public TankTrajectoryController create(TankTrajectory trajectory, MotionState max, double maxVoltage, Direction direction) {
        Profile right = ProfileFactory.createTrajectoryProfile(max, trajectory.right());
        Profile left = ProfileFactory.createTrajectoryProfile(max, trajectory.left());
        boolean isRightLonger = isRightLonger(right, left);

        if(isRightLonger)
            left = extendProfile(left, calcIdleTime(right, left, true));
        else
            right = extendProfile(right, calcIdleTime(right, left, false));

        return new TankTrajectoryController(
                maxVoltage,
                pidControllerFactory.create(right, maxVoltage),
                motionControllerFactory.create(right),
                pidControllerFactory.create(left, maxVoltage),
                motionControllerFactory.create(left),
                orientationControllerFactory.create(isRightLonger ? trajectory.right() : trajectory.left(), isRightLonger ? right : left, direction)
        );
    }

    private boolean isRightLonger(Profile right, Profile left) {
        return right.deltaState().timestamp().after(left.deltaState().timestamp());
    }

    private Time calcIdleTime(Profile right, Profile left, boolean isRightLonger) {
        return isRightLonger ? right.deltaState().timestamp().sub(left.deltaState().timestamp()) : left.deltaState().timestamp().sub(right.deltaState().timestamp());
    }

    private Profile extendProfile(Profile base, Time idleTime) {
        return base.then(new ConstantVelocityProfile(idleTime));
    }
}
