package tracer.controllers.factories;

import calculus.trajectories.TankTrajectory;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.controllers.TrajectoryController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.profiles.BasicProfile;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;

public class TankTrajectoryControllerFactory {
    private final TrajectoryControllerFactory left;
    private final TrajectoryControllerFactory right;

    public TankTrajectoryControllerFactory(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        left = TrajectoryControllerFactory.left(pidControllerParameters, motionControllerParameters, gP);
        right = TrajectoryControllerFactory.right(pidControllerParameters, motionControllerParameters, gP);
    }

    public TankTrajectoryController create(TankTrajectory trajectory, MotionState max, double maxVoltage, boolean isForward) {
        return new TankTrajectoryController(
                left(trajectory, max, maxVoltage, isForward),
                right(trajectory, max, maxVoltage, isForward)
        );
    }

    private TrajectoryController left(TankTrajectory trajectory, MotionState max, double maxVoltage, boolean isForward) {
        Time idleTime;

        if(trajectory.right().end() > trajectory.left().end())
            idleTime = idleTime(trajectory, max, true);
        else
            idleTime = Time.milliseconds(0);

        return left.create(trajectory.left(), max, maxVoltage, idleTime, isForward);
    }

    private TrajectoryController right(TankTrajectory trajectory, MotionState max, double maxVoltage, boolean isForward) {
        Time idleTime;

        if(trajectory.left().end() > trajectory.right().end())
            idleTime = idleTime(trajectory, max, false);
        else
            idleTime = Time.milliseconds(0);

        return right.create(trajectory.right(), max, maxVoltage, idleTime, isForward);
    }

    private Time idleTime(TankTrajectory trajectory, MotionState max, boolean isRightLonger) {
        Profile left = ProfileFactory.createTrajectoryProfile(max, trajectory.left());
        Profile right = ProfileFactory.createTrajectoryProfile(max, trajectory.right());

        return isRightLonger ? right.deltaState().timestamp().sub(left.deltaState().timestamp()) : left.deltaState().timestamp().sub(right.deltaState().timestamp());
    }
}
