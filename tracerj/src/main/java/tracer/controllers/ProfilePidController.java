package tracer.controllers;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;

public class ProfilePidController {
    private final PidController pidController;
    private Profile trajectoryProfile;

    public ProfilePidController(PidControllerParameters parameters) {
        pidController = new PidController(parameters.kP(), parameters.kI(), parameters.kD(), 0);
        trajectoryProfile = null;
    }

    public void setTarget(Profile trajectoryProfile, double maxVoltage) {
        this.trajectoryProfile = trajectoryProfile;
        pidController.setOutputLimit(maxVoltage);
    }

    public void reset() {
        pidController.reset();
    }

    public Time duration() {
        checkTarget();
        return trajectoryProfile.duration();
    }

    public double calculate(Position position) {
        checkTarget();
        return pidController.calculate(position.distance(), trajectoryProfile.distanceAt(position.timestamp()));
    }

    private void checkTarget() {
        if(trajectoryProfile == null)
            throw new NoTargetException();
    }
}
