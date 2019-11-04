package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import tracer.profiles.Profile;
import tracer.trajectories.Trajectory;

public class TrajectoryOrientationController {
    private static final double KP_MODIFIER = 1/80.0;

    private final Trajectory trajectory;
    private final Profile trajectoryProfile;
    private final double kP;

    public TrajectoryOrientationController(Trajectory trajectory, Profile trajectoryProfile, double kP) {
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
        this.kP = kP;
    }

    public double calculate(Position position) {
        double pOut = kP * getAngleError(position.getTiming(), position.getAngle()) * KP_MODIFIER;
        return ExtendedMath.constrain(pOut, -1, 1);
    }

    private double getAngleError(Time currentTime, double currentAngle) {
        return Math.toDegrees(trajectory.angleAt(trajectoryProfile.distanceAt(currentTime))) - currentAngle;
    }
}
