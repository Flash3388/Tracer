package tracer.controllers;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;
import tracer.trajectories.Trajectory;

import java.util.function.Function;

public class MotionController extends Controller{
    private final Function<Time, Double> angleAt;
    private final PidController pidController;

    private final double kV;
    private final double kA;

    private final double gP;

    public static MotionController forTrajectory(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        Profile functionalProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        Function<Time, Double> angleAt = time -> Math.toDegrees(trajectory.angleAt(functionalProfile.distanceAt(time)));

        return new MotionController(functionalProfile, angleAt, kV, kA, kP, kI, kD, gP);
    }

    public void reset() {
        pidController.reset();
    }

    @Override
    public double calculate(Position position) {
        Time timing = position.getTiming();
        double distance = position.getDistance();
        double angle = position.getAngle();

        double velocity = getProfile().velocityAt(timing);
        double acceleration = getProfile().accelerationAt(timing);
        double angleError = getAngleError(timing, angle);

        double vOut = kV * velocity;
        double aOut = kA * acceleration;

        double gOut = gP * angleError;

        return pidController.calculate(distance, getProfile().distanceAt(timing)) + vOut + aOut + gOut;
    }

    private MotionController(Profile trajectoryProfile, Function<Time, Double> angleAt, double kV, double kA, double kP, double kI, double kD, double gP) {
        super(trajectoryProfile);
        this.angleAt = angleAt;
        pidController = new PidController(kP, kI, kD, 0);

        this.kV = kV;
        this.kA = kA;

        this.gP = gP;
    }

    private double getAngleError(Time currentTime, double currentAngle) {
        return angleAt.apply(currentTime) - currentAngle;
    }
}
