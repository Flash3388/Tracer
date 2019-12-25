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

    public TankTrajectoryController(TankTrajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters,
                                    PidControllerParameters pidControllerParameters, double maxVoltage, double gP) {
        left = new TrajectoryController(trajectory.left(), max, motionControllerParameters, pidControllerParameters, maxVoltage, gP);
        right = new TrajectoryController(trajectory.right(), max, motionControllerParameters, pidControllerParameters, maxVoltage, -gP);
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
    public Time finalTimestamp() {
        return right.finalTimestamp();
    }
}
