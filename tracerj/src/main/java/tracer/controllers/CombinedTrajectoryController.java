package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.following.Followable;
import tracer.motion.Position;

import java.util.List;

public class CombinedTrajectoryController implements Followable {
    private final double maxVoltage;
    private final List<ProfilePidController> pidControllers;
    private final List<ProfileMotionController> motionControllers;
    private final TrajectoryOrientationController orientationController;

    public CombinedTrajectoryController(double maxVoltage, List<ProfilePidController> pidControllers, List<ProfileMotionController> motionControllers, TrajectoryOrientationController orientationController) {
        assert pidControllers.size() == motionControllers.size();

        this.maxVoltage = maxVoltage;
        this.pidControllers = pidControllers;
        this.motionControllers = motionControllers;
        this.orientationController = orientationController;
    }

    public double calculate(Position position, int side, boolean isRight) {
        double pidOut = pidControllers.get(side).calculate(position);
        double motionOut = motionControllers.get(side).calculate(position);
        double orientationOut = orientationController.calculate(position, isRight);
        double vOut = (pidOut + motionOut + orientationOut)/maxVoltage;

        return ExtendedMath.constrain(vOut, -1, 1);
    }

    @Override
    public void reset() {
        pidControllers.forEach(ProfilePidController::reset);
    }

    @Override
    public Time duration() {
        return orientationController.duration();
    }
}
