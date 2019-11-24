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

    public TrajectoryController(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD) {
        this(trajectory, max, kV, kA, kP, kI, kD, 0);
    }

    public TrajectoryController(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        trajectoryProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        pidController = new PidController(kP, kI, kD, 0);
        motionController = new ProfileMotionController(trajectoryProfile, kV, kA, 0);
        orientationController = new TrajectoryOrientationController(trajectory, trajectoryProfile, gP);
    }

    @Override
    public void reset() {
        pidController.reset();
    }

    @Override
    public Time finaltimestamp() {
        return trajectoryProfile.finaltimestamp();
    }

    public double calculate(Position position) {
        Time timestamp = position.gettimestamp();
        double distance = position.getDistance();

        double pidOut = pidController.calculate(distance, trajectoryProfile.distanceAt(timestamp));
        double motionOut = motionController.calculate(position.gettimestamp());
        double orientationOut = orientationController.calculate(position);

        return ExtendedMath.constrain(ExtendedMath.constrain(pidOut + motionOut, -1, 1) + orientationOut, -1, 1);
    }
}
