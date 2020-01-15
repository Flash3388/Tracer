package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;

public class ProfileMotionController {
    private final Profile profile;
    private final MotionControllerParameters parameters;

    public ProfileMotionController(Profile profile, MotionControllerParameters parameters) {
        this.profile = profile;
        this.parameters = parameters;
    }

    public double calculate(Position position) {
        double velocity = profile.velocityAt(position.timestamp());
        double acceleration = profile.accelerationAt(position.timestamp());

        double vOut = parameters.kV() * velocity;
        double aOut = parameters.kA() * acceleration;
        double sOut = parameters.kS() * Math.signum(velocity);

        return vOut + aOut + sOut;
    }
}
