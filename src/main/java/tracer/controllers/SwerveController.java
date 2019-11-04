package tracer.controllers;

import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.SwerveTrajectory;

@Deprecated
public class SwerveController {
    private final TrajectoryController rearLeft;
    private final TrajectoryController rearRight;
    private final TrajectoryController frontLeft;
    private final TrajectoryController frontRight;

    public SwerveController(SwerveTrajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        rearLeft = new TrajectoryController(trajectory.rearLeft(), max, kV, kA, kP, kI, kD);
        rearRight = new TrajectoryController(trajectory.rearRight(), max, kV, kA, kP, kI, kD);
        frontLeft = new TrajectoryController(trajectory.frontLeft(), max, kV, kA, kP, kI, kD);
        frontRight = new TrajectoryController(trajectory.frontRight(), max, kV, kA, kP, kI, kD);
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
}
