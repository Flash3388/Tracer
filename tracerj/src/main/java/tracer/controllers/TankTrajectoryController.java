package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import calculus.trajectories.TankTrajectory;

public class TankTrajectoryController implements Followable {
    private final TrajectoryController left;
    private final TrajectoryController right;

    public TankTrajectoryController(TankTrajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters,
                                    PidControllerParameters pidControllerParameters, double maxVoltage, double gP) {
//        if(trajectory.right().end() > trajectory.left().end()) {
//            right = TrajectoryController.create(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP);
//            TrajectoryController standardLeft = TrajectoryController.create(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP);
//            left = TrajectoryController.create(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP, idleTime(right, standardLeft));
//        }
//        else {
//            left = TrajectoryController.create(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP);
//            TrajectoryController standardRight = TrajectoryController.create(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP);
//            right = TrajectoryController.create(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP, idleTime(left, standardRight));
//        }
        left = null;
        right = null;
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
