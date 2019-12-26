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
        try {
            return left.calculate(position);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    public double calcForRight(Position position) {
        try {
            return right.calculate(position);
        } catch (IllegalArgumentException e) {
            return 0;
        }
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
}
