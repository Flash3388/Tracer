package com.flash3388.tracer.controllers.factories;

import com.flash3388.tracer.controllers.ProfilePidController;
import com.flash3388.tracer.profiles.base.Profile;
import com.flash3388.tracer.controllers.parameters.PidControllerParameters;

public class ProfilePidControllerFactory {
    private final PidControllerParameters parameters;

    public ProfilePidControllerFactory(PidControllerParameters parameters) {
        this.parameters = parameters;
    }

    public ProfilePidController create(Profile trajectoryProfile, double maxVoltage) {
        return new ProfilePidController(parameters, trajectoryProfile, maxVoltage);
    }
}
