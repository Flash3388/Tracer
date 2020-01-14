package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.profiles.Profile;
import tracer.trajectories.Trajectory;

public class ProfileMotionController {
    private final MotionControllerParameters parameters;

    private Profile profile;

    public ProfileMotionController(MotionControllerParameters parameters) {
        this.parameters = parameters;
        profile = null;
    }

    public void setTarget(Profile profile) {
        this.profile = profile;
    }

    public double calculate(Time timestamp) {
        checkTarget();

        double velocity = profile.velocityAt(timestamp);
        double acceleration = profile.accelerationAt(timestamp);

        double vOut = parameters.kV() * velocity;
        double aOut = parameters.kA() * acceleration;
        double sOut = parameters.kS() * Math.signum(velocity);

        return vOut + aOut + sOut;
    }

    private void checkTarget() {
        if(profile == null)
            throw new NoTargetException();
    }
}
