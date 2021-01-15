package tracer.controllers;

import tracer.motion.Position;

import java.util.Arrays;

public class TankTrajectoryController extends CombinedTrajectoryController {
    public TankTrajectoryController(double maxVoltage, ProfilePidController rightPidController,  ProfileMotionController rightMotionController,
                                    ProfilePidController leftPidController,  ProfileMotionController leftMotionController, TrajectoryOrientationController orientationController) {
        super(maxVoltage, Arrays.asList(rightPidController, leftPidController), Arrays.asList(rightMotionController, leftMotionController), orientationController);
    }

    public double calcForRight(Position position) {
        return calculate(position, 0, true);
    }

    public double calcForLeft(Position position) {
        return calculate(position, 1, false);
    }
}
