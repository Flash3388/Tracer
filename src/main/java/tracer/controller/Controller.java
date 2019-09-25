package tracer.controller;

import tracer.motion.PhysicalPosition;
import tracer.profiles.Profile;

public abstract class Controller {
    private Profile motionProfile;

    public Controller(Profile motionProfile) {
        this.motionProfile = motionProfile;
    }

    public Profile getProfile() {
        return motionProfile;
    }

    public abstract double calculate(PhysicalPosition position);
}
