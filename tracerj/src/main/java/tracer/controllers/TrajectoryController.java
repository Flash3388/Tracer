package tracer.controllers;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;
import tracer.trajectories.Trajectory;

public class TrajectoryController implements Followable {
    private final Profile trajectoryProfile;

    private final PidController pidController;
    private final ProfileMotionController motionController;
    private final TrajectoryOrientationController orientationController;
    private final double maxVoltage;

    public TrajectoryController(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage) {
        this(trajectory, max, motionControllerParameters, pidControllerParameters, maxVoltage, 0);
    }

    public TrajectoryController(Trajectory trajectory, MotionParameters max, MotionControllerParameters motionControllerParameters, PidControllerParameters pidControllerParameters, double maxVoltage, double gP) {
        trajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        System.out.println(trajectoryProfile.duration());
        motionController = new ProfileMotionController(trajectoryProfile, motionControllerParameters);
        pidController = new PidController(pidControllerParameters.kP(), pidControllerParameters.kI(), pidControllerParameters.kD(), 0);
        pidController.setOutputLimit(maxVoltage);
        orientationController = new TrajectoryOrientationController(trajectory, trajectoryProfile, gP);
        this.maxVoltage = maxVoltage;
    }

    @Override
    public void reset() {
        pidController.reset();
    }

    @Override
    public Time finalTimestamp() {
        return trajectoryProfile.finalTimestamp();
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
}
