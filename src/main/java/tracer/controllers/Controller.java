package tracer.controllers;

import tracer.motion.Position;
import tracer.profiles.Profile;

public abstract class Controller {
    private Profile motionProfile;

    public Controller(Profile motionProfile) {
        this.motionProfile = motionProfile;
    }

    public Profile getProfile() {
        return motionProfile;
    }

    public abstract double calculate(Position position);
}
