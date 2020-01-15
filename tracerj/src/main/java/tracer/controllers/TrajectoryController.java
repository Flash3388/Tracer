package tracer.controllers;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.profiles.ComplexProfile;
import tracer.profiles.LinearVelocityProfile;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;
import calculus.trajectories.Trajectory;

import java.util.Arrays;

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

    private static Profile extendProfile(Trajectory trajectory, Time idleTime, MotionParameters max) {
        Profile standardTrajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        return new ComplexProfile(0, max, Time.milliseconds(0), Arrays.asList(standardTrajectoryProfile, new LinearVelocityProfile(standardTrajectoryProfile, idleTime)));
    }
}
