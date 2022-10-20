package com.flash3388.tracer.controllers;

import com.flash3388.tracer.motion.Position;
import com.flash3388.tracer.profiles.ProfileState;
import com.flash3388.tracer.profiles.base.Profile;
import com.flash3388.tracer.controllers.parameters.MotionControllerParameters;

public class ProfileMotionController {
    private final MotionControllerParameters parameters;
    private final Profile profile;

    public ProfileMotionController(MotionControllerParameters parameters, Profile profile) {
        this.parameters = parameters;
        this.profile = profile;
    }

    public double calculate(Position position) {
        ProfileState state = profile.state(position.timestamp());
        double velocity = state.velocity();
        double acceleration = state.acceleration();

        double vOut = parameters.kV() * velocity;
        double aOut = parameters.kA() * acceleration;
        double sOut = parameters.kS() * Math.signum(velocity);

        return vOut + aOut + sOut;
    }
}
