package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.TankTrajectory;
import tracer.trajectories.Trajectory;

public class TankTrajectoryController implements Followable {
    private final TrajectoryController left;
    private final TrajectoryController right;

    public TankTrajectoryController(TankTrajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters,
                                    PidControllerParameters pidControllerParameters, double maxVoltage, double gP) {
        if(trajectory.right().end() > trajectory.left().end()) {
            right = new TrajectoryController(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP);
            TrajectoryController standardLeft = new TrajectoryController(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP);
            left = new TrajectoryController(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP, idleTime(right, standardLeft));
        }
        else {
            left = new TrajectoryController(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP);
            TrajectoryController standardRight = new TrajectoryController(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP);
            right = new TrajectoryController(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP, idleTime(left, standardRight));
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
