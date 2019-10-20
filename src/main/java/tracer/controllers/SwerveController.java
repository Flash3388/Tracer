package tracer.controllers;

import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.SwerveTrajectory;

public class SwerveController {
    private final MotionController rearLeft;
    private final MotionController rearRight;
    private final MotionController frontLeft;
    private final MotionController frontRight;

    public SwerveController(SwerveTrajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        rearLeft = MotionController.forTrajectory(trajectory.rearLeft(), max, kV, kA, kP, kI, kD, gP);
        rearRight = MotionController.forTrajectory(trajectory.rearRight(), max, kV, kA, kP, kI, kD, gP);
        frontLeft = MotionController.forTrajectory(trajectory.frontLeft(), max, kV, kA, kP, kI, kD, gP);
        frontRight = MotionController.forTrajectory(trajectory.frontRight(), max, kV, kA, kP, kI, kD, gP);
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
