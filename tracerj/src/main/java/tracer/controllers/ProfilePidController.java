package tracer.controllers;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;

public class ProfilePidController {
    private final PidController controller;
    private final Profile trajectoryProfile;

    public ProfilePidController(PidControllerParameters parameters, Profile trajectoryProfile, double maxVoltage) {
        this.trajectoryProfile = trajectoryProfile;
        controller = new PidController(parameters.kP(), parameters.kI(), parameters.kD(), 0);
        controller.setOutputLimit(maxVoltage);
    }

    public void reset() {
        controller.reset();
    }

    public Time duration() {
        return trajectoryProfile.duration();
    }

    public double calculate(Position position) {
        return controller.calculate(position.distance(), trajectoryProfile.distanceAt(position.timestamp()));
    }
}
