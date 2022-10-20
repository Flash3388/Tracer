package com.flash3388.tracer.controllers;

import com.flash3388.tracer.motion.Position;
import com.flash3388.tracer.profiles.base.Profile;

import java.util.Collections;

public class TrajectoryController extends CombinedTrajectoryController {
    private final Profile trajectoryProfile;
    private final TrajectoryOrientationController orientationController;

    public TrajectoryController(double maxVoltage, ProfilePidController pidController, ProfileMotionController motionController, TrajectoryOrientationController orientationController, Profile trajectoryProfile) {
        super(maxVoltage, Collections.singletonList(pidController), Collections.singletonList(motionController), orientationController);
        this.orientationController = orientationController;
        this.trajectoryProfile = trajectoryProfile;
    }

    public double calculate(Position position) {
        return calculate(position, 0, false);
    }

    public double expectedAngleAt(Position position) {
        return orientationController.expectedAngleAt(trajectoryProfile.state(position.timestamp()).distance());
    }
}
