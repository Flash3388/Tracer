package tracer.controllers.factories;

import tracer.controllers.ProfileMotionController;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.profiles.BasicProfile;
import tracer.profiles.Profile;

public class ProfileMotionControllerFactory {
    private final MotionControllerParameters parameters;

    public ProfileMotionControllerFactory(MotionControllerParameters parameters) {
        this.parameters = parameters;
    }

    public ProfileMotionController create(Profile trajectoryProfile) {
        return new ProfileMotionController(parameters, trajectoryProfile);
    }
}
