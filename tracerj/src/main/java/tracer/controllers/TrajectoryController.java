package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.following.Followable;
import tracer.motion.Position;

public class TrajectoryController implements Followable {
    private final ProfilePidController pidController;
    private final ProfileMotionController motionController;
    private final TrajectoryOrientationController orientationController;
    private final double maxVoltage;

    public TrajectoryController(ProfilePidController pidController, ProfileMotionController motionController,TrajectoryOrientationController orientationController, double maxVoltage) {
        this.pidController = pidController;
        this.motionController = motionController;
        this.orientationController = orientationController;
        this.maxVoltage = maxVoltage;
    }

    public double expectedAngleAt(Position position) {
        return orientationController.expectedAngleAt(position);
    }

    @Override
    public void reset() {
        pidController.reset();
    }

    @Override
    public Time duration() {
        return pidController.duration();
    }

    public double calculate(Position position) {
        double pidOut = pidController.calculate(position);
        double motionOut = motionController.calculate(position);
        double orientationOut = orientationController.calculate(position);
        double vOut = (pidOut + motionOut + orientationOut)/maxVoltage;

        return ExtendedMath.constrain(vOut, -1, 1);
    }
}
