package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.profiles.Profile;

public class ProfileMotionController {
    private final Profile profile;
    private final MotionControllerParameters parameters;

    public ProfileMotionController(Profile profile, MotionControllerParameters parameters) {
        this.profile = profile;
        this.parameters = parameters;
    }

    public double calculate(Time timestamp) {
        double velocity = profile.velocityAt(timestamp);
        double acceleration = profile.accelerationAt(timestamp);

        double vOut = parameters.kV() * velocity;
        double aOut = parameters.kA() * acceleration;
        double sOut = parameters.kS() * Math.signum(velocity);

        return vOut + aOut + sOut;
    }
}
