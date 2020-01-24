package tracer.controllers.factories;

import calculus.trajectories.TankTrajectory;
import com.flash3388.flashlib.robot.motion.Direction;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.controllers.TrajectoryController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.profiles.base.Profile;
import tracer.profiles.ProfileFactory;

public class TankTrajectoryControllerFactory {
    private final TrajectoryControllerFactory left;
    private final TrajectoryControllerFactory right;

    public TankTrajectoryControllerFactory(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        left = TrajectoryControllerFactory.left(pidControllerParameters, motionControllerParameters, gP);
        right = TrajectoryControllerFactory.right(pidControllerParameters, motionControllerParameters, gP);
    }

    public TankTrajectoryController create(TankTrajectory trajectory, MotionState max, double maxVoltage, Direction direction) {
        return new TankTrajectoryController(
                left(trajectory, max, maxVoltage, direction),
                right(trajectory, max, maxVoltage, direction)
        );
    }

    private TrajectoryController left(TankTrajectory trajectory, MotionState max, double maxVoltage, Direction direction) {
        Time idleTime;

        if(trajectory.right().end() > trajectory.left().end())
            idleTime = idleTime(trajectory, max, true);
        else
            idleTime = Time.milliseconds(0);

        return left.create(trajectory.left(), max, maxVoltage, idleTime, direction);
    }

    private TrajectoryController right(TankTrajectory trajectory, MotionState max, double maxVoltage, Direction direction) {
        Time idleTime;

        if(trajectory.left().end() > trajectory.right().end())
            idleTime = idleTime(trajectory, max, false);
        else
            idleTime = Time.milliseconds(0);

        return right.create(trajectory.right(), max, maxVoltage, idleTime, direction);
    }

    private Time idleTime(TankTrajectory trajectory, MotionState max, boolean isRightLonger) {
        Profile left = ProfileFactory.createTrajectoryProfile(max, trajectory.left());
        Profile right = ProfileFactory.createTrajectoryProfile(max, trajectory.right());

        return isRightLonger ? right.deltaState().timestamp().sub(left.deltaState().timestamp()) : left.deltaState().timestamp().sub(right.deltaState().timestamp());
    }
}
