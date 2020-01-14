package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.TankTrajectory;

public class TankTrajectoryController implements Followable {
    private final TrajectoryController left;
    private final TrajectoryController right;

    public static TankTrajectoryController fromParameters(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        return new TankTrajectoryController(
                TrajectoryController.left(pidControllerParameters, motionControllerParameters, gP),
                TrajectoryController.right(pidControllerParameters, motionControllerParameters, gP));
    }

    public TankTrajectoryController(TrajectoryController left, TrajectoryController right) {
        this.left = left;
        this.right = right;
    }

    public void setTarget(TankTrajectory trajectory, MotionParameters max, double maxVoltage) {
        if(trajectory.right().end() > trajectory.left().end()) {
            right.setTarget(trajectory.right(), max, maxVoltage);
            left.setTarget(trajectory.left(), max, maxVoltage);
            left.setTarget(trajectory.left(), max, idleTime(right, left), maxVoltage);
        }
        else {
            left.setTarget(trajectory.left(), max, maxVoltage);
            right.setTarget(trajectory.right(), max, maxVoltage);
            right.setTarget(trajectory.right(), max, idleTime(left, right), maxVoltage);
        }
    }

    public double calcForLeft(Position position) {
        return left.calculate(position);
    }

    public double calcForRight(Position position) {
        return right.calculate(position);
    }

    @Override
    public void reset() {
        left.reset();
        right.reset();
    }

    @Override
    public Time duration() {
        return right.duration().after(left.duration()) ? right.duration() : left.duration();
    }

    private Time idleTime(TrajectoryController longer, TrajectoryController shorter) {
        return longer.duration().sub(shorter.duration());
    }
}
