package tracer.controllers;

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
import tracer.trajectories.Trajectory;

import java.util.Arrays;

public class TrajectoryController implements Followable {
    private final ProfilePidController pidController;
    private final ProfileMotionController motionController;
    private final TrajectoryOrientationController orientationController;
    private double maxVoltage;

    public static TrajectoryController single(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters) {
        return right(pidControllerParameters, motionControllerParameters, 0);
    }

    public static TrajectoryController right(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        return left(pidControllerParameters, motionControllerParameters, -gP);
    }

    public static TrajectoryController left(PidControllerParameters pidControllerParameters, MotionControllerParameters motionControllerParameters, double gP) {
        return new TrajectoryController(new ProfilePidController(pidControllerParameters),
                new ProfileMotionController(motionControllerParameters),
                new TrajectoryOrientationController(gP));
    }

    public TrajectoryController(ProfilePidController pidController, ProfileMotionController motionController,TrajectoryOrientationController orientationController) {
        this.motionController = motionController;
        this.pidController = pidController;
        this.orientationController = orientationController;
        maxVoltage = 0;
    }

    public void setTarget(Trajectory trajectory, MotionParameters max, double maxVoltage) {
        setTarget(trajectory, max, Time.milliseconds(0), maxVoltage);
    }

    public void setTarget(Trajectory trajectory, MotionParameters max, Time idleTimeAtEnd, double maxVoltage) {
        Profile trajectoryProfile = extendProfile(trajectory, idleTimeAtEnd, max);
        this.maxVoltage = maxVoltage;
        pidController.setTarget(trajectoryProfile, maxVoltage);
        motionController.setTarget(trajectoryProfile);
        orientationController.setTarget(trajectory, trajectoryProfile);
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
        double motionOut = motionController.calculate(position.timestamp());
        double orientationOut = orientationController.calculate(position);
        double vOut = (pidOut + motionOut + orientationOut)/maxVoltage;

        return ExtendedMath.constrain(vOut, -1, 1);
    }

    private Profile extendProfile(Trajectory trajectory, Time idleTime, MotionParameters max) {
        Profile standardTrajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        return new ComplexProfile(0, max, Time.milliseconds(0), Arrays.asList(standardTrajectoryProfile, new LinearVelocityProfile(standardTrajectoryProfile, idleTime)));
    }

    private void checkTarget() {
        if(maxVoltage == 0)
            throw new NoTargetException();
    }
}
