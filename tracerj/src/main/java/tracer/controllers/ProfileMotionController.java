package tracer.controllers;

import tracer.controllers.parameters.MotionControllerParameters;
import tracer.motion.Position;
import tracer.profiles.base.Profile;

public class ProfileMotionController {
    private final MotionControllerParameters parameters;
    private final Profile profile;

    public ProfileMotionController(MotionControllerParameters parameters, Profile profile) {
        this.parameters = parameters;
        this.profile = profile;
    }

    public double calculate(Position position) {
        double velocity = profile.state(position.timestamp()).velocity().valueAsMetersPerSecond();
        double acceleration = profile.state(position.timestamp()).acceleration().valueAsMetersPerSecondSquared();

        double vOut = parameters.kV() * velocity;
        double aOut = parameters.kA() * acceleration;
        double sOut = parameters.kS() * Math.signum(velocity);

        return vOut + aOut + sOut;
    }
}
