package com.flash3388.tracer.controllers.factories;

import com.flash3388.tracer.controllers.ProfileMotionController;
import com.flash3388.tracer.profiles.base.Profile;
import com.flash3388.tracer.controllers.parameters.MotionControllerParameters;

public class ProfileMotionControllerFactory {
    private final MotionControllerParameters parameters;

    public ProfileMotionControllerFactory(MotionControllerParameters parameters) {
        this.parameters = parameters;
    }

    public ProfileMotionController create(Profile trajectoryProfile) {
        return new ProfileMotionController(parameters, trajectoryProfile);
    }
}
