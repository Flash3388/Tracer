package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;

public class ProfileMotionController {
    private final MotionControllerParameters parameters;
    private final Profile profile;

    public ProfileMotionController(MotionControllerParameters parameters, Profile profile) {
        this.parameters = parameters;
        this.profile = profile;
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
