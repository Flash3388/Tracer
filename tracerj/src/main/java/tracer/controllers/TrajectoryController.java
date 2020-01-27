package tracer.controllers;

import tracer.motion.Position;

import java.util.Collections;

public class TrajectoryController extends CombinedTrajectoryController {
    public TrajectoryController(double maxVoltage, ProfilePidController pidController, ProfileMotionController motionController, TrajectoryOrientationController orientationController) {
        super(maxVoltage, Collections.singletonList(pidController), Collections.singletonList(motionController), orientationController);
    }

    public double calculate(Position position) {
        return calculate(position, 0, false);
    }
}
