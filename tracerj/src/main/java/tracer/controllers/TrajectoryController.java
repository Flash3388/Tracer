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
    private final Profile trajectoryProfile;

    private final PidController pidController;
    private final ProfileMotionController motionController;
    private final TrajectoryOrientationController orientationController;
    private final double maxVoltage;

    public static TrajectoryController create(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage) {
        return create(trajectory, max, motionControllerParameters, pidControllerParameters, maxVoltage, 0, Time.milliseconds(0));
    }

    public static TrajectoryController create(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage, Time idleTimeAtEnd) {
        return create(trajectory, max, motionControllerParameters, pidControllerParameters, maxVoltage, 0, idleTimeAtEnd);
    }

    public static TrajectoryController create(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage, double gP) {
        return create(trajectory, max, motionControllerParameters, pidControllerParameters, maxVoltage, gP, Time.milliseconds(0));
    }

    public static TrajectoryController create(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage, double gP, Time idleTimeAtEnd) {
        Profile trajectoryProfile = extendProfile(trajectory, idleTimeAtEnd, max);
        return new TrajectoryController(trajectoryProfile,
                new ProfileMotionController(trajectoryProfile, motionControllerParameters),
                new PidController(pidControllerParameters.kP(), pidControllerParameters.kI(), pidControllerParameters.kD(), 0),
                new TrajectoryOrientationController(trajectory, trajectoryProfile, gP), maxVoltage);
    }

    public TrajectoryController(Profile trajectoryProfile, ProfileMotionController motionController, PidController pidController,TrajectoryOrientationController orientationController, double maxVoltage) {
        this.trajectoryProfile = trajectoryProfile;
        this.motionController = motionController;
        this.pidController = pidController;
        this.orientationController = orientationController;
        this.maxVoltage = maxVoltage;

        pidController.setOutputLimit(maxVoltage);
    }

    @Override
    public void reset() {
        pidController.reset();
    }

    @Override
    public Time duration() {
        return trajectoryProfile.finalTimestamp().sub(trajectoryProfile.initialTimestamp());
    }

    public double calculate(Position position) {
        Time timestamp = position.timestamp();
        double distance = position.distance();

        double pidOut = pidController.calculate(distance, trajectoryProfile.distanceAt(timestamp));
        double motionOut = motionController.calculate(position.timestamp());
        double orientationOut = orientationController.calculate(position);
        double vOut = (pidOut + motionOut + orientationOut)/maxVoltage;

        return ExtendedMath.constrain(vOut, -1, 1);
    }

    private static Profile extendProfile(Trajectory trajectory, Time idleTime, MotionParameters max) {
        Profile standardTrajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        return new ComplexProfile(0, max, Time.milliseconds(0), Arrays.asList(standardTrajectoryProfile, new LinearVelocityProfile(standardTrajectoryProfile, idleTime)));
    }
}
