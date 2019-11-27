package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.profiles.Profile;

public class ProfileMotionController {
    private final Profile profile;
    private final double kV;
    private final double kA;
    private final double kl;

    public ProfileMotionController(Profile profile, double kV, double kA, double kl) {
        this.profile = profile;
        this.kV = kV;
        this.kA = kA;
        this.kl = kl;
    }

    public double calculate(Time timestamp) {
        double vOut = kV * profile.velocityAt(timestamp);
        double aOut = kA * profile.accelerationAt(timestamp);
        double out = vOut + aOut + kl;

        return ExtendedMath.constrain(out, -1, 1);
    }
}
