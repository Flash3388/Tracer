package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.SwerveTrajectory;

public class SwerveTrajectoryController implements Followable {
    private final TrajectoryController rearLeft;
    private final TrajectoryController rearRight;
    private final TrajectoryController frontLeft;
    private final TrajectoryController frontRight;

    public SwerveTrajectoryController(SwerveTrajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage) {
        rearLeft = new TrajectoryController(trajectory.rearLeft(), max, motionControllerParameters, pidControllerParameters, maxVoltage);
        rearRight = new TrajectoryController(trajectory.rearRight(), max, motionControllerParameters, pidControllerParameters, maxVoltage);
        frontLeft = new TrajectoryController(trajectory.frontLeft(), max, motionControllerParameters, pidControllerParameters, maxVoltage);
        frontRight = new TrajectoryController(trajectory.frontRight(), max, motionControllerParameters, pidControllerParameters, maxVoltage);
    }

    public double calcForRearLeft(Position position) {
        return rearLeft.calculate(position);
    }

    public double calcForRearRight(Position position) {
        return rearRight.calculate(position);
    }

    public double calcForFrontLeft(Position position) {
        return frontLeft.calculate(position);
    }

    public double calcForFrontRight(Position position) {
        return frontRight.calculate(position);
    }

    @Override
    public void reset() {
        frontRight.reset();
        frontLeft.reset();
        rearRight.reset();
        rearLeft.reset();
    }

    @Override
    public Time finalTimestamp() {
        return frontRight.finalTimestamp();
    }
}
