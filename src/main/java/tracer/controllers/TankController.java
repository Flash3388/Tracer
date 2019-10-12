package tracer.controllers;

import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.TankTrajectory;

public class TankController {
    private final MotionController left;
    private final MotionController right;

    public TankController(TankTrajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        left = MotionController.forTrajectory(trajectory.left(), max, kV, kA, kP, kI, kD, gP);//might need to allow different constants for different sides (probably not tho)
        right = MotionController.forTrajectory(trajectory.right(), max, kV, kA, kP, kI, kD, gP);
    }

    public double calcForLeft(Position position) {
        return left.calculate(position);
    }

    public double calcForRight(Position position) {
        return right.calculate(position);
    }
}
