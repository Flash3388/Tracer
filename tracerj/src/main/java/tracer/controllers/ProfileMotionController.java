package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.profiles.Profile;

public class ProfileMotionController {
    private final Profile profile;
    private final double kV;
    private final double kA;
    private final double kS;

    public ProfileMotionController(Profile profile, MotionControllerParameters parameters) {
        this(profile, parameters.kV(), parameters.kA(), parameters.kS());
    }

    public ProfileMotionController(Profile profile, double kV, double kA, double kS) {
        this.profile = profile;
        this.kV = kV;
        this.kA = kA;
        this.kS = kS;
    }

    public double calculate(Time timestamp) {
        double velocity = profile.velocityAt(timestamp);

        double vOut = kV * velocity;
        double aOut = kA * profile.accelerationAt(timestamp);
        double sOut = kS * Math.signum(velocity);

        return vOut + aOut + sOut;
    }
}
