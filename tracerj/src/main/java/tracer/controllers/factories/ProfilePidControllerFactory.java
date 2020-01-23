package tracer.controllers.factories;

import tracer.controllers.ProfilePidController;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.profiles.BasicProfile;
import tracer.profiles.Profile;

public class ProfilePidControllerFactory {
    private final PidControllerParameters parameters;

    public ProfilePidControllerFactory(PidControllerParameters parameters) {
        this.parameters = parameters;
    }

    public ProfilePidController create(Profile trajectoryProfile, double maxVoltage) {
        return new ProfilePidController(parameters, trajectoryProfile, maxVoltage);
    }
}
