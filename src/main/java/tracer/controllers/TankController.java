package tracer.controllers;

import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.TankTrajectory;

public class TankController {
    private final TrajectoryController left;
    private final TrajectoryController right;

    public TankController(TankTrajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        left = new TrajectoryController(trajectory.left(), max, kV, kA, kP, kI, kD, gP);
        right = new TrajectoryController(trajectory.right(), max, kV, kA, kP, kI, kD, -gP);
    }

    public double calcForLeft(Position position) {
        return left.calculate(position);
    }

    public double calcForRight(Position position) {
        return right.calculate(position);
    }
}
